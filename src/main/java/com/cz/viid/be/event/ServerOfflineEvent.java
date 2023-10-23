package com.cz.viid.be.event;

import org.springframework.context.ApplicationEvent;

public class ServerOfflineEvent extends ApplicationEvent {

    public ServerOfflineEvent(String serverId) {
        super(serverId);
    }

    public String getServerId() {
        return (String) super.getSource();
    }
}
