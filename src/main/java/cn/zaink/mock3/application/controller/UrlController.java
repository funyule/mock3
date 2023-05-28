package cn.zaink.mock3.application.controller;

import cn.zaink.mock3.application.dto.MockUrlDto;
import cn.zaink.mock3.application.dto.UrlQry;
import cn.zaink.mock3.application.service.UrlService;
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
@RequestMapping("/system/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ApiOperation("url列表")
    @GetMapping({"", "/"})
    public Result<IPage<MockUrlDto>> list(UrlQry urlQry) {
        return Result.ok(urlService.find(urlQry));
    }

    @ApiOperation("新增路径")
    @PostMapping
    public Result<Long> create(@RequestBody MockUrlDto mockUrl) {
        return Result.ok(urlService.create(mockUrl));
    }

    @ApiOperation("更新路径")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody MockUrlDto mockUrl) {
        return Result.ok(urlService.update(mockUrl));
    }

    @ApiOperation("查看url详情")
    @GetMapping("/{id}")
    public Result<MockUrlDto> detail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return Result.ok(urlService.detail(id));
    }

    @ApiOperation("删除url")
    @DeleteMapping("{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        return Result.ok(urlService.delete(id));
    }
}
