package cn.zaink.mock3.application.init;

import cn.zaink.mock3.application.event.ModuleEvent;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.service.MockModuleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zaink
 **/
@Component
public class InitModuleRegister implements CommandLineRunner {

    private final ApplicationEventPublisher eventPublisher;
    private final MockModuleService moduleService;

    public InitModuleRegister(ApplicationEventPublisher eventPublisher, MockModuleService moduleService) {
        this.eventPublisher = eventPublisher;
        this.moduleService = moduleService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<MockModule> lists = moduleService.list(Wrappers.lambdaQuery());
        lists.parallelStream().forEach(r -> eventPublisher.publishEvent(new ModuleEvent.Update(r)));
    }
}
