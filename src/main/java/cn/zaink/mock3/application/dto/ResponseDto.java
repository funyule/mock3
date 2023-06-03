package cn.zaink.mock3.application.dto;

import cn.zaink.mock3.core.jackson.HttpStatusDeserializer;
import cn.zaink.mock3.core.jackson.HttpStatusJsonSerializer;
import cn.zaink.mock3.core.pojo.BasePojo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zaink
 **/
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ApiModel("返回体对象")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDto extends BasePojo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank
    @JsonSerialize(using = ToStringSerializer.class)
    private Long urlId;
    @NotBlank
    private String name;
    @NotBlank
    private String content;

    @JsonDeserialize(using = HttpStatusDeserializer.class)
    @JsonSerialize(using = HttpStatusJsonSerializer.class)
    @NotBlank
    private HttpStatus httpStatus;

    @ApiModelProperty(value = "状态", notes = "1-当前生效")
    @NotBlank
    private Integer enable;

    private String description;

    private List<HttpHeadersDto> customHeader;

    @NotBlank
    @ApiModelProperty(value = "http方法", required = true)
    private HttpMethod httpMethod;

    private String remark;

}
