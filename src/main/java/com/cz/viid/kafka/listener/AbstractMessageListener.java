package com.cz.viid.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.cz.viid.be.socket.WebSocketEndpoint;
import com.cz.viid.be.task.action.KeepaliveAction;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.context.AppContextHolder;
import com.cz.viid.framework.domain.entity.VIIDPublish;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.domain.vo.SubscribeNotificationRequest;
import com.cz.viid.kafka.KafkaStartupService;
import com.cz.viid.rpc.VIIDServerClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractMessageListener<T> implements CustomMessageListener {
    private final Logger log = LoggerFactory.getLogger(getClass());
    protected VIIDPublish publish;
    protected VIIDServer server;
    protected VIIDServerClient viidServerClient;
    protected KeepaliveAction keepaliveAction;
    private final String type;

    public AbstractMessageListener(VIIDPublish publish, String type) {
        this.publish = publish;
        this.type = type;
    }

    @Override
    public void configure() {
        this.viidServerClient = AppContextHolder.getBean(VIIDServerClient.class);
        this.keepaliveAction = AppContextHolder.getBean(KeepaliveAction.class);
        this.server = keepaliveAction.get(this.publish.getServerId());
    }

    @Override
    public void consumer(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        if (checkConsumeCondition()) {
            List<T> collect = records.stream()
                    .map(ConsumerRecord::value)
                    .map(this::messageConverter)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            try {
                if (Constants.VIID_SERVER.TRANSMISSION.WEBSOCKET.equals(server.getTransmission())) {
                    String serverId = publish.getServerId();
                    JSONObject payload = new JSONObject();
                    payload.put("type", this.type);
                    payload.put("data", collect);
                    WebSocketEndpoint.sendMessageToServerId(serverId, payload.toJSONString());
                } else {
                    SubscribeNotificationRequest request = this.packHandler(collect);
                    viidServerClient.subscribeNotifications(URI.create(publish.getReceiveAddr()),
                            request, publish.getServerId());
                }
            } catch (Exception e) {
                AppContextHolder.getBean(KafkaStartupService.class).delayPublish(this.publish);
                log.warn("视图库{}订阅消费出现错误,休眠一分钟", publish.getServerId());
                log.error(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("消费者离线或不满足消费条件");
        }
        ack.acknowledge();
    }

    public abstract T messageConverter(String value);

    public abstract SubscribeNotificationRequest packHandler(List<T> partition);

    protected boolean checkConsumeCondition() {
        long currentTimestamp = System.currentTimeMillis();
        long start = Objects.isNull(publish.getBeginTime()) ? 0L : publish.getBeginTime().getTime();
        long end = Objects.isNull(publish.getEndTime()) ? 0L : publish.getEndTime().getTime();
        return currentTimestamp > start && currentTimestamp < end && this.keepaliveAction.online(publish.getServerId());
    }

}
