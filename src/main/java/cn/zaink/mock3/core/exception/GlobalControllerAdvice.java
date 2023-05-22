package cn.zaink.mock3.core.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.zaink.mock3.core.pojo.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;


/**
 * @author : zhouyang
 * 统一的异常处理, 异常返回到前台统一是处理失败
 * 统一的响应格式
 */
@RestControllerAdvice("cn.zaink.mock3")
@Slf4j
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 参数校验
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数验证不合法", e);
        return Result.fail(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> illegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Result<String> bizExceptionHandler(BizException e) {
        log.error("业务异常:{},异常码:{}", e.getMessage(), e.getCode(), e);
        return Result.fail(e.getCode(), ExceptionUtil.getSimpleMessage(e));
    }

    /**
     * 其他相关异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        log.error("系统内部错误 ", ex);
        Throwable rootCause = ExceptionUtil.getRootCause(ex);
        if (rootCause instanceof BizException) {
            return bizExceptionHandler((BizException) rootCause);
        }
        return Result.fail(ExceptionUtil.getRootCauseMessage(ex));
    }

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows(JsonProcessingException.class)
    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType, @NonNull Class aClass, @NonNull ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (MediaType.APPLICATION_JSON.equals(mediaType)) {
            // 判断响应的Content-Type为JSON格式的body
            if (body instanceof Result) {
                // 如果响应返回的对象为统一响应体，则直接返回body
                return body;
            } else if (body instanceof String) {
                ObjectMapper om = new ObjectMapper();
                return om.writeValueAsString(Result.ok(body));
            } else {
                // 只有正常返回的结果才会进入这个判断流程，所以返回正常成功的状态码
                return Result.ok(body);
            }
        }
        // 非JSON格式body直接返回即可
        return body;
    }
}
