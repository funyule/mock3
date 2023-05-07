package cn.zaink.mock3.application.service;

import cn.zaink.mock3.application.pojo.ResponseDto;
import cn.zaink.mock3.application.pojo.UrlQry;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author zaink
 **/
public interface ResponseService {
    IPage<ResponseDto> find(UrlQry req);

    Boolean create(ResponseDto req);

    ResponseDto detail(Long id);
}
