package cn.zaink.mock3.infrastructure.service.impl;

import cn.zaink.mock3.infrastructure.domain.MockResponseHeader;
import cn.zaink.mock3.infrastructure.mapper.MockResponseHeaderMapper;
import cn.zaink.mock3.infrastructure.service.MockResponseHeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zaink
 * @description 针对表【mock_response_header(mock响应的http header)】的数据库操作Service实现
 * @createDate 2023-05-22 21:08:50
 */
@Service
public class MockResponseHeaderServiceImpl extends ServiceImpl<MockResponseHeaderMapper, MockResponseHeader>
        implements MockResponseHeaderService {

}




