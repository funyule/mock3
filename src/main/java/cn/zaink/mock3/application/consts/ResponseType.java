package cn.zaink.mock3.application.consts;

import cn.zaink.mock3.core.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 响应类型
 *
 * @author vt
 * @since 2020年05月15日
 */
@AllArgsConstructor
public enum ResponseType {

    /**
     * 手动模式
     */
    MANUAL(1, "手动模式"),

    /**
     * Restful模式
     */
    RESTFUL(2, "Restful模式");

    /**
     * 类型值
     */
    @Getter
    private final Integer code;

    @Getter
    private final String label;

    public static ResponseType byCode(int code) {
        return Arrays.stream(ResponseType.values())
                .filter(r -> code == r.getCode())
                .findAny()
                .orElseThrow(() -> new BizException("无法识别的ResponseType"));
    }
}
