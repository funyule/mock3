package cn.zaink.mock3.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zaink.mock3.infrastructure.domain.SysMenu;
import cn.zaink.mock3.infrastructure.service.SysMenuService;
import cn.zaink.mock3.infrastructure.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author zaink
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

}




