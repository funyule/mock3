package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.zaink.mock3.application.dto.ModuleDto;
import cn.zaink.mock3.application.dto.ModuleQry;
import cn.zaink.mock3.application.event.ModuleEvent;
import cn.zaink.mock3.application.service.ModuleService;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.service.MockModuleService;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import cn.zaink.mock3.infrastructure.service.MockUrlService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
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
    private final MockUrlService mockUrlService;
    private final MockResponseService mockResponseService;

    private final ApplicationEventPublisher eventPublisher;

    public ModuleServiceImpl(MockModuleService mockModuleService, MockUrlService mockUrlService, MockResponseService mockResponseService, ApplicationEventPublisher eventPublisher) {
        this.mockModuleService = mockModuleService;
        this.mockUrlService = mockUrlService;
        this.mockResponseService = mockResponseService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean delete(Long id) {
        MockModule mockModule = mockModuleService.getById(id);
        boolean removeById = mockModuleService.removeById(id);
        if (removeById) {
            eventPublisher.publishEvent(new ModuleEvent.Delete(mockModule));
        }
        return removeById;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long create(ModuleDto req) {
        MockModule mockModule = MockModule.builder()
                .id(IdWorker.getId())
                .name(req.getName())
                .serviceName(req.getServiceName())
                .publishService(req.getPublishService())
                .build();
        if (isBlank(mockModule.getServiceName())) {
            String namePinyin = HanLP.convertToPinyinString(req.getName(), "_", false);
            String capitalizeFully = WordUtils.capitalizeFully(namePinyin, '_')
                    .replaceAll("_", "");
            mockModule.setServiceName(capitalizeFully);
        }
        boolean saved = mockModuleService.save(mockModule);
        Assert.isTrue(saved);
        eventPublisher.publishEvent(new ModuleEvent.Create(mockModule));
        return mockModule.getId();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean update(Long id, ModuleDto module) {
        boolean update = mockModuleService.update(Wrappers.<MockModule>lambdaUpdate()
                .set(isNotBlank(module.getName()), MockModule::getName, module.getName())
                .set(isNotBlank(module.getServiceName()), MockModule::getServiceName, module.getServiceName())
                .set(null != module.getPublishService(), MockModule::getPublishService, module.getPublishService())
                .eq(MockModule::getId, id));
        if (update) {
            MockModule mockModule = mockModuleService.getById(id);
            eventPublisher.publishEvent(new ModuleEvent.Update(mockModule));
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
