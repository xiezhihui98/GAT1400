package com.cz.viid.kafka;

import com.cz.viid.be.service.VIIDPublishService;
import com.cz.viid.framework.config.AppConfig;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.context.AppContextHolder;
import com.cz.viid.kafka.listener.*;
import com.cz.viid.framework.domain.entity.VIIDPublish;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.service.VIIDServerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class KafkaStartupService implements ApplicationContextAware {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DefaultKafkaListenerErrorHandler errorHandler = new DefaultKafkaListenerErrorHandler();
    private final StringMessageConverter converter = new StringMessageConverter();
    private final MessageHandlerMethodFactory methodFactory = new DefaultMessageHandlerMethodFactory();
    @Autowired
    KafkaListenerEndpointRegistry registry;
    @Autowired
    KafkaListenerContainerFactory<MessageListenerContainer> factory;
    @Autowired
    VIIDPublishService viidPublishService;
    @Autowired
    VIIDServerService viidServerService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextHolder.setContext(applicationContext);
        viidServerService.afterPropertiesSet();
    }

    public void register(VIIDPublish publish) {
        if (Constants.SUBSCRIBE.STATUS.CONTINUE.equals(publish.getSubscribeStatus())) {
            doRegister(publish);
        }
    }

    public void serverPublishIdle(String serverId) {
        List<VIIDPublish> publishList = viidPublishService.findListByServerId(serverId);
        publishList.forEach(this::startPublish);
    }

    public void serverPublishStop(String serverId) {
        log.info("视图库{}数据发布停止", serverId);
        List<VIIDPublish> publishList = viidPublishService.findListByServerId(serverId);
        publishList.forEach(this::stopPublish);
    }

    public void startPublish(VIIDPublish publish) {
        Set<String> details = Stream.of(StringUtils.split(publish.getSubscribeDetail())).collect(Collectors.toSet());
        for (String detail : details) {
            String id = this.listenerIdBuilder(publish.getSubscribeId(), detail);
            MessageListenerContainer listenerContainer = registry.getListenerContainer(id);
            if (Objects.isNull(listenerContainer)) {
                this.register(publish);
                listenerContainer = registry.getListenerContainer(id);
                if (Objects.isNull(listenerContainer))
                    continue;
            }
            boolean running = listenerContainer.isRunning();
            if (!running) {
                listenerContainer.start();
            }
        }

    }

    public void stopPublish(VIIDPublish publish) {
        Set<String> containerIds = registry.getListenerContainerIds();
        for (String containerId : containerIds) {
            if (containerId.startsWith(publish.getSubscribeId())) {
                MessageListenerContainer listenerContainer = registry.getListenerContainer(containerId);
                if (Objects.nonNull(listenerContainer) && listenerContainer.isRunning()) {
                    listenerContainer.stop();
                }
                registry.unregisterListenerContainer(containerId);
            }
        }
    }

    public void delayPublish(VIIDPublish publish) {
        this.stopPublish(publish);
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(60000);
                this.startPublish(publish);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, AppConfig.GLOBAL_EXECUTOR);
    }

    private synchronized void doRegister(VIIDPublish publish) {
        VIIDServer setting = viidServerService.getCurrentServer();
        Set<String> details = Stream.of(StringUtils.split(publish.getSubscribeDetail())).collect(Collectors.toSet());
        for (String detail : details) {
            String endpointId = this.listenerIdBuilder(publish.getSubscribeId(), detail);
            Set<String> containerIds = registry.getListenerContainerIds();
            if (containerIds.contains(endpointId))
                continue;
            MethodKafkaListenerEndpoint<String, String> endpoint = new MethodKafkaListenerEndpoint<>();
            CustomMessageListener messageListener;
            String prefix;
            switch (detail) {
                case Constants.VIID_SERVER.SUBSCRIBE_DETAIL.DEVICE_DIR:
                    messageListener = new APEMessageListener(publish);
                    prefix = Constants.DEFAULT_TOPIC_PREFIX.APE_DEVICE;
                    break;
                case Constants.VIID_SERVER.SUBSCRIBE_DETAIL.VIDEO_TOLLGATE_DIR:
                    messageListener = new TollgateMessageListener(publish);
                    prefix = Constants.DEFAULT_TOPIC_PREFIX.TOLLGATE_DEVICE;
                    break;
                case Constants.VIID_SERVER.SUBSCRIBE_DETAIL.FACE_INFO:
                    messageListener = new FaceMessageListener(publish);
                    prefix = Constants.DEFAULT_TOPIC_PREFIX.FACE_RECORD;
                    break;
                case Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PERSON_INFO:
                    messageListener = new PersonMessageListener(publish);
                    prefix = Constants.DEFAULT_TOPIC_PREFIX.PERSON_RECORD;
                    break;
                case Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PLATE_INFO:
                    messageListener = new MotorVehicleMessageListener(publish);
                    prefix = Constants.DEFAULT_TOPIC_PREFIX.MOTOR_VEHICLE;
                    break;
                case Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PLATE_MIRCO_INFO:
                    messageListener = new NonMotorVehicleMessageListener(publish);
                    prefix = Constants.DEFAULT_TOPIC_PREFIX.NON_MOTOR_VEHICLE;
                    break;
                case Constants.VIID_SERVER.SUBSCRIBE_DETAIL.RAW:
                    messageListener = new RawMessageListener(publish);
                    prefix = Constants.DEFAULT_TOPIC_PREFIX.RAW;
                    break;
                default:
                    throw new RuntimeException("没有指定resourceClass");
            }
            messageListener.configure();
            Set<String> resourceUri = Arrays.stream(
                    StringUtils.split(publish.getResourceUri(), ",")
            ).map(String::trim).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
            if (resourceUri.contains(setting.getServerId())) {
                endpoint.setTopicPattern(Pattern.compile("^" + prefix + ".*"));
            } else {
                endpoint.setTopics(resourceUri.stream()
                        .map(prefix::concat)
                        .toArray(String[]::new));
            }
            endpoint.setBean(messageListener);
            Method method = ReflectionUtils.findMethod(messageListener.getClass(), "consumer", List.class, Acknowledgment.class);
            if (Objects.isNull(method))
                throw new RuntimeException("消费者没有实现消费方法");
            endpoint.setMethod(method);
            endpoint.setId(endpointId);
            endpoint.setGroupId(publish.getSubscribeId());
            endpoint.setMessagingConverter(converter);
            endpoint.setErrorHandler(errorHandler);
            endpoint.setMessageHandlerMethodFactory(methodFactory);
            if (Objects.nonNull(publish.getReportInterval())) {
                Properties properties = new Properties();
                properties.put("group.instance.id", publish.getSubscribeId());
                properties.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, publish.getReportInterval() * 1000);
                endpoint.setConsumerProperties(properties);
            }

            registry.registerListenerContainer(endpoint, factory);
            log.info("视图库{}注册Kafka消费端点,消费类型{}", publish.getServerId(), detail);
        }
    }

    public String listenerIdBuilder(String subscribeId, String detail) {
        return StringUtils.join(subscribeId, "::", detail);
    }
}
