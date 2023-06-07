package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.zaink.mock3.application.dto.HttpHeadersDto;
import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.application.dto.ResponseQry;
import cn.zaink.mock3.application.event.UrlResponseEvent;
import cn.zaink.mock3.application.service.ResponseService;
import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import cn.zaink.mock3.infrastructure.domain.MockResponseHeader;
import cn.zaink.mock3.infrastructure.service.MockResponseHeaderService;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author zaink
 **/
@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService {

    private final MockResponseService mockResponseService;
    private final MockResponseHeaderService mockResponseHeaderService;
    private final ApplicationEventPublisher eventPublisher;

    public ResponseServiceImpl(MockResponseService mockResponseService, MockResponseHeaderService mockResponseHeaderService, ApplicationEventPublisher eventPublisher) {
        this.mockResponseService = mockResponseService;
        this.mockResponseHeaderService = mockResponseHeaderService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public IPage<ResponseDto> find(ResponseQry req) {
        LambdaQueryWrapper<MockResponse> queryWrapper = Wrappers.<MockResponse>lambdaQuery()
                .eq(MockResponse::getUrlId, req.getUrlId())
                .eq(null != req.getEnable(), MockResponse::getEnable, req.getEnable())
                .eq(null != req.getHttpMethod(), MockResponse::getHttpMethod, req.getHttpMethod())
                .eq(null != req.getHttpStatus(), MockResponse::getHttpStatus, null != req.getHttpStatus() ? req.getHttpStatus().value() : null)
                .orderByDesc(MockResponse::getCreateTime);
        Page<MockResponse> page = new Page<>(req.getCurrent(), req.getSize());
        return mockResponseService.page(page, queryWrapper)
                .convert(this::po2dto);
    }

    private ResponseDto po2dto(MockResponse r) {
        List<HttpHeadersDto> customHeaders = mockResponseHeaderService.list(Wrappers.<MockResponseHeader>lambdaQuery()
                        .eq(MockResponseHeader::getMockResponseId, r.getId()))
                .stream()
                .map(h -> HttpHeadersDto.builder()
                        .name(h.getName()).value(h.getValue())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.builder()
                .id(r.getId()).description(r.getDescription())
                .createBy(r.getCreateBy()).createTime(r.getCreateTime())
                .name(r.getName()).enable(r.getEnable())
                .httpMethod(HttpMethod.valueOf(r.getHttpMethod())).remark(r.getRemark())
                .httpStatus(HttpStatus.valueOf(r.getHttpStatus()))
                .customHeader(customHeaders)
                .content(r.getContent())
                .build();
    }

    ;

    @Override
    public ResponseDto detail(Long id) {
        MockResponse mockResponse = mockResponseService.getById(id);
        return null == mockResponse ? null : po2dto(mockResponse);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean update(ResponseDto req) {
        Assert.notNull(req.getId(), () -> new BizException("Id不能为空"));
        boolean updated = mockResponseService.update(Wrappers.<MockResponse>lambdaUpdate()
                .set(null != req.getEnable(), MockResponse::getEnable, req.getEnable())
                .set(isNotBlank(req.getDescription()), MockResponse::getDescription, req.getDescription())
                .set(isNotBlank(req.getName()), MockResponse::getName, req.getName())
                .set(isNotBlank(req.getContent()), MockResponse::getContent, req.getContent())
                .set(null != req.getHttpMethod(), MockResponse::getHttpMethod, null != req.getHttpMethod() ? req.getHttpMethod().name() : null)
                .set(null != req.getHttpStatus(), MockResponse::getHttpStatus, null != req.getHttpStatus() ? req.getHttpStatus().value() : null)
                .eq(MockResponse::getId, req.getId()));
        if (updated) {
            MockResponse mockResponse = mockResponseService.getById(req.getId());
            eventPublisher.publishEvent(new UrlResponseEvent.Update(mockResponse, req));
        }
        return updated;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean delete(Long id) {
        MockResponse mockResponse = mockResponseService.getById(id);
        boolean b = mockResponseService.removeById(id);
        if (b) {
            eventPublisher.publishEvent(new UrlResponseEvent.Delete(mockResponse));
        }
        return b;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long create(ResponseDto req) {
        MockResponse mockResponse = MockResponse.builder()
                .id(IdWorker.getId()).name(req.getName())
                .content(req.getContent())
                .description(req.getDescription())
                .enable(req.getEnable())
                .httpStatus(req.getHttpStatus().value())
                .httpMethod(req.getHttpMethod().name())
                .remark(req.getRemark())
                .urlId(req.getUrlId())
                .createTime(LocalDateTime.now())
                .build();
        mockResponseService.save(mockResponse);
        Long responseId = mockResponse.getId();
        eventPublisher.publishEvent(new UrlResponseEvent.Create(mockResponse, req));
        return responseId;
    }

}
