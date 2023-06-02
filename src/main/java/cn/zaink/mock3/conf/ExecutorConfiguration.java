package cn.zaink.mock3.conf;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wanghao
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfiguration {

    @Bean("AsyncTaskExecutor")
    public AsyncTaskExecutor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() / 2);
        //配置最大线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        //配置队列大小
        executor.setQueueCapacity(10000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("Async-");
        //异步线程加入父线程的上下文
        executor.setTaskDecorator(new AsyncTaskDecorator());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean("ScheduledExecutorService")
    public ScheduledExecutorService scheduledExecutorService() {
        ThreadFactory threadFactory = ThreadFactoryBuilder.create()
                .setNamePrefix("Scheduled-")
                .setDaemon(false)
                .build();
        return new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 4
                , threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * @author wanghao
     * 将父线程的上下文放入到子线程种，解决异步async线程无TraceId
     */
    public static class AsyncTaskDecorator implements TaskDecorator {
        @Override
        public @NonNull Runnable decorate(@NonNull Runnable runnable) {
            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    if (!CollectionUtils.isEmpty(copyOfContextMap)) {
                        MDC.setContextMap(copyOfContextMap);
                    }
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        }
    }
}
