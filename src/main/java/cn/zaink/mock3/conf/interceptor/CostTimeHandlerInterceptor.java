package cn.zaink.mock3.conf.interceptor;

import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zaink
 */
@Slf4j
public class CostTimeHandlerInterceptor implements HandlerInterceptor {
    private final ThreadLocal<Long> timestamp = new ThreadLocal<>();

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        timestamp.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        log.info("request：{}，cost：{}", request.getRequestURI(), DateUtil.formatBetween(System.currentTimeMillis() - timestamp.get()));
        timestamp.remove();
    }
}
