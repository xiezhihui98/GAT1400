package com.cz.viid.be.api;

import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.aspect.Log;
import com.cz.viid.framework.exception.VIIDRuntimeException;
import com.cz.viid.framework.domain.dto.*;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.domain.entity.VIIDSubscribe;
import com.cz.viid.framework.domain.vo.*;
import com.cz.viid.framework.security.DigestData;
import com.cz.viid.framework.security.VIIDDigestAuthenticationEntryPoint;
import com.cz.viid.be.service.VIIDNotificationService;
import com.cz.viid.be.service.SubscribeService;
import com.cz.viid.framework.service.VIIDServerService;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class VIIDServerApi {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VIIDNotificationService VIIDNotificationService;
    @Autowired
    SubscribeService subscribeService;
    @Resource
    VIIDDigestAuthenticationEntryPoint digestAuthenticationEntryPoint;
    @Autowired
    VIIDServerService viidServerService;

    @Log("注册接口")
    @PostMapping(value = "/VIID/System/Register")
    public VIIDBaseResponse register(@RequestBody RegisterRequest request,
                                   HttpServletRequest servletRequest,
                                   HttpServletResponse servletResponse) throws IOException {
        try {
            String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isBlank(authorization))
                throw new VIIDRuntimeException("注册请求未携带凭证");
            DigestData digestData = new DigestData(authorization);
            VIIDServer viidServer = viidServerService.register(request, digestData);
            return new VIIDBaseResponse(
                    new ResponseStatusObject(viidServer.getServerId(),
                            "/VIID/System/Register", "0", "注册成功")
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            digestAuthenticationEntryPoint.commence(servletRequest, servletResponse, null);
        }
        return null;
    }

    @Log("注销接口")
    @PostMapping("/VIID/System/UnRegister")
    public VIIDBaseResponse unRegister(@RequestBody UnRegisterRequest request) {
        String deviceId = request.getUnRegisterObject().getDeviceId();
        viidServerService.unRegister(deviceId);
        return new VIIDBaseResponse(new ResponseStatusObject(deviceId, "/VIID/System/UnRegister", "0", "注销成功"));
    }

    @Log("保活接口")
    @PostMapping("/VIID/System/Keepalive")
    public VIIDBaseResponse keepalive(@RequestBody KeepaliveRequest request,
                                    HttpServletRequest servletRequest) {
        ResponseStatusObject statusObject = viidServerService.keepalive(request.getKeepaliveObject().getDeviceId());
        return new VIIDBaseResponse(statusObject);
    }

    @Log("校时接口")
    @GetMapping("/VIID/System/Time")
    public SystemTimeResponse systemTime() {
        return new SystemTimeResponse(viidServerService.getCurrentServer().getServerId());
    }

    @Log("订阅通知接口")
    @PostMapping("/VIID/SubscribeNotifications")
    public VIIDNotificationResponse subscribeNotifications(@RequestBody SubscribeNotificationRequest request,
                                                                HttpServletRequest servletRequest) {
        List<ResponseStatusObject> responseStatusObjectList = new ArrayList<>();
        List<SubscribeNotificationObject> subscribeNotificationObject = Optional.of(request)
                .map(SubscribeNotificationRequest::getSubscribeNotificationListObject)
                .map(SubscribeNotifications::getSubscribeNotificationObject)
                .orElse(Collections.emptyList());
        final String url = servletRequest.getRequestURI();
        for (SubscribeNotificationObject notificationObject : subscribeNotificationObject) {
            VIIDSubscribe subscribe = VIIDNotificationService.getSubscribeById(notificationObject.getSubscribeID());
            if (Objects.isNull(subscribe))
                continue;
            ResponseStatusObject responseStatusObject;
            try {
                Set<String> details = Stream.of(StringUtils.split(subscribe.getSubscribeDetail(), ","))
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toSet());
                for (String detail : details) {
                    if (Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PERSON_INFO.equals(detail)
                            && Objects.nonNull(notificationObject.getPersonObjectList())) {
                        responseStatusObject = VIIDNotificationService.personHandler(notificationObject, subscribe);
                    } else if (Constants.VIID_SERVER.SUBSCRIBE_DETAIL.PLATE_INFO.equals(detail)
                            && Objects.nonNull(notificationObject.getMotorVehicleObjectList())) {
                        responseStatusObject = VIIDNotificationService.motorVehicleHandler(notificationObject, subscribe);
                    }  else {
                        responseStatusObject = new ResponseStatusObject(null, "0", "操作成功");
                    }
                    responseStatusObject.setRequestUrl(url);
                    responseStatusObjectList.add(responseStatusObject);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                responseStatusObject = new ResponseStatusObject(null, "500", "操作失败");
                responseStatusObject.setRequestUrl(url);
                responseStatusObjectList.add(responseStatusObject);
            }
        }
        return new VIIDNotificationResponse(new ResponseStatusListObject(responseStatusObjectList));
    }
}
