package com.cz.viid.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.viid.be.event.ServerOnlineEvent;
import com.cz.viid.be.event.ServerOfflineEvent;
import com.cz.viid.be.task.action.KeepaliveAction;
import com.cz.viid.fe.domain.SearchDataRequest;
import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.config.RedisConfig;
import com.cz.viid.framework.context.AppContextHolder;
import com.cz.viid.framework.domain.dto.DeviceIdObject;
import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.cz.viid.framework.domain.entity.VIIDServer;
import com.cz.viid.framework.domain.vo.RegisterRequest;
import com.cz.viid.framework.exception.VIIDRuntimeException;
import com.cz.viid.framework.mapper.VIIDServerMapper;
import com.cz.viid.framework.security.DigestData;
import com.cz.viid.framework.service.VIIDServerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VIIDServerServiceImpl extends ServiceImpl<VIIDServerMapper, VIIDServer>
        implements VIIDServerService {

    @Autowired
    KeepaliveAction keepaliveAction;

    /**
     * 初始化所有需要双向保活的视图库
     *
     */
    @Override
    public void afterPropertiesSet() {
        List<VIIDServer> list = list();
        list.stream()
                .filter(ele -> Constants.VIID_SERVER.SERVER_CATEGORY.GATEWAY.equals(ele.getCategory()))
                .findFirst()
                .orElseGet(() -> {
                    VIIDServer server = new VIIDServer();
                    server.setServerId("admin");
                    server.setServerName("初始化视图库");
                    server.setCategory(Constants.VIID_SERVER.SERVER_CATEGORY.GATEWAY);
                    server.setEnabled(true);
                    server.setScheme("http");
                    server.setKeepalive(false);
                    server.setHost("127.0.0.1");
                    server.setPort(8121);
                    server.setUsername("admin");
                    server.setAuthenticate("admin");
                    save(server);
                    return server;
                });
        list.stream()
                .filter(ele -> !Constants.VIID_SERVER.SERVER_CATEGORY.GATEWAY.equals(ele.getCategory()))
                .forEach(ele -> {
                    ele.setOnline(false);
                    keepaliveAction.register(ele);
                });
    }

    @Override
    public VIIDServer getByIdAndEnabled(String id) {
        QueryWrapper<VIIDServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(VIIDServer::getServerId, id);
        queryWrapper.lambda().eq(VIIDServer::getEnabled, true);
        return super.getOne(queryWrapper);
    }


    @Override
    public VIIDServer getCurrentServer() {
        return getCurrentServer(true);
    }

    @Cacheable(cacheNames = RedisConfig.CACHE_10MIN, key = "'CACHE_SERVER_ME'", condition = "#useCache == true", unless = "#result == null")
    @Override
    public VIIDServer getCurrentServer(boolean useCache) {
        QueryWrapper<VIIDServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(VIIDServer::getCategory, Constants.VIID_SERVER.SERVER_CATEGORY.GATEWAY);
        return super.getOne(queryWrapper);
    }

    @Override
    public VIIDServer register(RegisterRequest request, DigestData digestData) {
        String serverId = Optional.ofNullable(request)
                .map(RegisterRequest::getRegisterObject)
                .map(DeviceIdObject::getDeviceId)
                .orElse(null);
        VIIDServer viidServer = this.getByIdAndEnabled(serverId);
        if (Objects.isNull(viidServer))
            throw new VIIDRuntimeException("未登记视图库设备ID");
        digestData.setUsername(viidServer.getUsername());
        digestData.validateAndDecode(DigestData.DIGEST_KEY, DigestData.DIGEST_REALM);
        viidServer.setOnline(true);
        AppContextHolder.publishEvent(new ServerOnlineEvent(viidServer));
        keepaliveAction.register(viidServer);
        return viidServer;
    }

    @Override
    public boolean unRegister(String deviceId) {
        keepaliveAction.unregister(deviceId);
        AppContextHolder.publishEvent(new ServerOfflineEvent(deviceId));
        return true;
    }

    @Override
    public ResponseStatusObject keepalive(String deviceId) {
        VIIDServer viidServer = keepaliveAction.keepalive(deviceId);
        ResponseStatusObject statusObject;
        if (Objects.nonNull(viidServer)) {
            statusObject = new ResponseStatusObject("/VIID/System/Keepalive", "0", "保活成功");
        } else {
            statusObject = new ResponseStatusObject("/VIID/System/Keepalive", "500", "保活失败,请先注册");
        }
        return statusObject;
    }

    @Override
    public Page<VIIDServer> list(SearchDataRequest request) {
        QueryWrapper<VIIDServer> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getServerId())) {
            queryWrapper.lambda().eq(VIIDServer::getServerId, request.getServerId());
        }
        if (StringUtils.isNotBlank(request.getServerName())) {
            queryWrapper.lambda().likeRight(VIIDServer::getServerName, request.getServerName());
        }
        queryWrapper.lambda().eq(VIIDServer::getCategory, Constants.VIID_SERVER.SERVER_CATEGORY.DOWN_DOMAIN);
        queryWrapper.lambda().orderByDesc(VIIDServer::getCreateTime);
        Page<VIIDServer> page = super.page(request.pageable(), queryWrapper);
        page.getRecords().forEach(server -> server.setOnline(keepaliveAction.online(server.getServerId())));
        return page;
    }

    @Override
    public boolean upsert(VIIDServer request) {
        VIIDServer old = getById(request.getServerId());
        boolean res;
        if (Objects.nonNull(old)) {
            res = super.updateById(request);
            this.changeDomain(request.getServerId());
        } else {
            request.setEnabled(false);
            request.setCategory(Constants.VIID_SERVER.SERVER_CATEGORY.DOWN_DOMAIN);
            res = this.save(request);
        }
        return res;
    }

    @Override
    public void changeDomain(String source) {
        //TODO ...
    }

    @Override
    public boolean maintenance(VIIDServer viidServer) {
        VIIDServer me = this.getCurrentServer();
        if (Objects.nonNull(me)) {
            viidServer.setServerId(me.getServerId());
        }
        viidServer.setCategory(Constants.VIID_SERVER.SERVER_CATEGORY.GATEWAY);
        return saveOrUpdate(viidServer);
    }
}
