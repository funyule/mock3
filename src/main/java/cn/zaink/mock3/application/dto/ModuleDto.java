package cn.zaink.mock3.application.dto;

import cn.zaink.mock3.core.pojo.BasePojo;
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
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ApiModel("模块返回体对象")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModuleDto extends BasePojo {
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("服务名")
    private String serviceName;

    @ApiModelProperty("是否发布服务")
    private Integer publishService;
}
