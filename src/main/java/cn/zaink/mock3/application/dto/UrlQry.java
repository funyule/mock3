package cn.zaink.mock3.application.dto;

import cn.zaink.mock3.core.pojo.PageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private Integer module;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("url")
    private String url;
    @ApiModelProperty("描述")
    private String description;
}
