package com.cz.viid.be.service.impl;

import com.cz.viid.be.service.VIIDNotificationService;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.*;
import com.cz.viid.framework.domain.entity.APEDevice;
import com.cz.viid.framework.domain.entity.TollgateDevice;
import com.cz.viid.framework.domain.entity.VIIDSubscribe;
import com.cz.viid.framework.security.SecurityContext;
import com.cz.viid.framework.service.*;
import com.cz.viid.utils.JsonCommon;
import com.cz.viid.utils.StructCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VIIDNotificationServiceImpl implements VIIDNotificationService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Resource
    APEDeviceService apeDeviceService;
    @Resource
    TollgateDeviceService tollgateDeviceService;
    @Autowired
    VIIDSubscribeService viidSubscribeService;

    @Override
    public VIIDSubscribe getSubscribeById(String subscribeId) {
        return viidSubscribeService.getCacheById(subscribeId);
    }

    @Override
    public ResponseStatusObject motorVehicleHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe) {
        String serverId = SecurityContext.getRequestServerId();
        MotorVehicleObjects motorVehicleObjects = notificationObject.getMotorVehicleObjectList();
        if (Objects.nonNull(motorVehicleObjects.getMotorVehicleObject())) {
            String topic = StringUtils.join(Constants.DEFAULT_TOPIC_PREFIX.MOTOR_VEHICLE, serverId);
            for (MotorVehicleObject motorVehicleObject : motorVehicleObjects.getMotorVehicleObject()) {
                kafkaTemplate.send(topic, JsonCommon.toJson(motorVehicleObject));
            }
        }
        return new ResponseStatusObject(null, "0", "操作成功");
    }

    @Override
    public ResponseStatusObject tollgateHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe) {

        return new ResponseStatusObject(null, "0", "操作成功");
    }

    @Override
    public ResponseStatusObject apeDeviceHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe) {

        return new ResponseStatusObject(null, "0", "操作成功");
    }

    @Override
    public ResponseStatusObject faceHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe) {

        return new ResponseStatusObject(null, "0", "操作成功");
    }

    @Override
    public ResponseStatusObject nonMotorVehicleHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe) {

        return new ResponseStatusObject(null, "0", "操作成功");
    }

    @Override
    public ResponseStatusObject personHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe) {
        String serverId = SecurityContext.getRequestServerId();
        PersonList personList = notificationObject.getPersonObjectList();
        if (Objects.nonNull(personList)) {
            String topic = StringUtils.join(Constants.DEFAULT_TOPIC_PREFIX.PERSON_RECORD, serverId);
            for (Person person : personList.getPersonObject()) {
                kafkaTemplate.send(topic, JsonCommon.toJson(person));
            }
        }
        return new ResponseStatusObject(null, "0", "操作成功");
    }
}
