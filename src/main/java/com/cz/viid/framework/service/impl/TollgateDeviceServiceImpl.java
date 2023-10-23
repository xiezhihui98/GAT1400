package com.cz.viid.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.viid.framework.domain.entity.TollgateDevice;
import com.cz.viid.framework.mapper.TollgateDeviceMapper;
import com.cz.viid.framework.service.TollgateDeviceService;
import org.springframework.stereotype.Service;

@Service
public class TollgateDeviceServiceImpl extends ServiceImpl<TollgateDeviceMapper, TollgateDevice>
        implements TollgateDeviceService {
}
