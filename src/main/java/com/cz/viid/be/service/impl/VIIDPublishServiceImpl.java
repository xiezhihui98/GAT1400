package com.cz.viid.be.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.viid.be.service.VIIDPublishService;
import com.cz.viid.framework.domain.entity.VIIDPublish;
import com.cz.viid.framework.mapper.VIIDPublishMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VIIDPublishServiceImpl extends ServiceImpl<VIIDPublishMapper, VIIDPublish>
        implements VIIDPublishService {

    @Override
    public List<VIIDPublish> findListByServerId(String serverId) {
        QueryWrapper<VIIDPublish> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(VIIDPublish::getServerId, serverId);
        return list(wrapper);
    }
}
