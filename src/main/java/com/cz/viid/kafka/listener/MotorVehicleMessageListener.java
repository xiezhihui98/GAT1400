package com.cz.viid.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.MotorVehicleObject;
import com.cz.viid.framework.domain.dto.MotorVehicleObjects;
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
 * 车辆信息消息监听器
 * from 消费默认topic的过车数据
 * to PublishEntity定义的视图库回调
 */
public class MotorVehicleMessageListener extends AbstractMessageListener<MotorVehicleObject> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MotorVehicleMessageListener(VIIDPublish publish) {
        super(publish, Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PLATE_INFO);
    }

    @Override
    public MotorVehicleObject messageConverter(String value) {
        return Optional.of(JSONObject.parseObject(value, MotorVehicleObject.class))
                .filter(ele -> StringUtils.isNotBlank(ele.getMotorVehicleID()))
                .orElse(null);
    }

    @Override
    public SubscribeNotificationRequest packHandler(List<MotorVehicleObject> partitions) {
        long size = partitions.size();
        SubscribeNotifications subscribeNotifications = new SubscribeNotifications();
        subscribeNotifications.setPageRecordNum(size);
        subscribeNotifications.setTotalNum(size);
        List<SubscribeNotificationObject> notificationObjects = Optional.of(partitions)
                .map(this::MotorVehicleObjectsBuilder)
                .map(this::SubscribeNotificationObjectBuilder)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        subscribeNotifications.setSubscribeNotificationObject(notificationObjects);
        SubscribeNotificationRequest notificationRequest = new SubscribeNotificationRequest();
        notificationRequest.setSubscribeNotificationListObject(subscribeNotifications);
        return notificationRequest;
    }

    private SubscribeNotificationObject SubscribeNotificationObjectBuilder(MotorVehicleObjects objects) {
        SubscribeNotificationObject model = new SubscribeNotificationObject();
        model.setExecuteOperation("1");
        model.setNotificationID(IdWorker.getIdStr(VIIDPublish.class) + "0");
        model.setSubscribeID(publish.getSubscribeId());
        model.setTitle(publish.getTitle());
        model.setTriggerTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        model.setInfoIDs(objects.getMotorVehicleObject()
                .stream()
                .map(MotorVehicleObject::getMotorVehicleID)
                .collect(Collectors.joining(","))
        );
        model.setMotorVehicleObjectList(objects);
        return model;
    }

    private MotorVehicleObjects MotorVehicleObjectsBuilder(List<MotorVehicleObject> data) {
        MotorVehicleObjects model = new MotorVehicleObjects();
        model.setMotorVehicleObject(data);
        model.setPageRecordNum((long) data.size());
        return model;
    }
}
