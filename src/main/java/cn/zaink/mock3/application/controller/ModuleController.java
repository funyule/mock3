package cn.zaink.mock3.application.controller;

import cn.zaink.mock3.application.pojo.ModuleDto;
import cn.zaink.mock3.core.pojo.PageQry;
import cn.zaink.mock3.core.pojo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zaink
 **/
@Api(tags = "模块 API")
@Slf4j
@RestController
@RequestMapping("/module")
public class ModuleController {

    @ApiOperation("分页接口")
    @GetMapping("/lists")
    public Result<IPage<ModuleDto>> lists(PageQry qry) {
        return Result.ok();
    }

}
