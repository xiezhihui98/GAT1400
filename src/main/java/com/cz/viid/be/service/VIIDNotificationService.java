package com.cz.viid.be.service;


import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.dto.SubscribeNotificationObject;
import com.cz.viid.framework.domain.entity.VIIDSubscribe;

public interface VIIDNotificationService {

    VIIDSubscribe getSubscribeById(String subscribeId);

    ResponseStatusObject motorVehicleHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe);

    ResponseStatusObject tollgateHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe);

    ResponseStatusObject apeDeviceHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe);

    ResponseStatusObject faceHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe);

    ResponseStatusObject nonMotorVehicleHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe);

    ResponseStatusObject personHandler(SubscribeNotificationObject notificationObject, VIIDSubscribe subscribe);
}
