package com.cz.viid.kafka.listener;

import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.entity.VIIDPublish;
import com.cz.viid.framework.domain.vo.SubscribeNotificationRequest;

import java.util.List;

/**
 * 测试用监听器
 */
public class RawMessageListener extends AbstractMessageListener<String> {

    public RawMessageListener(VIIDPublish publish) {
        super(publish, Constants.VIID_SERVER.SUBSCRIBE_DETAIL.RAW);
    }

    @Override
    public String messageConverter(String value) {
        return value;
    }

    @Override
    public SubscribeNotificationRequest packHandler(List<String> partition) {
        return new SubscribeNotificationRequest();
    }
}
