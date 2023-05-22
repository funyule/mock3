package cn.zaink.mock3.application.utils;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;

/**
 * @author zaink
 **/
public class NacosUtil {

    private final ConfigService configService;
    private final NamingService namingService;

    public NacosUtil(ConfigService configService, NamingService namingService) {
        this.configService = configService;
        this.namingService = namingService;
    }
}
