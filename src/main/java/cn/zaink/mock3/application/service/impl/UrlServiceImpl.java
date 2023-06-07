package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.zaink.mock3.application.dto.MockUrlDto;
import cn.zaink.mock3.application.dto.ModuleDto;
import cn.zaink.mock3.application.dto.UrlQry;
import cn.zaink.mock3.application.event.UrlEvent;
import cn.zaink.mock3.application.service.UrlService;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.domain.MockUrlLogic;
import cn.zaink.mock3.infrastructure.service.MockModuleService;
import cn.zaink.mock3.infrastructure.service.MockUrlLogicService;
import cn.zaink.mock3.infrastructure.service.MockUrlService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author zaink
 **/
@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    private final MockUrlService mockUrlService;
    private final MockUrlLogicService mockUrlLogicService;

    private final MockModuleService mockModuleService;

    private final ApplicationEventPublisher eventPublisher;

    public UrlServiceImpl(MockUrlService mockUrlService, MockUrlLogicService mockUrlLogicService, MockModuleService mockModuleService, ApplicationEventPublisher eventPublisher) {
        this.mockUrlService = mockUrlService;
        this.mockUrlLogicService = mockUrlLogicService;
        this.mockModuleService = mockModuleService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public IPage<MockUrlDto> find(UrlQry req) {
        LambdaQueryWrapper<MockUrl> queryWrapper = Wrappers.<MockUrl>lambdaQuery()
                .like(StrUtil.isNotBlank(req.getName()), MockUrl::getName, req.getName())
                .like(StrUtil.isNotBlank(req.getDescription()), MockUrl::getDescription, req.getDescription())
                .eq(null != req.getResponseType(), MockUrl::getResponseType, req.getResponseType())
                .eq(null != req.getModuleId(), MockUrl::getModuleId, req.getModuleId())
                .like(isNotBlank(req.getUrl()), MockUrl::getUrl, req.getUrl())
                .orderByDesc(MockUrl::getCreateTime);
        Page<MockUrl> page = new Page<>(req.getCurrent(), req.getSize());
        return mockUrlService.page(page, queryWrapper)
                .convert(this::po2dto);
    }

    private MockUrlDto po2dto(MockUrl r) {
        if (null == r) {
            return null;
        }
        Long moduleId = r.getModuleId();
        MockModule mockModule = mockModuleService.getById(moduleId);
        return MockUrlDto.builder()
                .id(r.getId()).name(r.getName()).moduleId(moduleId)
                .url(r.getUrl()).description(r.getDescription())
                .responseType(r.getResponseType())
                .createBy(r.getCreateBy()).createTime(r.getCreateTime())
                .module(ModuleDto.builder()
                        .id(moduleId).name(mockModule.getName())
                        .serviceName(mockModule.getServiceName())
                        .publishService(mockModule.getPublishService())
                        .build())
                .build();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long create(MockUrlDto req) {
        String reqUrl = req.getUrl();
        String formatUrlStr = formatUrlStr(reqUrl);
        MockUrlLogic logUrl = createLogUrl(formatUrlStr);
        MockUrl mockUrl = MockUrl.builder()
                .id(IdWorker.getId()).moduleId(req.getModuleId())
                .name(req.getName()).url(formatUrlStr)
                .responseType(req.getResponseType())
                .logic(null != logUrl ? logUrl.getId() : null)
                .description(req.getDescription())
                .createTime(LocalDateTime.now())
                .build();
        mockUrlService.save(mockUrl);
        eventPublisher.publishEvent(new UrlEvent.Create(mockUrl));
        return mockUrl.getId();
    }

    private String formatUrlStr(String url) {
        // not start with /, concat it.
        if (!url.startsWith(StrUtil.SLASH)) {
            url = StrUtil.SLASH.concat(url);
        }
        // end with /, remove it
        while (url.endsWith(StrUtil.SLASH) && url.length() > 1) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    private static final Pattern PLACEHOLD = Pattern.compile("\\/\\{([^{}]+)\\}");

    private MockUrlLogic createLogUrl(String url) {
        boolean findPlaceHolder = PLACEHOLD.matcher(url).find();
        log.debug("findPlaceHolder {}", findPlaceHolder);
        if (findPlaceHolder) {
            String regexStr = url
                    .replaceFirst("/", "\\\\/")
                    .replaceAll("\\.", "\\\\.")
                    .replaceAll("\\/\\{([^{}]+)\\}", "\\\\\\/(\\\\w+)");
            MockUrlLogic urlLogic = MockUrlLogic.builder()
                    .id(IdWorker.getId())
                    .subUrl(regexStr)
                    .build();
            mockUrlLogicService.save(urlLogic);
            return urlLogic;
        }
        return null;
    }

    @Override
    public MockUrlDto detail(Long id) {
        MockUrl mockUrl = mockUrlService.getById(id);
        return po2dto(mockUrl);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean delete(Long id) {
        MockUrl mockUrl = mockUrlService.getById(id);
        boolean removeById = mockUrlService.removeById(id);
        if (removeById) {
            eventPublisher.publishEvent(new UrlEvent.Delete(mockUrl));
        }
        return removeById;
    }

    @Override
    public Boolean update(MockUrlDto req) {
        boolean updated = mockUrlService.update(Wrappers.<MockUrl>lambdaUpdate()
                .set(isNotBlank(req.getName()), MockUrl::getName, req.getName())
                .set(isNotBlank(req.getUrl()), MockUrl::getUrl, req.getUrl())
                .set(isNotBlank(req.getDescription()), MockUrl::getDescription, req.getDescription())
                .set(null != req.getModuleId(), MockUrl::getModuleId, req.getModuleId())
                .set(null != req.getResponseType(), MockUrl::getResponseType, req.getResponseType())
                .eq(MockUrl::getId, req.getId()));
        if (updated) {
            MockUrl mockUrl = mockUrlService.getById(req.getId());
            eventPublisher.publishEvent(new UrlEvent.Update(mockUrl));
        }
        return updated;
    }
}
