package cn.zaink.mock3.application.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * response 启用状态
 *
 * @author vt
 * @since 2019年11月26日
 */
@Getter
@AllArgsConstructor
public enum MainEnum {

    /* 启用*/
    ENABLE(1),

    /* 停用*/
    DISABLE(-1);


    private final Integer code;

}