package cn.zaink.mock3.application.dto;

import cn.zaink.mock3.core.jackson.HttpStatusDeserializer;
import cn.zaink.mock3.core.pojo.PageQry;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

/**
 * @author zaink
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ApiModel("url查询对象")
@Data
public class ResponseQry extends PageQry {
    @NotNull
    private Long urlId;

    @ApiModelProperty("是否启用")
    private Boolean enable;

    @ApiModelProperty(value = "http方法", required = true)
    private HttpMethod httpMethod;

    @JsonDeserialize(using = HttpStatusDeserializer.class)
    private HttpStatus httpStatus;
}
