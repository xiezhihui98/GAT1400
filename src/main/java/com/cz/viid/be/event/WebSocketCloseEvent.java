package com.cz.viid.be.event;

import com.cz.viid.framework.domain.entity.VIIDServer;
import org.springframework.context.ApplicationEvent;

public class WebSocketCloseEvent extends ApplicationEvent {

    public WebSocketCloseEvent(VIIDServer server) {
        super(server);
    }

    public VIIDServer getVIIDServer() {
        return (VIIDServer)super.getSource();
    }
}
