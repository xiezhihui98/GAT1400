package com.cz.viid.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class VIIDDigestAuthenticationEntryPoint extends DigestAuthenticationEntryPoint {
    private final Logger log = LoggerFactory.getLogger(VIIDDigestAuthenticationEntryPoint.class);

    public VIIDDigestAuthenticationEntryPoint() {
        super();
        super.setKey(DigestData.DIGEST_KEY);
        super.setRealmName(DigestData.DIGEST_REALM);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        long expiryTime = System.currentTimeMillis() + (long)(this.getNonceValiditySeconds() * 1000);
        String signatureValue = DigestAuthUtils.md5Hex(expiryTime + ":" + this.getKey());
        String nonceValue = expiryTime + ":" + signatureValue;
        String nonceValueBase64 = new String(Base64.getEncoder().encode(nonceValue.getBytes()));
        String authenticateHeader = "Digest realm=\"" + this.getRealmName() + "\", qop=\"auth\", nonce=\"" + nonceValueBase64 + "\", opaque=\"3c734f53df054bbe96938930c62ef1b1\"";
//        System.out.println(new DigestData(authenticateHeader).toDigestHeader("xzh_admin", "xzh_admin", "POST"));
        response.addHeader("WWW-Authenticate", authenticateHeader);
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.warn("未携带用户标识请求: {}, client: {}", request.getRequestURI(), request.getRemoteHost());
    }
}
