package com.cz.viid.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.viid.fe.domain.VIIDSubscribeRequest;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.config.RedisConfig;
import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.dto.SubscribeObject;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.domain.entity.VIIDSubscribe;
import com.cz.viid.framework.domain.vo.SubscribesRequest;
import com.cz.viid.framework.domain.vo.VIIDSubscribesResponse;
import com.cz.viid.framework.mapper.VIIDSubscribeMapper;
import com.cz.viid.framework.service.VIIDServerService;
import com.cz.viid.framework.service.VIIDSubscribeService;
import com.cz.viid.rpc.VIIDServerClient;
import com.cz.viid.utils.StructCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class VIIDSubscribeServiceImpl extends ServiceImpl<VIIDSubscribeMapper, VIIDSubscribe>
        implements VIIDSubscribeService {

    @Resource
    VIIDServerService viidServerService;
    @Resource
    VIIDServerClient viidServerClient;

    @Cacheable(cacheNames = RedisConfig.CACHE_15MIN,
            key = "'SubscribeEntityService::getCacheById::' + #subscribeId",
            unless = "#result == null")
    @Override
    public VIIDSubscribe getCacheById(String subscribeId) {
        return getById(subscribeId);
    }

    @Override
    public VIIDSubscribesResponse subscribes(VIIDSubscribeRequest request) {
        VIIDServer setting = viidServerService.getCurrentServer();
        VIIDServer domain = viidServerService.getById(request.getServerId());
        String resourceUri = assembleResourceUri(request);

        SubscribeObject subscribe = StructCodec.inputSubscribeBuilder(resourceUri, request.getTitle(),
                 request.getSubscribeDetail(), domain);
        subscribe.setResourceClass(request.getResourceClass());
        //发送订阅请求
        URI uri = URI.create(domain.httpUrlBuilder());
        VIIDSubscribesResponse response = viidServerClient.addSubscribes(uri,
                SubscribesRequest.create(subscribe), setting.getServerId());
        VIIDSubscribe entity = StructCodec.castSubscribe(subscribe);
        entity.setSubscribeDetail(request.getSubscribeDetail());
        entity.setServerId(request.getServerId());
        this.save(entity);
        return VIIDSubscribesResponse.create(response.getResponseStatusListObject().getResponseStatusObject());
    }

    @Override
    public VIIDSubscribesResponse unSubscribes(VIIDSubscribeRequest request) {
        VIIDServer setting = viidServerService.getCurrentServer();
        VIIDSubscribe subscribe = this.getById(request.getSubscribeId());
        if (Objects.isNull(subscribe))
            return VIIDSubscribesResponse.create(
                    Collections.singletonList(new ResponseStatusObject("", "500", "订阅不存在"))
            );
        VIIDServer domain = viidServerService.getById(subscribe.getServerId());
        URI uri = URI.create(domain.httpUrlBuilder());
        List<String> subscribeIds = Collections.singletonList(subscribe.getSubscribeId());
        VIIDSubscribesResponse response = viidServerClient.cancelSubscribes(uri,
                subscribeIds, setting.getServerId());
        this.removeByIds(subscribeIds);
        return VIIDSubscribesResponse.create(response.getResponseStatusListObject().getResponseStatusObject());
    }

    private String assembleResourceUri(VIIDSubscribeRequest request) {
        if (Constants.VIID_SERVER.RESOURCE_CLASS.VIEW_LIBRARY.equals(request.getResourceClass())) {
            return request.getServerId();
        } else if (Constants.VIID_SERVER.RESOURCE_CLASS.TOLLGATE.equals(request.getResourceClass())) {
            return String.join(",", request.getResourceUri());
        } else {
            throw new RuntimeException("ResourceClass输入错误");
        }
    }

    @Override
    public Page<VIIDSubscribe> list(VIIDSubscribeRequest request) {
        QueryWrapper<VIIDSubscribe> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getServerId())) {
            queryWrapper.lambda().eq(VIIDSubscribe::getServerId, request.getServerId());
        }
        if (StringUtils.isNotBlank(request.getSubscribeDetail())) {
            queryWrapper.lambda().eq(VIIDSubscribe::getSubscribeDetail, request.getSubscribeDetail());
        }
        if (StringUtils.isNotBlank(request.getTitle())) {
            queryWrapper.lambda().likeRight(VIIDSubscribe::getTitle, request.getTitle());
        }
        queryWrapper.lambda().orderByDesc(VIIDSubscribe::getCreateTime);
        return super.page(request.pageable(), queryWrapper);
    }
}
