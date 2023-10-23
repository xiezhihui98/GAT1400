package com.cz.viid.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.viid.framework.domain.entity.VIIDPublish;

import java.util.List;

public interface VIIDPublishService extends IService<VIIDPublish> {

    List<VIIDPublish> findListByServerId(String serverId);
}
