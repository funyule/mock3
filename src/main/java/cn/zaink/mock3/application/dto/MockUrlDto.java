package cn.zaink.mock3.application.dto;

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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * mock_url
 *
 * @author zaink
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ApiModel("url对象")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MockUrlDto extends BasePojo {

    @NotNull
    @ApiModelProperty("所属模块")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long moduleId;

    @ApiModelProperty("所属模块信息")
    private ModuleDto module;
    /**
     *
     */
    @NotBlank
    @ApiModelProperty("url路径")
    private String url;

    /**
     *
     */
    @NotBlank
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    /**
     *
     */
    private String logic;

    /**
     * @see cn.zaink.mock3.application.consts.ResponseType
     */
    @NotBlank
    @Min(1)
    @Max(2)
    @ApiModelProperty(value = "响应类型", allowableValues = "1,2", required = true)
    private Integer responseType;

}