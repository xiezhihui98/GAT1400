package com.cz.viid.framework.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.viid.framework.domain.entity.VIIDSubscribe;
import com.cz.viid.framework.domain.vo.VIIDSubscribesResponse;
import com.cz.viid.fe.domain.VIIDSubscribeRequest;

public interface VIIDSubscribeService extends IService<VIIDSubscribe> {

    VIIDSubscribe getCacheById(String subscribeId);

    VIIDSubscribesResponse subscribes(VIIDSubscribeRequest request);

    VIIDSubscribesResponse unSubscribes(VIIDSubscribeRequest request);

    Page<VIIDSubscribe> list(VIIDSubscribeRequest request);
}
