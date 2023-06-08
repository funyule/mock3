package cn.zaink.mock3.application.service;

import cn.zaink.mock3.application.dto.LogDto;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author zaink
 **/
public interface UrlLogService {
    IPage<LogDto.Response> find(LogDto.Request urlQry);

    Boolean clear();
}
