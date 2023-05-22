package cn.zaink.mock3.application.pojo;

import cn.zaink.mock3.core.pojo.BasePojo;
import io.swagger.annotations.ApiModel;
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
    private String name;
    private String serviceName;
}
