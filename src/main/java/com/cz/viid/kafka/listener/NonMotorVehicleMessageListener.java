package com.cz.viid.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.NonMotorVehicle;
import com.cz.viid.framework.domain.dto.NonMotorVehicleList;
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
 * 非机动车信息消息监听器
 * from 消费默认topic的非机动车数据
 * to PublishEntity定义的视图库回调
 */
public class NonMotorVehicleMessageListener extends AbstractMessageListener<NonMotorVehicle> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public NonMotorVehicleMessageListener(VIIDPublish publish) {
        super(publish, Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PLATE_MIRCO_INFO);
    }

    @Override
    public NonMotorVehicle messageConverter(String value) {
        return Optional.of(value)
                .map(ele -> JSONObject.parseObject(ele, NonMotorVehicle.class))
                .filter(ele -> StringUtils.isNotBlank(ele.getNonMotorVehicleID()))
                .orElse(null);
    }

    public SubscribeNotificationRequest packHandler(List<NonMotorVehicle> partition) {
        long size = partition.size();
        SubscribeNotifications subscribeNotifications = new SubscribeNotifications();
        subscribeNotifications.setPageRecordNum(size);
        subscribeNotifications.setTotalNum(size);
        List<SubscribeNotificationObject> notificationObjects = Optional.of(partition)
                .map(this::NonMotorVehicleListBuilder)
                .map(this::SubscribeNotificationObjectBuilder)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        subscribeNotifications.setSubscribeNotificationObject(notificationObjects);
        SubscribeNotificationRequest notificationRequest = new SubscribeNotificationRequest();
        notificationRequest.setSubscribeNotificationListObject(subscribeNotifications);
        return notificationRequest;
    }

    private SubscribeNotificationObject SubscribeNotificationObjectBuilder(NonMotorVehicleList objects) {
        SubscribeNotificationObject model = new SubscribeNotificationObject();
        model.setExecuteOperation("1");
        model.setNotificationID(IdWorker.getIdStr(VIIDPublish.class) + "0");
        model.setSubscribeID(publish.getSubscribeId());
        model.setTitle(publish.getTitle());
        model.setTriggerTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        model.setInfoIDs(objects.getNonMotorVehicleObject()
                .stream()
                .map(NonMotorVehicle::getNonMotorVehicleID)
                .collect(Collectors.joining(","))
        );
        model.setNonMotorVehicleObjectList(objects);
        return model;
    }

    public NonMotorVehicleList NonMotorVehicleListBuilder(List<NonMotorVehicle> data) {
        NonMotorVehicleList model = new NonMotorVehicleList();
        model.setNonMotorVehicleObject(data);
        return model;
    }
}
