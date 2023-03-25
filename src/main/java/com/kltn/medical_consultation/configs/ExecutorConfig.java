package com.kltn.medical_consultation.configs;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

//    @Value("${fix.task-thread-pool: 15}")
//    private int fixNumberThread;
//
//    @Value("${no-sql.thead-pool: 30}")
//    private int noSqlDatabasePool;
//
//    @Value("${sql.thread.pool: 15}")
//    private int sqlDatabasePool;
//
//    /**
//     * pool de xu ly dong bo all
//     */
//    @Bean("indexAllPool")
//    public Executor indexAllPool() {
//        return Executors.newFixedThreadPool(fixNumberThread);
//    }
//
//    /**
//     *
//     * pool giành cho các tác vụ không liên quan đến sql database
//     */
//    @Bean("noSqlPool")
//    public Executor noSqlPool() {
//        return Executors.newFixedThreadPool(noSqlDatabasePool);
//
//    }
//
//    /**
//     *
//     * pool giành cho các tác vụ  liên quan đến sql database
//     */
//    @Bean("sqlPool")
//    public Executor sqlPool() {
//        return Executors.newFixedThreadPool(sqlDatabasePool);
//
//    }
//
//    @Bean
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        ThreadPoolTaskScheduler threadPoolTaskScheduler
//                = new ThreadPoolTaskScheduler();
//        threadPoolTaskScheduler.setPoolSize(5);
//        threadPoolTaskScheduler.setThreadNamePrefix(
//                "ThreadPoolTask");
//        return threadPoolTaskScheduler;
//    }
}
