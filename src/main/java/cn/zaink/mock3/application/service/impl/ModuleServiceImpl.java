package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.zaink.mock3.application.dto.ModuleDto;
import cn.zaink.mock3.application.dto.ModuleQry;
import cn.zaink.mock3.application.event.ModulePublishEvent;
import cn.zaink.mock3.application.service.ModuleService;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.service.MockModuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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

    private final ApplicationEventPublisher eventPublisher;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean delete(Long id) {
        return mockModuleService.removeById(id);
    }

    public ModuleServiceImpl(MockModuleService mockModuleService, ApplicationEventPublisher eventPublisher) {
        this.mockModuleService = mockModuleService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long create(ModuleDto req) {
        MockModule entity = MockModule.builder()
                .id(IdWorker.getId())
                .name(req.getName())
                .serviceName(req.getServiceName())
                .publishService(req.getPublishService())
                .build();
        if (isBlank(entity.getServiceName())) {
            String namePinyin = HanLP.convertToPinyinString(req.getName(), "", false);
            entity.setServiceName(namePinyin);
        }
        boolean saved = mockModuleService.save(entity);
        Assert.isTrue(saved);
        return entity.getId();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean update(Long id, ModuleDto module) {
        boolean update = mockModuleService.update(Wrappers.<MockModule>lambdaUpdate()
                .set(isNotBlank(module.getName()), MockModule::getName, module.getName())
                .set(isNotBlank(module.getServiceName()), MockModule::getServiceName, module.getServiceName())
                .set(null != module.getPublishService(), MockModule::getPublishService, module.getPublishService())
                .eq(MockModule::getId, id));
        MockModule mockModule = mockModuleService.getById(id);
        if (update) {
            eventPublisher.publishEvent(new ModulePublishEvent(mockModule));
        }
        return update;
    }

    @Override
    public IPage<ModuleDto> lists(ModuleQry qry) {
        Page<MockModule> page = new Page<>(qry.getCurrent(), qry.getSize());
        return mockModuleService.page(page, Wrappers.<MockModule>lambdaQuery()
                        .eq(null != qry.getId(), MockModule::getId, qry.getId())
                        .like(isNotBlank(qry.getName()), MockModule::getName, qry.getName())
                        .like(isNotBlank(qry.getServiceName()), MockModule::getServiceName, qry.getServiceName())
                        .eq(null != qry.getPublishService(), MockModule::getPublishService, qry.getPublishService()))
                .convert(r -> ModuleDto.builder()
                        .id(r.getId()).name(r.getName()).serviceName(r.getServiceName())
                        .publishService(r.getPublishService())
                        .build());
    }
}
