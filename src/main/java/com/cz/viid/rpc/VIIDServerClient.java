package com.cz.viid.rpc;

import com.cz.viid.framework.domain.vo.*;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@FeignClient(name = "VIIDServerClient", url = "http://127.0.0.254")
public interface VIIDServerClient {

    @PostMapping("/VIID/System/Register")
    Response register(URI uri, @RequestBody RegisterRequest request,
                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);

    @PostMapping("/VIID/System/UnRegister")
    VIIDBaseResponse unRegister(URI uri, @RequestBody UnRegisterRequest request,
                                @RequestHeader("User-Identify") String authorization);

    @PostMapping("/VIID/System/Keepalive")
    VIIDBaseResponse keepalive(URI uri, @RequestBody KeepaliveRequest keepaliveRequest,
                             @RequestHeader("User-Identify") String userIdentifier);

    @PostMapping("/VIID/Subscribes")
    VIIDSubscribesResponse addSubscribes(URI uri, @RequestBody SubscribesRequest request,
                                         @RequestHeader("User-Identify") String userIdentifier);

    @PutMapping("/VIID/Subscribes")
    VIIDSubscribesResponse updateSubscribes(URI uri, @RequestBody SubscribesRequest request,
                                        @RequestHeader("User-Identify") String userIdentifier);

    @DeleteMapping("/VIID/Subscribes")
    VIIDSubscribesResponse cancelSubscribes(URI uri, @RequestParam("IDList") List<String> resourceIds,
                                        @RequestHeader("User-Identify") String userIdentifier);

//    @PostMapping("/VIID/SubscribeNotifications")
    @PostMapping
    VIIDNotificationResponse subscribeNotifications(URI uri, @RequestBody SubscribeNotificationRequest request,
                                                         @RequestHeader("User-Identify") String userIdentifier);
}
