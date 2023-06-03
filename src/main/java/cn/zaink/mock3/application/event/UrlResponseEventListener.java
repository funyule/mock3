package cn.zaink.mock3.application.event;

import cn.zaink.mock3.application.dto.HttpHeadersDto;
import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import cn.zaink.mock3.infrastructure.domain.MockResponseHeader;
import cn.zaink.mock3.infrastructure.service.MockResponseHeaderService;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class UrlResponseEventListener {

    private final ApplicationEventPublisher eventPublisher;
    private final MockResponseService mockResponseService;
    private final MockResponseHeaderService mockResponseHeaderService;

    public UrlResponseEventListener(ApplicationEventPublisher eventPublisher, MockResponseService mockResponseService, MockResponseHeaderService mockResponseHeaderService) {
        this.eventPublisher = eventPublisher;
        this.mockResponseService = mockResponseService;
        this.mockResponseHeaderService = mockResponseHeaderService;
    }

    @Transactional(rollbackFor = Throwable.class)
    @EventListener(UrlResponseEvent.Create.class)
    public void onEvent(UrlResponseEvent.Create event) {
        MockResponse mockResponse = event.getSource();
        ResponseDto sourceRequest = event.getSourceRequest();
        eventPublisher.publishEvent(new UrlResponseEvent.Update(mockResponse, sourceRequest));
    }

    @Transactional(rollbackFor = Throwable.class)
    @EventListener(UrlResponseEvent.Update.class)
    public void onEvent(UrlResponseEvent.Update event) {
        ResponseDto sourceRequest = event.getSourceRequest();
        MockResponse mockResponse = event.getSource();
        if (1 == sourceRequest.getEnable()) {
            // 同一中HttpMethod类型的response只允许有一个enable存在
            mockResponseService.update(Wrappers.<MockResponse>lambdaUpdate()
                    .eq(MockResponse::getUrlId, sourceRequest.getUrlId())
                    .eq(MockResponse::getHttpMethod, mockResponse.getHttpMethod())
                    .ne(MockResponse::getId, mockResponse.getId())
                    .set(MockResponse::getEnable, false));
        }
        eventPublisher.publishEvent(new UrlResponseEvent.Delete(mockResponse));
        List<HttpHeadersDto> customHeaders = sourceRequest.getCustomHeader();
        if (CollectionUtils.isNotEmpty(customHeaders)) {
            List<MockResponseHeader> collect = customHeaders.stream()
                    .map(h -> MockResponseHeader.builder()
                            .id(IdWorker.getId())
                            .mockResponseId(mockResponse.getId())
                            .name(h.getName()).value(h.getValue())
                            .createTime(LocalDateTime.now())
                            .build())
                    .collect(Collectors.toList());
            mockResponseHeaderService.saveBatch(collect);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @EventListener(UrlResponseEvent.Delete.class)
    public void onEvent(UrlResponseEvent.Delete event) {
        MockResponse mockResponse = event.getSource();
        mockResponseHeaderService.remove(Wrappers.<MockResponseHeader>lambdaQuery()
                .eq(MockResponseHeader::getMockResponseId, mockResponse.getId()));
    }
}
