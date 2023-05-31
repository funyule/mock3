package cn.zaink.mock3.application.controller;

import cn.zaink.mock3.application.dto.ModuleDto;
import cn.zaink.mock3.application.dto.ModuleQry;
import cn.zaink.mock3.application.service.ModuleService;
import cn.zaink.mock3.core.pojo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author zaink
 **/
@Api(tags = "模块 API")
@Slf4j
@RestController
@RequestMapping("/system/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @ApiOperation("分页接口")
    @GetMapping
    public Result<IPage<ModuleDto>> lists(ModuleQry qry) {
        return Result.ok(moduleService.lists(qry));
    }

    @ApiOperation("新建")
    @PostMapping
    public Result<Long> create(@RequestBody ModuleDto module) {
        return Result.ok(moduleService.create(module));
    }

    @ApiOperation("修改")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody ModuleDto module) {
        return Result.ok(moduleService.update(id, module));
    }

    @ApiOperation("新建")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.ok(moduleService.delete(id));
    }
}
