package com.cz.viid.be.service;

import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.dto.SubscribeObject;

public interface SubscribeService {

    ResponseStatusObject upsertSubscribes(SubscribeObject subscribeObject);

    ResponseStatusObject cancelSubscribes(String subscribeId);
}
