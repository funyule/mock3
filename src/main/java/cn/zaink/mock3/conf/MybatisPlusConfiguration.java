package cn.zaink.mock3.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zaink
 **/
@Configuration
@MapperScan("cn.zaink.mock3.**.mapper")
public class MybatisPlusConfiguration {
}
