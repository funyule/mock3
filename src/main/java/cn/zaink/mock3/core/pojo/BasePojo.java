package cn.zaink.mock3.core.pojo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author zaink
 **/
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BasePojo {

    @ApiModelProperty("标识id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createBy;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;
    @ApiModelProperty("备注")
    private String remark;
}
