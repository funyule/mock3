package cn.zaink.mock3.core.handler;

import cn.zaink.mock3.application.consts.ResponseType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;

/**
 * @author zaink
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockResponseStrategyFactory {

    private static final EnumMap<ResponseType, MockResponseStrategy> STRATEGY_ENUM_MAP = new EnumMap<>(ResponseType.class);

    public static void addStrategy(ResponseType type, MockResponseStrategy strategy) {
        STRATEGY_ENUM_MAP.put(type, strategy);
        if (log.isTraceEnabled()) {
            log.trace("添加mock响应策略模式：{}，{}", strategy.type(), strategy.getClass().getName());
        }
    }

    public static MockResponseStrategy get(ResponseType type) {
        return STRATEGY_ENUM_MAP.get(type);
    }
}
