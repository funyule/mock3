package cn.zaink.mock3.application.service.impl;

import cn.zaink.mock3.application.pojo.ResponseDto;
import cn.zaink.mock3.application.pojo.UrlQry;
import cn.zaink.mock3.application.service.ResponseService;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author zaink
 **/
@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService {

    private final MockResponseService mockResponseService;

    public ResponseServiceImpl(MockResponseService mockResponseService) {
        this.mockResponseService = mockResponseService;
    }

    @Override
    public IPage<ResponseDto> find(UrlQry req) {
        LambdaQueryWrapper<MockResponse> queryWrapper = Wrappers.<MockResponse>lambdaQuery()
                .orderByDesc(MockResponse::getCreateTime);
        Page<MockResponse> page = new Page<>(req.getCurrent(), req.getSize());
        return mockResponseService.page(page, queryWrapper)
                .convert(po2dto);
    }

    private final Function<MockResponse, ResponseDto> po2dto = r -> ResponseDto.builder()
            .id(r.getId()).description(r.getDescription())
            .createBy(r.getCreateBy()).createTime(r.getCreateTime())
            .updateBy(r.getUpdateBy()).updateTime(r.getUpdateTime())
            .name(r.getContent()).status(r.getMain())
            .httpMethod(r.getMethod()).remark(r.getRemark())
            .httpStatus(r.getStatusCode()).customHeader(r.getCustomHeader())
            .build();

    @Override
    public ResponseDto detail(Long id) {
        MockResponse mockResponse = mockResponseService.getById(id);
        return null == mockResponse ? null : po2dto.apply(mockResponse);
    }

    @Override
    public Boolean create(ResponseDto req) {
        MockResponse response = MockResponse.builder()
                .id(IdWorker.getId())
                .customHeader(req.getCustomHeader())
                .content(req.getName())
                .description(req.getDescription())
                .main(req.getStatus())
                .statusCode(req.getHttpStatus())
                .method(req.getHttpMethod())
                .remark(req.getRemark())
                .build();
        return mockResponseService.save(response);
    }

}
