package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.SubscribeListObject;
import com.cz.viid.framework.domain.dto.SubscribeObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class SubscribesRequest {

    @JsonProperty("SubscribeListObject")
    private SubscribeListObject subscribeListObject;

    public static SubscribesRequest create(List<SubscribeObject> subscribes) {
        SubscribesRequest request = new SubscribesRequest();
        SubscribeListObject subscribeListObject = new SubscribeListObject();
        subscribeListObject.setSubscribeObject(subscribes);
        request.setSubscribeListObject(subscribeListObject);
        return request;
    }

    public static SubscribesRequest create(SubscribeObject subscribes) {
        SubscribesRequest request = new SubscribesRequest();
        SubscribeListObject subscribeListObject = new SubscribeListObject();
        subscribeListObject.setSubscribeObject(Collections.singletonList(subscribes));
        request.setSubscribeListObject(subscribeListObject);
        return request;
    }
}
