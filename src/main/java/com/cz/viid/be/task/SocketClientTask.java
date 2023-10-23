package com.cz.viid.be.task;

import com.alibaba.fastjson.JSONObject;
import com.cz.viid.be.event.WebSocketCloseEvent;
import com.cz.viid.be.socket.client.WebsocketClient;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.context.AppContextHolder;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.service.VIIDServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
public class SocketClientTask {
    private static final Logger log = LoggerFactory.getLogger(KeepaliveTask.class);
    private final Map<String, SocketClient> socketSubscribeMap = new HashMap<>();
    private final Consumer<String> consumer = log::info;
    private final String pingMessage;
    @Resource
    VIIDServerService service;

    public SocketClientTask() {
        JSONObject payload = new JSONObject();
        payload.put("type", Constants.VIID_SERVER.SUBSCRIBE_DETAIL.RAW);
        payload.put("data", List.of("ping"));
        this.pingMessage = payload.toJSONString();
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void socketClientIdle() {
        Set<String> keySet = socketSubscribeMap.keySet();
        for (String key : keySet) {
            SocketClient client = socketSubscribeMap.get(key);
            if (Objects.nonNull(client)) {
                if (!client.future.isDone()) {
                    client.client.send(this.pingMessage);
                }
            }
        }
    }

    @Order(1)
    @PreDestroy
    public void destroy() {
        socketSubscribeMap.forEach((key, value) -> value.client.close());
    }

    public synchronized void serverSocketAdd(VIIDServer server) {
        log.info("启动视图库{}WebSocket连接...", server.getServerId());
        SocketClient socketClient = socketSubscribeMap.get(server.getServerId());
        if (Objects.isNull(socketClient) || socketClient.future.isDone()) {
            this.serverSocketRemove(server.getServerId());
            SocketClient client = new SocketClient();
            VIIDServer setting = service.getCurrentServer();
            String url = String.format("ws://%s:%s/VIID/Subscribe/WebSocket", server.getHost(), server.getPort());
            client.client = new WebsocketClient(url, setting.getServerId(), consumer);
            client.future = CompletableFuture.runAsync(client.client).whenCompleteAsync((unused, throwable) -> {
                if (Objects.nonNull(throwable)) {
                    log.info("WebSocket异常中断,发布关闭事件{}", throwable.getClass());
                    AppContextHolder.publishEvent(new WebSocketCloseEvent(server));
                }
            });
            socketSubscribeMap.put(server.getServerId(), client);
            log.info("启动视图库{}的WebSocket连接完成", server.getServerId());
        }
    }

    public synchronized void serverSocketRemove(String serverId) {
        SocketClient client = socketSubscribeMap.remove(serverId);
        if (Objects.nonNull(client) && !client.future.isDone()) {
            client.client.close();
            log.info("断开视图库{}的WebSocket连接", serverId);
        }
    }

    public static class SocketClient {
        private WebsocketClient client;
        private CompletableFuture<Void> future;

    }
}
