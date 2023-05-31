package cn.zaink.mock3.application.dto;

import cn.zaink.mock3.core.pojo.PageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author zaink
 **/
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("url查询对象")
@Data
public class UrlQry extends PageQry {

    @ApiModelProperty("所属模块")
    private String moduleId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("url")
    private String url;
    /**
     * @see cn.zaink.mock3.application.consts.ResponseType
     */
    @NotBlank
    @Min(1)
    @Max(2)
    @ApiModelProperty(value = "响应类型", allowableValues = "1,2", required = true)
    private Integer responseType;
    @ApiModelProperty("描述")
    private String description;
}
