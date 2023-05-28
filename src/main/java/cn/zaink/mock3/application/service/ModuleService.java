package cn.zaink.mock3.application.service;

import cn.zaink.mock3.application.dto.ModuleDto;
import cn.zaink.mock3.application.dto.ModuleQry;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author zaink
 **/
public interface ModuleService {
    Boolean delete(Long id);

    Long create(ModuleDto module);

    Boolean update(Long id, ModuleDto module);

    IPage<ModuleDto> lists(ModuleQry qry);
}
