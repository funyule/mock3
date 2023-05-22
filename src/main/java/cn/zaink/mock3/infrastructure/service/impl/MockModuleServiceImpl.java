package cn.zaink.mock3.infrastructure.service.impl;

import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.mapper.MockModuleMapper;
import cn.zaink.mock3.infrastructure.service.MockModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zaink
 * @description 针对表【mock_module(模块url)】的数据库操作Service实现
 * @createDate 2023-05-15 23:13:46
 */
@Service
public class MockModuleServiceImpl extends ServiceImpl<MockModuleMapper, MockModule>
        implements MockModuleService {

}




