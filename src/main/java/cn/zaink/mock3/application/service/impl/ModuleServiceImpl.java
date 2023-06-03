package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.zaink.mock3.application.dto.ModuleDto;
import cn.zaink.mock3.application.dto.ModuleQry;
import cn.zaink.mock3.application.event.ModuleEvent;
import cn.zaink.mock3.application.service.ModuleService;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.service.MockModuleService;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import cn.zaink.mock3.infrastructure.service.MockUrlService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<MockUrl> mockUrls = mockUrlService.list(Wrappers.<MockUrl>lambdaQuery()
                .eq(MockUrl::getModuleId, mockModule.getId()));
        if (CollectionUtils.isNotEmpty(mockUrls)) {
            Set<Long> urlIds = mockUrls.stream().map(MockUrl::getId).collect(Collectors.toSet());
            mockResponseService.remove(Wrappers.<MockResponse>lambdaQuery()
                    .in(MockResponse::getUrlId, urlIds));
            mockUrlService.removeByIds(urlIds);
        }
        eventPublisher.publishEvent(new ModuleEvent.Delete(mockModule));
        return mockModuleService.removeById(id);
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
