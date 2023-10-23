package com.cz.viid.be.event;

import com.cz.viid.framework.domain.entity.VIIDServer;
import org.springframework.context.ApplicationEvent;

public class ServerOnlineEvent extends ApplicationEvent {

    public ServerOnlineEvent(VIIDServer server) {
        super(server);
    }

    public VIIDServer getVIIDServer() {
        return (VIIDServer)super.getSource();
    }
}
