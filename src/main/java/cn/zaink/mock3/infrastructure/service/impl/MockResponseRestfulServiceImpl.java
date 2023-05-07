package cn.zaink.mock3.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zaink.mock3.infrastructure.domain.MockResponseRestful;
import cn.zaink.mock3.infrastructure.service.MockResponseRestfulService;
import cn.zaink.mock3.infrastructure.mapper.MockResponseRestfulMapper;
import org.springframework.stereotype.Service;

/**
* @author zaink
*/
@Service
public class MockResponseRestfulServiceImpl extends ServiceImpl<MockResponseRestfulMapper, MockResponseRestful>
    implements MockResponseRestfulService{

}




