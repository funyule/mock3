package cn.zaink.mock3.conf;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger使用配置
 *
 * @author yaoxiangping
 **/
@Configuration
@EnableKnife4j
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("cn.zaink.mock3.application"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(publicHeaders());
    }

    private List<RequestParameter> publicHeaders() {
        List<RequestParameter> parameters = new ArrayList<>(3);
        parameters.add(new RequestParameterBuilder()
                .name("x-user-id").description("用户id")
                .example(new ExampleBuilder().value("1").build())
                .in(ParameterType.HEADER).build());
        return parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档").description("Mock3 文档")
                .contact(new Contact("zaink", "https://mock3.zaink.cn", "funyule@vip.qq.com"))
                .version("1.0")
                .build();
    }

}
