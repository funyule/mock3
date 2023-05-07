package cn.zaink.mock3.core.pojo;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


/**
 * @author : zhouyang
 */
@Data
@ApiModel(value = "ActionResponse", description = "返回前端的统一实体")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 2567419642776514758L;

    @ApiModelProperty("错误码")
    private Integer code;
    /**
     * 正常返回业务数据
     */
    @ApiModelProperty("业务数据")
    private T data;

    /**
     * 非正常返回时的提醒信息
     */
    @ApiModelProperty("响应信息")
    private String msg;

    @ApiModelProperty("是否成功")
    @JsonProperty("success")
    public Boolean isSuccess() {
        return Objects.equals(0, this.code);
    }

    public static <R> Result<R> ok() {
        return ok("{}", "success");
    }

    public static <R> Result<R> ok(String msg, Object... args) {
        return back(null, 0, msg, args);
    }

    public static <R> Result<R> ok(int code, String msg, Object... args) {
        return back(null, code, msg, args);
    }

    public static <R> Result<R> ok(R data) {
        return back(data, 0, "success");
    }

    public static <R> Result<R> fail() {
        return fail("{}", "fail");
    }

    public static <R> Result<R> fail(String msg, Object... args) {
        return fail(500, msg, args);
    }

    public static <R> Result<R> fail(int code, String msg, Object... args) {
        return back(null, code, msg, args);
    }

    public static <R> Result<R> fail(R data) {
        return back(data, 500, "fail");
    }

    private static <R> Result<R> back(R data, int status, String msg, Object... args) {
        Result<R> response = new Result<>();
        response.setCode(status);
        response.setMsg(StrUtil.format(msg, args));
        response.setData(data);
        return response;
    }
}
