package cn.zaink.mock3.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zaink.mock3.infrastructure.domain.SysConfig;
import cn.zaink.mock3.infrastructure.service.SysConfigService;
import cn.zaink.mock3.infrastructure.mapper.SysConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author zaink
*/
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
    implements SysConfigService{

}




