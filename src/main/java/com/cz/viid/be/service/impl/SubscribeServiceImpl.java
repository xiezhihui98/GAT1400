package com.cz.viid.be.service.impl;

import com.cz.viid.be.service.SubscribeService;
import com.cz.viid.be.service.VIIDPublishService;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.dto.SubscribeObject;
import com.cz.viid.framework.domain.entity.VIIDPublish;
import com.cz.viid.framework.security.SecurityContext;
import com.cz.viid.kafka.KafkaStartupService;
import com.cz.viid.utils.StructCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    final Logger log = LoggerFactory.getLogger(SubscribeServiceImpl.class);

    @Autowired
    VIIDPublishService viidPublishService;
    @Autowired
    KafkaStartupService kafkaStartupService;

    @Override
    public ResponseStatusObject upsertSubscribes(SubscribeObject subscribeObject) {
        if (Constants.SUBSCRIBE.OPERATE_TYPE.CANCEL.equals(subscribeObject.getOperateType())) {
            return cancelSubscribes(subscribeObject.getSubscribeId());
        }
        return new ResponseStatusObject(null, "500", message);
    }

    @Override
    public ResponseStatusObject cancelSubscribes(String subscribeId) {

        return new ResponseStatusObject("/VIID/Subscribes", "500", "操作失败");
    }
}
