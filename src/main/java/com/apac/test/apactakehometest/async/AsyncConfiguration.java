package com.apac.test.apactakehometest.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration
{
    private final int mCorePoolSize = 1;
    private final int mMaxPoolSize = 1;
    private final int mQueueCapacity = 100;
    private final String mThreadNamePrefix = "AsynchThread-";
    public static final String mAsyncThreadName = "asyncExecutor";

    @Bean(name = mAsyncThreadName)
    public Executor asyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(mCorePoolSize);
        executor.setMaxPoolSize(mMaxPoolSize);
        executor.setQueueCapacity(mQueueCapacity);
        executor.setThreadNamePrefix(mThreadNamePrefix);
        executor.initialize();
        return executor;
    }
}