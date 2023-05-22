package cn.zaink.mock3.application.controller;

import cn.zaink.mock3.application.pojo.ResponseDto;
import cn.zaink.mock3.application.pojo.UrlQry;
import cn.zaink.mock3.application.service.ResponseService;
import cn.zaink.mock3.core.pojo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author zaink
 **/
@Api(tags = "返回体 API")
@RestController
@RequestMapping("/system/response")
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @ApiOperation("列表")
    @GetMapping
    public Result<IPage<ResponseDto>> list(UrlQry req) {
        return Result.ok(responseService.find(req));
    }

    @ApiOperation("新增")
    @PostMapping
    public Result<Boolean> create(@RequestBody ResponseDto req) {
        return Result.ok(responseService.create(req));
    }

    @ApiOperation("修改")
    @PutMapping("/{id}")
    public Result<Boolean> update(
            @PathVariable Long id,
            @RequestBody ResponseDto req) {
        return Result.ok(true);
    }

    @ApiOperation("查看详情")
    @GetMapping("/{id}")
    public Result<ResponseDto> detail(@ApiParam(value = "id", required = true) @RequestParam("id") Long id) {
        return Result.ok(responseService.detail(id));
    }
}
