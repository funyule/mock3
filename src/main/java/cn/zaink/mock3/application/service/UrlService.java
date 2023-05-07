package cn.zaink.mock3.application.service;

import cn.zaink.mock3.application.pojo.MockUrlDto;
import cn.zaink.mock3.application.pojo.UrlQry;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author zaink
 **/
public interface UrlService {
    IPage<MockUrlDto> find(UrlQry urlQry);

    Boolean create(MockUrlDto mockUrl);

    MockUrlDto detail(Long id);
}
