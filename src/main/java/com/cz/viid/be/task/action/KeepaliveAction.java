package com.cz.viid.be.task.action;

import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.service.VIIDServerService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class KeepaliveAction {

    private final Cache<String, VIIDServer> KEEPALIVE_VIID_SERVERS = CacheBuilder.newBuilder()
//            .expireAfterAccess(Duration.ofSeconds(120))
            .build();

    @Lazy
    @Autowired
    VIIDServerService viidServerService;

    public VIIDServer get(String serverId) {
        return KEEPALIVE_VIID_SERVERS.getIfPresent(serverId);
    }

    public void refresh(VIIDServer domain) {
        VIIDServer viidServer = KEEPALIVE_VIID_SERVERS.getIfPresent(domain.getServerId());
        if (Objects.nonNull(viidServer)) {
            domain.setOnline(viidServer.getOnline());
            KEEPALIVE_VIID_SERVERS.put(domain.getServerId(), domain);
        }
    }

    public VIIDServer keepalive(String serverId) {
        return KEEPALIVE_VIID_SERVERS.getIfPresent(serverId);
    }

    public void register(VIIDServer domain) {
        KEEPALIVE_VIID_SERVERS.put(domain.getServerId(), domain);
    }

    public void unregister(String serverId) {
        KEEPALIVE_VIID_SERVERS.invalidate(serverId);
    }

    public Map<String, VIIDServer> allServer() {
        return KEEPALIVE_VIID_SERVERS.asMap();
    }

    public long size() {
        return KEEPALIVE_VIID_SERVERS.size();
    }

    public boolean online(String serverId) {
        VIIDServer viewLibrary = this.get(serverId);
        if (Objects.nonNull(viewLibrary)) {
            return viewLibrary.getOnline();
        }
        VIIDServer server = getCurrentServer();
        return StringUtils.equals(server.getServerId(), serverId);
    }

    public VIIDServer getCurrentServer() {
        return viidServerService.getCurrentServer();
    }

    public VIIDServer getCurrentServer(boolean useCache) {
        return viidServerService.getCurrentServer(useCache);
    }
}
