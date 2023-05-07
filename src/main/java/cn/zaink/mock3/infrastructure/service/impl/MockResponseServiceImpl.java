package cn.zaink.mock3.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import cn.zaink.mock3.infrastructure.mapper.MockResponseMapper;
import org.springframework.stereotype.Service;

/**
* @author zaink
*/
@Service
public class MockResponseServiceImpl extends ServiceImpl<MockResponseMapper, MockResponse>
    implements MockResponseService{

}




