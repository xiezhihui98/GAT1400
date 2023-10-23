package com.cz.viid.be.task.action;

import com.cz.viid.rpc.VIIDServerClient;
import com.cz.viid.framework.exception.VIIDRuntimeException;
import com.cz.viid.framework.security.DigestData;
import com.cz.viid.framework.domain.dto.DeviceIdObject;
import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.domain.vo.KeepaliveRequest;
import com.cz.viid.framework.domain.vo.RegisterRequest;
import com.cz.viid.framework.domain.vo.UnRegisterRequest;
import com.cz.viid.framework.domain.vo.VIIDBaseResponse;
import com.cz.viid.framework.service.VIIDServerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;


@Component
public class VIIDServerAction {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    VIIDServerClient viidServerClient;
    @Autowired
    VIIDServerService viidServerService;
    @Autowired
    KeepaliveAction keepaliveAction;

    public void register(String domainId) {
        VIIDServer domain = viidServerService.getByIdAndEnabled(domainId);
        if (Objects.nonNull(domain)) {
            try {
                this.register(domain);
                keepaliveAction.register(domain);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public VIIDBaseResponse register(VIIDServer domain) throws IOException {
        VIIDServer setting = viidServerService.getCurrentServer();
        return register(domain, setting);
    }

    public VIIDBaseResponse register(VIIDServer domain, VIIDServer setting) throws IOException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRegisterObject(new DeviceIdObject(setting.getServerId()));
        Response response = null;
        try {
            response = viidServerClient.register(URI.create(domain.httpUrlBuilder()), registerRequest, "");
            if (response.status() == HttpStatus.UNAUTHORIZED.value()) {
                Collection<String> headers = response.headers().get(HttpHeaders.WWW_AUTHENTICATE);
                String header = Optional.ofNullable(headers)
                        .filter(ele -> !ele.isEmpty())
                        .map(LinkedList::new)
                        .map(LinkedList::getFirst)
                        .orElseThrow(() -> new VIIDRuntimeException("未返回正确的 WWW-Authenticate"));
                DigestData digestData = new DigestData(header);
                String authenticate = digestData.toDigestHeader(domain.getUsername(), domain.getAuthenticate(), "POST");
                response = viidServerClient.register(URI.create(domain.httpUrlBuilder()), registerRequest, authenticate);
            }
            VIIDBaseResponse gaBaseResponse = objectMapper.readValue(response.body().asReader(StandardCharsets.UTF_8), VIIDBaseResponse.class);
            Optional.ofNullable(gaBaseResponse)
                    .map(VIIDBaseResponse::getResponseStatusObject)
                    .map(ResponseStatusObject::getStatusCode)
                    .filter("0"::equals)
                    .orElseThrow(() -> new RuntimeException("注册失败"));
            return gaBaseResponse;
        } finally {
            if (Objects.nonNull(response))
                response.close();
        }
    }

    public VIIDBaseResponse unRegister(VIIDServer domain, VIIDServer setting) {
        DeviceIdObject deviceObject = new DeviceIdObject();
        deviceObject.setDeviceId(setting.getServerId());
        UnRegisterRequest request = new UnRegisterRequest();
        request.setUnRegisterObject(deviceObject);
        return viidServerClient.unRegister(URI.create(domain.httpUrlBuilder()), request, setting.getServerId());
    }

    public VIIDBaseResponse keepalive(VIIDServer domain, VIIDServer setting) {
        DeviceIdObject deviceObject = new DeviceIdObject();
        deviceObject.setDeviceId(setting.getServerId());
        KeepaliveRequest request = KeepaliveRequest.builder(deviceObject);
        return viidServerClient.keepalive(URI.create(domain.httpUrlBuilder()), request, setting.getServerId());
    }


}
