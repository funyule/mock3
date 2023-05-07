package cn.zaink.mock3.application.controller;

import cn.zaink.mock3.application.pojo.MockUrlDto;
import cn.zaink.mock3.application.service.UrlService;
import cn.zaink.mock3.application.pojo.UrlQry;
import cn.zaink.mock3.core.pojo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * url路径Controller
 *
 * @author zaink
 */
@Api(tags = "Url API")
@Slf4j
@RestController
@RequestMapping("/system/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ApiOperation("url列表")
    @GetMapping("/list")
    public Result<IPage<MockUrlDto>> list(UrlQry urlQry) {
        return Result.ok(urlService.find(urlQry));
    }

    @ApiOperation("新增路径")
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody MockUrlDto mockUrl) {
        return Result.ok(urlService.create(mockUrl));
    }

    @ApiOperation("查看url详情")
    @GetMapping("/detail")
    public Result<MockUrlDto> detail(@ApiParam(value = "id", required = true) @RequestParam("id") Long id) {
        return Result.ok(urlService.detail(id));
    }
}
