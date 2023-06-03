package cn.zaink.mock3.application.controller;

import cn.zaink.mock3.application.dto.HttpStatusDto;
import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.application.dto.ResponseQry;
import cn.zaink.mock3.application.service.ResponseService;
import cn.zaink.mock3.core.pojo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static String getFieldValue(Field f) {
        try {
            f.setAccessible(true);
            return String.valueOf(f.get(null));
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @ApiOperation("列表")
    @GetMapping
    public Result<IPage<ResponseDto>> list(@Valid ResponseQry req) {
        return Result.ok(responseService.find(req));
    }

    @ApiOperation("新增")
    @PostMapping
    public Result<String> create(@Valid @RequestBody ResponseDto req) {
        return Result.ok(String.valueOf(responseService.create(req)));
    }

    @ApiOperation("修改")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody ResponseDto req) {
        req.setId(id);
        return Result.ok(responseService.update(req));
    }

    @ApiOperation("查看详情")
    @GetMapping("/{id}")
    public Result<ResponseDto> detail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return Result.ok(responseService.detail(id));
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return Result.ok(responseService.delete(id));
    }

    @ApiOperation("http请求头列表")
    @GetMapping("/http/headers")
    public Result<List<String>> httpHeaders() {
        List<String> headers = Arrays.stream(HttpHeaders.class.getDeclaredFields())
                .filter(h -> Modifier.isFinal(h.getModifiers()))
                .filter(h -> Modifier.isStatic(h.getModifiers()))
                .filter(h -> Modifier.isPublic(h.getModifiers()))
                .filter(h -> !Sets.newHashSet("EMPTY").contains(h.getName()))
                .map(ResponseController::getFieldValue)
                .collect(Collectors.toList());
        return Result.ok(headers);
    }

    @ApiOperation("http方法列表")
    @GetMapping("/http/methods")
    public Result<List<String>> httpMethods() {
        List<String> methods = Arrays.stream(HttpMethod.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return Result.ok(methods);
    }

    @ApiOperation("http状态列表")
    @GetMapping("/http/status")
    public Result<List<HttpStatusDto>> httpStatus() {
        Map<String, Field> map = Arrays.stream(HttpStatus.class.getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, e -> e));
        List<HttpStatusDto> statusList = Arrays.stream(HttpStatus.values())
                .filter(e -> {
                    Field field = map.get(e.name());
                    return !field.isAnnotationPresent(Deprecated.class);
                })
                .map(s -> HttpStatusDto.builder().name(s.name())
                        .reasonPhrase(s.getReasonPhrase())
                        .value(s.value()).build())
                .collect(Collectors.toList());
        return Result.ok(statusList);
    }
}
