package com.cz.viid.be.event;

import com.cz.viid.be.task.SocketClientTask;
import com.cz.viid.be.task.action.KeepaliveAction;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.kafka.KafkaStartupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class AppEventListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    KeepaliveAction keepaliveAction;
    @Resource
    KafkaStartupService kafkaStartupService;
    @Resource
    SocketClientTask socketClientTask;

    @EventListener(WebSocketCloseEvent.class)
    public void websocketClose(WebSocketCloseEvent event) {
        log.info("接收到websocketClose事件,标记视图库下线");
        VIIDServer server = event.getVIIDServer();
        VIIDServer viidServer = keepaliveAction.get(server.getServerId());
        if (Objects.nonNull(viidServer)) {
            viidServer.setOnline(false);
            kafkaStartupService.serverPublishStop(server.getServerId());
        }
    }

    @EventListener(ServerOnlineEvent.class)
    public void serverRegister(ServerOnlineEvent event) {
        log.info("接收到视图库注册事件,处理WebSocket连接和数据发布");
        VIIDServer viidServer = event.getVIIDServer();
        if (Constants.VIID_SERVER.SERVER_CATEGORY.DOWN_DOMAIN.equals(viidServer.getCategory()) &&
                Constants.VIID_SERVER.TRANSMISSION.WEBSOCKET.equals(viidServer.getTransmission())) {
            socketClientTask.serverSocketAdd(viidServer);
        }
        kafkaStartupService.serverPublishIdle(viidServer.getServerId());
    }

    @EventListener(ServerOfflineEvent.class)
    public void serverUnRegister(ServerOfflineEvent event) {
        log.info("接收到视图库注销事件,停止数据发布");
        kafkaStartupService.serverPublishStop(event.getServerId());
    }
}
