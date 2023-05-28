package cn.zaink.mock3.application.dto;

import cn.zaink.mock3.core.pojo.PageQry;
import io.swagger.annotations.ApiModel;
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

    private String name;
}
