package com.cz.viid.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.Person;
import com.cz.viid.framework.domain.dto.PersonList;
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
 * 自动采集的人员消息监听器
 * from 消费kafka topic的自动采集的人员数据
 * to PublishEntity定义的视图库回调
 */
public class PersonMessageListener extends AbstractMessageListener<Person> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public PersonMessageListener(VIIDPublish publish) {
        super(publish, Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PERSON_INFO);
    }

    @Override
    public Person messageConverter(String value) {
        return Optional.of(value)
                .map(ele -> JSONObject.parseObject(ele, Person.class))
                .filter(ele -> StringUtils.isNotBlank(ele.getPersonID()))
                .orElse(null);
    }

    @Override
    public SubscribeNotificationRequest packHandler(List<Person> partition) {
        PersonList personList = new PersonList();
        personList.setPersonObject(partition);

        SubscribeNotificationObject notificationObject = new SubscribeNotificationObject();
        notificationObject.setExecuteOperation("1");
        notificationObject.setNotificationID(IdWorker.getIdStr(VIIDPublish.class) + "0");
        notificationObject.setSubscribeID(publish.getSubscribeId());
        notificationObject.setTitle(publish.getTitle());
        notificationObject.setTriggerTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        notificationObject.setInfoIDs(partition
                .stream()
                .map(Person::getPersonID)
                .collect(Collectors.joining(","))
        );
        notificationObject.setPersonObjectList(personList);

        long size = partition.size();
        SubscribeNotifications notifications = new SubscribeNotifications();
        notifications.setSubscribeNotificationObject(Collections.singletonList(notificationObject));
        notifications.setPageRecordNum(size);
        notifications.setTotalNum(size);

        SubscribeNotificationRequest notificationRequest = new SubscribeNotificationRequest();
        notificationRequest.setSubscribeNotificationListObject(notifications);
        return notificationRequest;
    }
}
