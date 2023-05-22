package cn.zaink.mock3.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.zaink.mock3.application.pojo.MockUrlDto;
import cn.zaink.mock3.application.pojo.UrlQry;
import cn.zaink.mock3.application.service.UrlService;
import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.domain.MockUrlLogic;
import cn.zaink.mock3.infrastructure.service.MockUrlLogicService;
import cn.zaink.mock3.infrastructure.service.MockUrlService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author zaink
 **/
@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    private final MockUrlService mockUrlService;
    private final MockUrlLogicService mockUrlLogicService;

    public UrlServiceImpl(MockUrlService mockUrlService, MockUrlLogicService mockUrlLogicService) {
        this.mockUrlService = mockUrlService;
        this.mockUrlLogicService = mockUrlLogicService;
    }

    @Override
    public IPage<MockUrlDto> find(UrlQry mockUrl) {
        LambdaQueryWrapper<MockUrl> queryWrapper = Wrappers.<MockUrl>lambdaQuery()
                .like(StrUtil.isNotBlank(mockUrl.getName()), MockUrl::getName, mockUrl.getName())
                .like(StrUtil.isNotBlank(mockUrl.getDescription()), MockUrl::getDescription, mockUrl.getDescription())
                .orderByDesc(MockUrl::getCreateTime);
        Page<MockUrl> page = new Page<>(mockUrl.getCurrent(), mockUrl.getSize());
        page.setSearchCount(true);
        return mockUrlService.page(page, queryWrapper)
                .convert(po2dto);
    }

    private final Function<MockUrl, MockUrlDto> po2dto = r -> MockUrlDto.builder()
            .id(r.getId()).name(r.getName())
            .url(r.getUrl()).description(r.getDescription())
            .responseType(r.getResponseType())
            .createBy(r.getCreateBy()).createTime(r.getCreateTime())
            .updateBy(r.getUpdateBy()).updateTime(r.getUpdateTime())
            .build();

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long create(MockUrlDto req) {
        String reqUrl = req.getUrl();
        String formatUrlStr = formatUrlStr(reqUrl);
        MockUrlLogic logUrl = createLogUrl(formatUrlStr);
        MockUrl mockUrl = MockUrl.builder()
                .id(IdWorker.getId())
                .name(req.getName()).url(formatUrlStr)
                .responseType(req.getResponseType())
                .logic(null != logUrl ? logUrl.getLogicId() : null)
                .description(req.getDescription())
                .createTime(LocalDateTime.now())
                .build();
        mockUrlService.save(mockUrl);
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
                    .logicId(IdWorker.getId())
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
        return null == mockUrl ? null : po2dto.apply(mockUrl);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean delete(Long id) {
        MockUrl mockUrl = mockUrlService.getById(id);
        Assert.notNull(mockUrl, () -> new BizException("Url不存在"));
        if (null != mockUrl.getLogic()) {
            mockUrlLogicService.remove(Wrappers.<MockUrlLogic>lambdaQuery()
                    .eq(MockUrlLogic::getLogicId, mockUrl.getLogic()));
        }
        return mockUrlService.removeById(id);
    }

    @Override
    public Boolean update(MockUrlDto mockUrl) {
        return false;
    }
}
