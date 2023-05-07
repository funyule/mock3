package cn.zaink.mock3.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zaink.mock3.infrastructure.domain.MockLog;
import cn.zaink.mock3.infrastructure.service.MockLogService;
import cn.zaink.mock3.infrastructure.mapper.MockLogMapper;
import org.springframework.stereotype.Service;

/**
* @author zaink
*/
@Service
public class MockLogServiceImpl extends ServiceImpl<MockLogMapper, MockLog>
    implements MockLogService{

}




