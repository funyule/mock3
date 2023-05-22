package cn.zaink.mock3.application.service;

import cn.zaink.mock3.application.pojo.MockUrlDto;
import cn.zaink.mock3.application.pojo.UrlQry;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author zaink
 **/
public interface UrlService {
    IPage<MockUrlDto> find(UrlQry urlQry);

    Long create(MockUrlDto mockUrl);

    MockUrlDto detail(Long id);

    Boolean delete(Long id);

    Boolean update(MockUrlDto mockUrl);
}
