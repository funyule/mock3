package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.zaink.mock3.application.dto.ModuleDto;
import cn.zaink.mock3.application.dto.ModuleQry;
import cn.zaink.mock3.application.service.ModuleService;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.service.MockModuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author zaink
 **/
@Slf4j
@Service
public class ModuleServiceImpl implements ModuleService {

    private final MockModuleService mockModuleService;

    public ModuleServiceImpl(MockModuleService mockModuleService) {
        this.mockModuleService = mockModuleService;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean delete(Long id) {
        return mockModuleService.removeById(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long create(ModuleDto module) {
        MockModule entity = MockModule.builder()
                .id(IdWorker.getId())
                .name(module.getName())
                .serviceName(module.getServiceName())
                .build();
        if (isBlank(entity.getServiceName())) {
            String namePinyin = HanLP.convertToPinyinString(module.getName(), StrUtil.UNDERLINE, false);
            entity.setServiceName(namePinyin);
        }
        boolean saved = mockModuleService.save(entity);
        Assert.isTrue(saved);
        return entity.getId();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean update(Long id, ModuleDto module) {
        return mockModuleService.update(Wrappers.<MockModule>lambdaUpdate()
                .set(isNotBlank(module.getName()), MockModule::getName, module.getName())
                .set(isNotBlank(module.getServiceName()), MockModule::getServiceName, module.getServiceName())
                .eq(MockModule::getId, id));
    }

    @Override
    public IPage<ModuleDto> lists(ModuleQry qry) {
        Page<MockModule> page = new Page<>(qry.getCurrent(), qry.getSize());
        return mockModuleService.page(page)
                .convert(r -> ModuleDto.builder()
                        .id(r.getId()).name(r.getName()).serviceName(r.getServiceName())
                        .build());
    }
}
