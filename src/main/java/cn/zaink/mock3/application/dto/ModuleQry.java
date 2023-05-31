package cn.zaink.mock3.application.dto;

import cn.zaink.mock3.core.pojo.PageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zaink
 **/
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ApiModel("url查询对象")
@Data
public class ModuleQry extends PageQry {

    @ApiModelProperty("编号")
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("服务名")
    private String serviceName;

    @ApiModelProperty("是否发布服务")
    private Integer publishService;
}
