package cn.zaink.mock3.application.event;

import cn.hutool.core.map.MapBuilder;
import cn.zaink.mock3.application.utils.NacosUtil;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.PreservedMetadataKeys;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
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
    private int port;

    public ModulePublishListener(NacosUtil nacosUtil, InetUtils inetUtils, Environment environment, ObjectMapper objectMapper) {
        this.nacosUtil = nacosUtil;
        this.inetUtils = inetUtils;
        this.environment = environment;
    }

    @EventListener(ModulePublishEvent.class)
    public void publish2nacos(ModulePublishEvent event) throws NacosException, JsonProcessingException {
        MockModule mockModule = event.getSource();
        String serviceName = mockModule.getServiceName();
        boolean enabled = mockModule.getPublishService() == 1;
        String group = "DEFAULT";
        if (enabled) {
            Map<String, String> serviceMetaData = MapBuilder.<String, String>create()
                    .put(PreservedMetadataKeys.REGISTER_SOURCE, "SPRING_CLOUD")
                    .put(PreservedMetadataKeys.HEART_BEAT_INTERVAL, "5s")
                    .build();

            Instance instance = new Instance();
            instance.setIp(inetUtils.findFirstNonLoopbackAddress().getHostAddress());
            instance.setPort(port);
            instance.setWeight(1.0);
            instance.setClusterName(group);
            instance.setEnabled(true);
            instance.setMetadata(serviceMetaData);
            nacosUtil.getNamingService().registerInstance(serviceName, instance);
        } else {
            List<Instance> allInstances = nacosUtil.getNamingService().getAllInstances(serviceName);
            if (CollectionUtils.isNotEmpty(allInstances)) {
                for (Instance instance : allInstances) {
                    nacosUtil.getNamingService().deregisterInstance(serviceName, instance);
                }
                nacosUtil.getConfigService().removeConfig(serviceName, group);
            }
        }
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
        if (this.port < 0) {
            this.port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
        }
    }
}
