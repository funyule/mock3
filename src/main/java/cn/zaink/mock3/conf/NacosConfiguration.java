package cn.zaink.mock3.conf;

import cn.zaink.mock3.application.utils.NacosUtil;
import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zaink
 **/
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@Configuration
public class NacosConfiguration {

    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    public NacosConfiguration(NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
    }

    @ConditionalOnMissingBean(NamingService.class)
    @Bean
    public NamingService namingService() throws NacosException {
        return NacosFactory.createNamingService(nacosDiscoveryProperties.getNacosProperties());
    }

    @ConditionalOnMissingBean(ConfigService.class)
    @Bean
    public ConfigService configService() throws NacosException {
        return NacosFactory.createConfigService(nacosDiscoveryProperties.getNacosProperties());
    }

    @ConditionalOnBean({ConfigService.class, NamingService.class})
    @Bean
    public NacosUtil nacosUtil() throws NacosException {
        return new NacosUtil(configService(), namingService());
    }
}
