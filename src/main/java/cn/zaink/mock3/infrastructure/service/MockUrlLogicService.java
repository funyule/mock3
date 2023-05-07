package cn.zaink.mock3.infrastructure.service;

import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.domain.MockUrlLogic;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zaink
 */
public interface MockUrlLogicService extends IService<MockUrlLogic> {
    MockUrlLogic matchUrl(String uri);
}
