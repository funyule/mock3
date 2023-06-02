package cn.zaink.mock3.conf;

import cn.zaink.mock3.core.handler.AbstractUrlHandler;
import cn.zaink.mock3.core.handler.MockResponseStrategy;
import cn.zaink.mock3.core.handler.MockResponseStrategyFactory;
import cn.zaink.mock3.core.handler.UrlHandlerChain;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zaink
 **/
@Configuration
public class Mock3Configuration implements InitializingBean {

    private final ApplicationContext context;

    public Mock3Configuration(ApplicationContext context) {
        this.context = context;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        context.getBeansOfType(MockResponseStrategy.class)
                .forEach((key, value) -> MockResponseStrategyFactory.addStrategy(value.type(), value));

        List<AbstractUrlHandler> urlHandlers = context.getBeansOfType(AbstractUrlHandler.class)
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Ordered::getOrder))
                .collect(Collectors.toList());
        UrlHandlerChain.init(urlHandlers);
    }

}
