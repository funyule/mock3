package cn.zaink.mock3.application.pojo;

import cn.hutool.core.date.DatePattern;
import cn.zaink.mock3.core.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDateTime;

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

    /**
     *
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

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