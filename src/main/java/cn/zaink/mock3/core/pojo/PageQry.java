package cn.zaink.mock3.core.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zhukun
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@ApiModel(value = "分页查询公共入参模型")
public class PageQry {

    @ApiModelProperty(value = "每页大小，默认值10", example = "10")
    private Integer size = 10;

    @ApiModelProperty(value = "当前页码，默认1", example = "1")
    private Integer current = 1;

    @ApiModelProperty(value = "搜索关键字")
    private String keyWords;
}
