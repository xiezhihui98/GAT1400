package com.cz.viid.framework.security;

import com.cz.viid.be.task.action.KeepaliveAction;
import com.cz.viid.framework.domain.entity.VIIDServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    KeepaliveAction keepaliveAction;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdentify = request.getHeader("User-Identify");
        if (Objects.isNull(authentication) && StringUtils.hasText(userIdentify)) {
            VIIDServer loginUser = keepaliveAction.get(userIdentify);
            if (Objects.nonNull(loginUser) && Boolean.TRUE.equals(loginUser.getOnline())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, Collections.emptyList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
