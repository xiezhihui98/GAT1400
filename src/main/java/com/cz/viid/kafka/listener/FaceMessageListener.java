package com.cz.viid.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.FaceObject;
import com.cz.viid.framework.domain.dto.FaceObjectList;
import com.cz.viid.framework.domain.dto.SubscribeNotificationObject;
import com.cz.viid.framework.domain.dto.SubscribeNotifications;
import com.cz.viid.framework.domain.entity.VIIDPublish;
import com.cz.viid.framework.domain.vo.SubscribeNotificationRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 人脸信息消息监听器
 * from 消费kafka topic的人脸数据
 * to PublishEntity定义的视图库回调
 */
public class FaceMessageListener extends AbstractMessageListener<FaceObject> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public FaceMessageListener(VIIDPublish publish) {
        super(publish, Constants.VIID_SERVER.SUBSCRIBE_DETAIL.FACE_INFO);
    }

    @Override
    public FaceObject messageConverter(String value) {
        return Optional.of(value)
                .map(ele -> JSONObject.parseObject(value, FaceObject.class))
                .filter(ele -> StringUtils.isNotBlank(ele.getFaceID()))
                .orElse(null);
    }

    @Override
    public SubscribeNotificationRequest packHandler(List<FaceObject> partition) {
        FaceObjectList faceObjectList = new FaceObjectList();
        faceObjectList.setFaceObject(partition);

        SubscribeNotificationObject notificationObject = new SubscribeNotificationObject();
        notificationObject.setExecuteOperation("1");
        notificationObject.setNotificationID(IdWorker.getIdStr(VIIDPublish.class) + "0");
        notificationObject.setSubscribeID(publish.getSubscribeId());
        notificationObject.setTitle(publish.getTitle());
        notificationObject.setTriggerTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        notificationObject.setInfoIDs(partition
                .stream()
                .map(FaceObject::getFaceID)
                .collect(Collectors.joining(","))
        );
        notificationObject.setFaceObjectList(faceObjectList);

        long size = partition.size();
        SubscribeNotifications subscribeNotifications = new SubscribeNotifications();
        subscribeNotifications.setPageRecordNum(size);
        subscribeNotifications.setTotalNum(size);
        subscribeNotifications.setSubscribeNotificationObject(Collections.singletonList(notificationObject));

        SubscribeNotificationRequest notificationRequest = new SubscribeNotificationRequest();
        notificationRequest.setSubscribeNotificationListObject(subscribeNotifications);
        return notificationRequest;
    }
}
