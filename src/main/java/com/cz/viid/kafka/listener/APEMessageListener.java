package com.cz.viid.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.APEObject;
import com.cz.viid.framework.domain.dto.DeviceObjectList;
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
 * 采集设备消息监听器
 * from 消费kafka topic的采集设备
 * to PublishEntity定义的视图库回调
 */
public class APEMessageListener extends AbstractMessageListener<APEObject> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public APEMessageListener(VIIDPublish publish) {
        super(publish, Constants.VIID_SERVER.SUBSCRIBE_DETAIL.DEVICE_DIR);
    }

    @Override
    public APEObject messageConverter(String value) {
        return Optional.of(JSONObject.parseObject(value, APEObject.class))
                .filter(obj -> StringUtils.isNotBlank(obj.getApeID()))
                .orElse(null);
    }
    @Override
    public SubscribeNotificationRequest packHandler(List<APEObject> partition) {
        DeviceObjectList deviceObjectList = new DeviceObjectList();
        deviceObjectList.setAPEObject(partition);

        SubscribeNotificationObject notificationObject = new SubscribeNotificationObject();
        notificationObject.setExecuteOperation("1");
        notificationObject.setNotificationID(IdWorker.getIdStr(VIIDPublish.class) + "0");
        notificationObject.setSubscribeID(publish.getSubscribeId());
        notificationObject.setTitle(publish.getTitle());
        notificationObject.setTriggerTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        notificationObject.setInfoIDs(partition
                .stream()
                .map(APEObject::getApeID)
                .collect(Collectors.joining(","))
        );
        notificationObject.setDeviceList(deviceObjectList);

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
