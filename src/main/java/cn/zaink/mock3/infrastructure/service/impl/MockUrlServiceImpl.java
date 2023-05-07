package cn.zaink.mock3.infrastructure.service.impl;

import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.mapper.MockUrlMapper;
import cn.zaink.mock3.infrastructure.service.MockUrlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zaink
 */
@Service
public class MockUrlServiceImpl extends ServiceImpl<MockUrlMapper, MockUrl> implements MockUrlService {

}




