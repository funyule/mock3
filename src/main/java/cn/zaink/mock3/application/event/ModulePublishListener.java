package cn.zaink.mock3.application.event;

import cn.hutool.core.map.MapBuilder;
import cn.zaink.mock3.application.utils.NacosUtil;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.PreservedMetadataKeys;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class ModulePublishListener implements ApplicationListener<WebServerInitializedEvent> {

    private final NacosUtil nacosUtil;
    private final InetUtils inetUtils;
    private final Environment environment;

    private final ObjectMapper objectMapper;
    private int port;

    public ModulePublishListener(NacosUtil nacosUtil, InetUtils inetUtils, Environment environment, ObjectMapper objectMapper) {
        this.nacosUtil = nacosUtil;
        this.inetUtils = inetUtils;
        this.environment = environment;
        this.objectMapper = objectMapper;
    }

    @EventListener(ModulePublishEvent.class)
    public void publish(ModulePublishEvent event) throws NacosException, JsonProcessingException {
        MockModule mockModule = event.getSource();
        String serviceName = mockModule.getServiceName();
        String group = "DEFAULT";
        Map<String, String> metadata = MapBuilder.<String, String>create()
                .put(PreservedMetadataKeys.REGISTER_SOURCE, "SPRING_CLOUD")
                .put(PreservedMetadataKeys.HEART_BEAT_INTERVAL, "5s")
                .put("url.suffix", serviceName.concat("/fake"))
                .build();

        Instance instance = new Instance();
        instance.setIp(inetUtils.findFirstNonLoopbackAddress().getHostAddress());
        instance.setPort(port);
        instance.setWeight(1.0);
        instance.setClusterName(group);
        instance.setEnabled(mockModule.getPublishService() == 1);
        instance.setMetadata(metadata);
        nacosUtil.getNamingService().registerInstance(serviceName, instance);
        nacosUtil.getConfigService().publishConfig(serviceName, group, objectMapper.writeValueAsString(metadata), ConfigType.JSON.getType());
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
        if (this.port < 0) {
            this.port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
        }
    }
}
