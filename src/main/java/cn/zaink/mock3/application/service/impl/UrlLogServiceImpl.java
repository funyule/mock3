package cn.zaink.mock3.application.service.impl;

import cn.zaink.mock3.application.dto.LogDto;
import cn.zaink.mock3.application.service.UrlLogService;
import cn.zaink.mock3.infrastructure.domain.MockLog;
import cn.zaink.mock3.infrastructure.service.MockLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zaink
 **/
@Slf4j
@Service
public class UrlLogServiceImpl implements UrlLogService {

    private final MockLogService mockLogService;

    public UrlLogServiceImpl(MockLogService mockLogService) {
        this.mockLogService = mockLogService;
    }

    @Override
    public IPage<LogDto.Response> find(LogDto.Request urlQry) {
        Page<MockLog> page = new Page<>(urlQry.getCurrent(), urlQry.getSize());
        return mockLogService.page(page, Wrappers.<MockLog>lambdaQuery().orderByDesc(MockLog::getCreateTime))
                .convert(l -> LogDto.Response.builder()
                        .id(l.getId())
                        .requestMethod(l.getRequestMethod()).requestUrl(l.getRequestUrl())
                        .requestIp(l.getRequestIp())

                        .responseStatus(l.getResponseStatus())
                        .responseHeader(l.getResponseHeader())
                        .responseBody(l.getResponseBody())
                        .createTime(l.getCreateTime()).remark(l.getRemark())
                        .build());
    }

    @Override
    public Boolean clear() {
        return mockLogService.remove(Wrappers.lambdaQuery());
    }
}
