package com.cz.viid.utils;

import com.cz.viid.framework.config.Constants;
import com.cz.viid.framework.domain.dto.APEObject;
import com.cz.viid.framework.domain.dto.SubscribeObject;
import com.cz.viid.framework.domain.dto.TollgateObject;
import com.cz.viid.framework.domain.entity.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class StructCodec {

    /**
     * 卡口订阅构建
     * 备注: SubscribeDetail 有时候无法订阅单个资源, 可以多加一个,标识多个资源 例: "7,"
     * @param resourceUri 卡口ID, 多个ID使用,分隔
     * @param title      订阅标题
     * @param server     服务节点
     * @param subscribeDetail 订阅资源类型  7=视频卡口目录(卡口设备) 13=车辆数据(卡口过车)
     * @return 订阅对象
     */
    public static SubscribeObject inputSubscribeBuilder(String resourceUri, String title,
                                                        String subscribeDetail, VIIDServer server) {
        SubscribeObject subscribe = new SubscribeObject();
        subscribe.setSubscribeId(randomSubscriberId(server.getServerId()));
        subscribe.setTitle(title);
        if (Constants.VIID_SERVER.SUBSCRIBE_DETAIL.VIDEO_TOLLGATE_DIR.equals(subscribeDetail)
                || Constants.VIID_SERVER.SUBSCRIBE_DETAIL.DEVICE_DIR.equals(subscribeDetail)) {
            //TODO 防止低版本海口平台bug
            subscribe.setSubscribeDetail(subscribeDetail + ",");
        } else {
            subscribe.setSubscribeDetail(subscribeDetail);
        }
        subscribe.setResourceUri(resourceUri);
        subscribe.setApplicationName("bdmap_yt");
        subscribe.setApplicationOrg("d1");
        subscribe.setBeginTime(DateUtils.addHours(new Date(), -8));
        try {
            subscribe.setEndTime(DateUtils.parseDate("2099-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            subscribe.setBeginTime(DateUtils.addYears(new Date(), 50));
        }
        if (Constants.VIID_SERVER.SUBSCRIBE_DETAIL.RAW.equals(subscribeDetail)) {
            subscribe.setReceiveAddr(String.format("ws://%s:%s/VIID/Subscribe/WebSocket", server.getHost(), server.getPort()));
        } else {
            subscribe.setReceiveAddr(server.httpUrlBuilder() + "/VIID/SubscribeNotifications");
        }
        subscribe.setReportInterval(3);
        subscribe.setReason("测试" + title);
        subscribe.setOperateType(0);
        subscribe.setSubscribeStatus(0);
        //0=卡口,1=设备,4=视图库
        subscribe.setResourceClass(0);
        subscribe.setResultImageDeclare("-1");
        subscribe.setResultFeatureDeclare(1);
        return subscribe;
    }

    public static SubscribeObject castSubscribe(VIIDPublish entity) {
        if (Objects.nonNull(entity)) {
            SubscribeObject subscribe = new SubscribeObject();
            BeanUtils.copyProperties(entity, subscribe);
            return subscribe;
        }
        return null;
    }

    public static VIIDSubscribe castSubscribe(SubscribeObject subscribe) {
        VIIDSubscribe entity = new VIIDSubscribe();
        BeanUtils.copyProperties(subscribe, entity);
        return entity;
    }

    public static VIIDPublish castPublish(SubscribeObject subscribe) {
        VIIDPublish entity = new VIIDPublish();
        BeanUtils.copyProperties(subscribe, entity);
        return entity;
    }

    public static APEDevice castApeDevice(APEObject src) {
        APEDevice entity = new APEDevice();
        entity.setApeId(src.getApeID());
        entity.setName(src.getName());
        entity.setModel(src.getModel());
        entity.setIpAddr(src.getIPAddr());
        entity.setPort(src.getPort());
        entity.setLongitude(src.getLongitude());
        entity.setLatitude(src.getLatitude());
        entity.setPlaceCode(src.getPlaceCode());
        entity.setIsOnline(src.getIsOnline());
        entity.setUserId(src.getUserId());
        entity.setPassword(src.getPassword());
        entity.setFunctionType(src.getFunctionType());
        return entity;
    }

    public static SubscribeObject castPublish(VIIDPublish publish) {
        SubscribeObject subscribe = new SubscribeObject();
        BeanUtils.copyProperties(publish, subscribe);
        return subscribe;
    }

    public static TollgateDevice castTollgateDevice(TollgateObject src) {
        TollgateDevice device = new TollgateDevice();
        device.setTollgateID(src.getTollgateID());
        device.setLaneNum(src.getLaneNum());
        device.setLatitude(src.getLatitude());
        device.setLongitude(src.getLongitude());
        device.setName(src.getName());
        device.setOrgCode(src.getOrgCode());
        device.setPlaceCode(src.getPlaceCode());
        device.setStatus(src.getStatus());
        device.setTollgateCat(src.getTollgateCat());
        device.setTollgateUsage(src.getTollgateUsage());
        return device;
    }

    private static String randomSubscriberId(String organization) {
        if (StringUtils.length(organization) < 6)
            organization = "431000";
        String prefix = organization.substring(0, 6);
        String suffix = "000000";
        String op = "03";
        String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        String random = RandomStringUtils.randomNumeric(7);
        // 6+6+2+12+7
        return prefix + suffix + op + timestamp + random;
    }

}
