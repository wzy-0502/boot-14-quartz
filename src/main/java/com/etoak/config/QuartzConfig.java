package com.etoak.config;

import com.etoak.factory.MvcJobFactory;
import com.etoak.job.EmailJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * Quartz集群配置
 * 1. 配置数据源
 * 2. 配置事务管理器
 * 3. 配置JobDetailFactoryBean
 * 4. 配置CronTriggerFactoryBean
 * 5. 配置SchedulerFactoryBean
 */
@Configuration
public class QuartzConfig {

    @Autowired
    DataSource dataSource;


    @Autowired
    MvcJobFactory jobFactory;

    @Bean
    public DataSourceTransactionManager tx() {
        return new DataSourceTransactionManager(this.dataSource);
    }

    @Bean
    public JobDetailFactoryBean emailJob() {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setName("emailJob");
        jobDetail.setGroup("emailJob");
        jobDetail.setJobClass(EmailJob.class);
        // 设置持久化
        jobDetail.setDurability(true);
        return jobDetail;
    }

    @Bean
    public CronTriggerFactoryBean emailTrigger() {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setName("emailTrigger");
        trigger.setGroup("emailGroup");
        trigger.setJobDetail(this.emailJob().getObject());
        trigger.setCronExpression("*/5 * * * * ?");
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean scheduler() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        // 设置数据源
        scheduler.setDataSource(this.dataSource);

        // 设置事务管理器
        scheduler.setTransactionManager(this.tx());

        // 设置集群配置文件quartz.properties
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        scheduler.setConfigLocation(resolver.getResource("classpath:quartz.properties"));

        // 设置trigger
        scheduler.setTriggers(this.emailTrigger().getObject());

        // 设置jobFactory，解决job任务无法使用spring业务bean的问题
        scheduler.setJobFactory(this.jobFactory);
        return scheduler;
    }

}
