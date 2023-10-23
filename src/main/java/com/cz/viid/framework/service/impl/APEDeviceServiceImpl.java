package com.cz.viid.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.viid.framework.domain.entity.APEDevice;
import com.cz.viid.framework.mapper.APEDeviceMapper;
import com.cz.viid.framework.service.APEDeviceService;
import org.springframework.stereotype.Service;

@Service
public class APEDeviceServiceImpl extends ServiceImpl<APEDeviceMapper, APEDevice>
        implements APEDeviceService {
}
