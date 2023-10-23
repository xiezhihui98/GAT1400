package com.cz.viid.framework.aspect;

import com.cz.viid.framework.context.LogHolder;
import com.cz.viid.framework.security.SecurityContext;
import com.cz.viid.framework.domain.dto.OperationLog;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

@ConditionalOnExpression("${server.enable.aspect:true}")
@Aspect
@Component
public class LogAspect {
    private final Logger log = LoggerFactory.getLogger(LogAspect.class);
    @Autowired
    ObjectMapper objectMapper;

    @Pointcut("@annotation(com.cz.viid.framework.aspect.Log)")
    public void logPointCut() {
    }

    @AfterReturning(pointcut = "logPointCut()", returning = "response")
    public void doAfter(JoinPoint joinPoint, Object response) {
        handleLog(joinPoint, null, response);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    private void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            HttpServletRequest request = httpServletRequest();
            OperationLog operationLog = new OperationLog();
            operationLog.setPointer(controllerLog.value());
            operationLog.setTimestamp(new Date());
            operationLog.setMethod(request.getMethod());
            operationLog.setResource(request.getRequestURI());
            operationLog.setClient(getClientIp(request));
            if (Objects.nonNull(jsonResult)) {
                operationLog.setResponse(objectMapper.writeValueAsString(jsonResult));
            }
            if (Objects.nonNull(e)) {
                operationLog.setException(e.getMessage());
            }
            VIIDServer loginUser = SecurityContext.getVIIDServer();
            if (Objects.nonNull(loginUser)) {
                operationLog.setUserId(loginUser.getServerId());
            }
            LogHolder.add(operationLog);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }


    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

    private String getClientIp(HttpServletRequest request) {
        if (StringUtils.hasText(request.getHeader("X-Forwarded-For"))) {
            return request.getHeader("X-Forwarded-For");
        } else {
            return request.getRemoteAddr();
        }
    }

    private HttpServletRequest httpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(requestAttributes, "servlet container RequestContextHolder not have HttpServletRequest");
        return requestAttributes.getRequest();
    }
}
