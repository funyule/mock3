package cn.zaink.mock3.application.controller;

import cn.zaink.mock3.application.dto.LogDto;
import cn.zaink.mock3.application.service.UrlLogService;
import cn.zaink.mock3.core.pojo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zaink
 **/
@Api(tags = "日志API")
@Slf4j
@RestController
@RequestMapping("/system/url/log")
public class UrlLogController {

    private final UrlLogService urlLogService;

    public UrlLogController(UrlLogService urlLogService) {
        this.urlLogService = urlLogService;
    }

    @ApiOperation("列表")
    @GetMapping({"", "/"})
    public Result<IPage<LogDto.Response>> list(LogDto.Request urlQry) {
        return Result.ok(urlLogService.find(urlQry));
    }

    @ApiOperation("清空")
    @PostMapping({"/clear"})
    public Result<Boolean> clear() {
        return Result.ok(urlLogService.clear());
    }
}
