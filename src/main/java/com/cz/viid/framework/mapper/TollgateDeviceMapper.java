package com.cz.viid.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.viid.framework.domain.entity.TollgateDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TollgateDeviceMapper extends BaseMapper<TollgateDevice> {

    List<TollgateDevice> findUnSubscribeDevice(@Param("nodeId") String nodeId,
                                               @Param("orgCode") String orgCode);
}
