package com.cz.viid.framework.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.viid.framework.security.DigestData;
import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.domain.vo.RegisterRequest;
import com.cz.viid.fe.domain.SearchDataRequest;

public interface VIIDServerService extends IService<VIIDServer> {

    void afterPropertiesSet();
    VIIDServer getByIdAndEnabled(String id);

    VIIDServer getCurrentServer(boolean useCache);
    VIIDServer getCurrentServer();

    VIIDServer register(RegisterRequest request, DigestData digestData);

    boolean unRegister(String deviceId);

    ResponseStatusObject keepalive(String deviceId);

    Page<VIIDServer> list(SearchDataRequest request);

    boolean upsert(VIIDServer request);

    void changeDomain(String source);

    boolean maintenance(VIIDServer viidServer);
}
