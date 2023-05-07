package cn.zaink.mock3.application.pojo;

import cn.zaink.mock3.core.pojo.BasePojo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @author zaink
 **/
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ApiModel("返回体对象")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDto extends BasePojo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @NotBlank
    private String content;
    @NotBlank
    private Integer statusCode;
    @NotBlank
    private Integer main;

    private String description;

    private String customHeader;

    @NotBlank
    @ApiModelProperty(value = "http方法", allowableValues = "GET,POST,DELETE,PUT,HEAD",required = true)
    private String method;

    private String remark;
}
