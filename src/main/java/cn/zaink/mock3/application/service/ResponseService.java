package cn.zaink.mock3.application.service;

import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.application.dto.ResponseQry;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author zaink
 **/
public interface ResponseService {
    IPage<ResponseDto> find(ResponseQry req);

    Long create(ResponseDto req);

    ResponseDto detail(Long id);

    Boolean update(ResponseDto req);

    Boolean delete(Long id);
}
