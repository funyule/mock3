package cn.zaink.mock3.application.utils;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.Getter;

/**
 * @author zaink
 **/
public class NacosUtil {

    @Getter
    private final ConfigService configService;
    @Getter
    private final NamingService namingService;

    public NacosUtil(ConfigService configService, NamingService namingService) {
        this.configService = configService;
        this.namingService = namingService;
    }
}
