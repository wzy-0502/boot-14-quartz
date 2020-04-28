package com.etoak.factory;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

@Component
public class MvcJobFactory extends SpringBeanJobFactory {

    @Autowired
    AutowireCapableBeanFactory factory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 创建任务
        Object job = super.createJobInstance(bundle);
        // 将任务对象注入到spring容器中
        factory.autowireBean(job);
        return job;
    }
}
