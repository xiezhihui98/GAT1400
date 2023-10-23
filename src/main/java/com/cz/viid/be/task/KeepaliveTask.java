package com.cz.viid.be.task;

import com.cz.viid.be.event.ServerOfflineEvent;
import com.cz.viid.be.event.ServerOnlineEvent;
import com.cz.viid.be.task.action.KeepaliveAction;
import com.cz.viid.be.task.action.VIIDServerAction;
import com.cz.viid.framework.context.AppContextHolder;
import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.domain.vo.VIIDBaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Order(Integer.MIN_VALUE)
@Component
public class KeepaliveTask {
    private static final Logger log = LoggerFactory.getLogger(KeepaliveTask.class);

    @Autowired
    KeepaliveAction keepaliveAction;
    @Autowired
    VIIDServerAction viidServerAction;

    @Scheduled(fixedDelay = 50, timeUnit = TimeUnit.SECONDS)
    public void keepalive() {
        if (keepaliveAction.size() == 0)
            return;
        VIIDServer setting = keepaliveAction.getCurrentServer();
        keepaliveAction.allServer().forEach((key, value) -> {
            try {
                if (Boolean.TRUE.equals(value.getKeepalive())) {
                    log.info("主动保活: {}", key);
                    if (!Boolean.TRUE.equals(value.getOnline())) {
                        boolean success = success(viidServerAction.register(value, setting));
                        if (!success)
                            return;
                        value.setOnline(Boolean.TRUE);
                        AppContextHolder.publishEvent(new ServerOnlineEvent(value));
                    }
                    viidServerAction.keepalive(value, setting);
                }
            } catch (Exception e) {
                value.setOnline(false);
                AppContextHolder.publishEvent(new ServerOfflineEvent(value.getServerId()));
                log.warn("保活失败: {}, 错误消息: {}", key, e.getMessage());
            }
        });
    }

    @Order(Integer.MIN_VALUE)
    @PreDestroy
    public void destroy() {
        if (keepaliveAction.size() == 0)
            return;
        VIIDServer setting = keepaliveAction.getCurrentServer(false);
        keepaliveAction.allServer().forEach((key, value) -> {
            try {
                log.info("主动注销: {}", key);
                viidServerAction.unRegister(value, setting);
            } catch (Exception e) {
                //调用注销辉有403
                log.warn("注销失败: {}, 错误消息: {}", key, e.getMessage());
            } finally {
                keepaliveAction.unregister(value.getServerId());
            }
        });
    }

    private boolean success(VIIDBaseResponse response) {
        return Optional.ofNullable(response)
                .map(VIIDBaseResponse::getResponseStatusObject)
                .map(ResponseStatusObject::getStatusCode)
                .map("0"::equals)
                .orElse(false);
    }
}
