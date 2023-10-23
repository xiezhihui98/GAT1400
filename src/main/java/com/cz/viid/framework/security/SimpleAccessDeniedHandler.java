package com.cz.viid.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
    private final Logger log = LoggerFactory.getLogger(SimpleAccessDeniedHandler.class);

    @Autowired
    VIIDDigestAuthenticationEntryPoint digestAuthenticationEntryPoint;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        digestAuthenticationEntryPoint.commence(httpServletRequest, httpServletResponse, new AuthenticationException(e.getMessage(), e) {
        });
        log.error("认证过滤链错误: {}", e.getMessage());
    }
}
