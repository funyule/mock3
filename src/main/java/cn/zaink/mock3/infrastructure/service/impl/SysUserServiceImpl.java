package cn.zaink.mock3.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zaink.mock3.infrastructure.domain.SysUser;
import cn.zaink.mock3.infrastructure.service.SysUserService;
import cn.zaink.mock3.infrastructure.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author zaink
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




