package cn.zaink.mock3.core.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 统一业务异常，用于包裹很多方法声明式的向上抛异常
 *
 * @author zaink
 */
public class BizException extends RuntimeException {
    @Getter
    private final int code;

    public BizException(HttpStatus httpStatus) {
        this(httpStatus.value(), httpStatus.getReasonPhrase());
    }

    public BizException(String message, Object... args) {
        this(500, message, args);
    }

    public BizException(int code, String message, Object... args) {
        super(StrUtil.format(message, args));
        this.code = code;
    }

    public BizException(int code, Throwable cause, String message, Object... args) {
        super(StrUtil.format(message, args), cause);
        this.code = code;
    }

    public BizException(Throwable cause, String message, Object... args) {
        this(500, cause, message, args);
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = 500;
    }

}
