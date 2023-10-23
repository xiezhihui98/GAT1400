package com.cz.viid.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.SubscribeNotificationObject;
import com.cz.viid.framework.domain.dto.SubscribeNotifications;
import com.cz.viid.framework.domain.dto.TollgateObject;
import com.cz.viid.framework.domain.dto.TollgateObjectList;
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
 * 视频目录信息消息监听器
 * from 消费默认topic的过车数据
 * to PublishEntity定义的视图库回调
 */
public class TollgateMessageListener extends AbstractMessageListener<TollgateObject> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public TollgateMessageListener(VIIDPublish publish) {
        super(publish, Constants.VIID_SERVER.SUBSCRIBE_DETAIL.VIDEO_TOLLGATE_DIR);
    }


    @Override
    public TollgateObject messageConverter(String value) {
        return Optional.of(value)
                .map(ele -> JSONObject.parseObject(ele, TollgateObject.class))
                .filter(ele -> StringUtils.isNotBlank(ele.getTollgateID()))
                .orElse(null);
    }

    @Override
    public SubscribeNotificationRequest packHandler(List<TollgateObject> partition) {
        long size = partition.size();
        SubscribeNotifications subscribeNotifications = new SubscribeNotifications();
        subscribeNotifications.setPageRecordNum(size);
        subscribeNotifications.setTotalNum(size);
        List<SubscribeNotificationObject> notificationObjects = Optional.of(partition)
                .map(this::TollgateObjectBuilder)
                .map(this::SubscribeNotificationObjectBuilder)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        subscribeNotifications.setSubscribeNotificationObject(notificationObjects);

        SubscribeNotificationRequest notificationRequest = new SubscribeNotificationRequest();
        notificationRequest.setSubscribeNotificationListObject(subscribeNotifications);
        return notificationRequest;
    }

    private TollgateObjectList TollgateObjectBuilder(List<TollgateObject> data) {
        TollgateObjectList model = new TollgateObjectList();
        model.setTollgateObject(data);
        return model;
    }

    private SubscribeNotificationObject SubscribeNotificationObjectBuilder(TollgateObjectList objects) {
        SubscribeNotificationObject model = new SubscribeNotificationObject();
        model.setExecuteOperation("1");
        model.setNotificationID(IdWorker.getIdStr(VIIDPublish.class) + "0");
        model.setSubscribeID(publish.getSubscribeId());
        model.setTitle(publish.getTitle());
        model.setTriggerTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        model.setInfoIDs(objects.getTollgateObject()
                .stream()
                .map(TollgateObject::getTollgateID)
                .collect(Collectors.joining(","))
        );
        model.setTollgateObjectList(objects);
        return model;
    }
}
