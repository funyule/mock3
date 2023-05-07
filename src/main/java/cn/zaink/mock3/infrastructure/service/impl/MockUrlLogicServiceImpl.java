package cn.zaink.mock3.infrastructure.service.impl;

import cn.zaink.mock3.infrastructure.domain.MockUrl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zaink.mock3.infrastructure.domain.MockUrlLogic;
import cn.zaink.mock3.infrastructure.service.MockUrlLogicService;
import cn.zaink.mock3.infrastructure.mapper.MockUrlLogicMapper;
import org.springframework.stereotype.Service;

/**
* @author zaink
*/
@Service
public class MockUrlLogicServiceImpl extends ServiceImpl<MockUrlLogicMapper, MockUrlLogic>
    implements MockUrlLogicService{

    @Override
    public MockUrlLogic matchUrl(String url) {
        return this.getOne(Wrappers.<MockUrlLogic>query()
                .apply("{0} REGEXP sub_url", url), false);
    }
}




