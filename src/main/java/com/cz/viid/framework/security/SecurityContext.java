package com.cz.viid.framework.security;

import com.cz.viid.framework.domain.entity.VIIDServer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityContext {

    public static VIIDServer getVIIDServer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) && authentication.getPrincipal() instanceof VIIDServer
                ? (VIIDServer) authentication.getPrincipal() : null;
    }

    public static String getRequestServerId() {
        VIIDServer server = getVIIDServer();
        if (Objects.nonNull(server))
            return server.getServerId();
        throw new RuntimeException("security上下文不存在用户凭证");
    }
}
