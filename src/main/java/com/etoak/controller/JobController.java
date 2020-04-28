package com.etoak.controller;

import com.etoak.job.OrderJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    Scheduler scheduler;

    @RequestMapping(value = "/create", produces = {"application/json;charset=UTF-8"})
    public Object create(@RequestParam String jobName, @RequestParam String jobGroup,
                         @RequestParam String trigger, @RequestParam String triggerGroup,
                         @RequestParam String cron) throws SchedulerException {
        Map<String, Object> result = new HashMap<>();

        JobKey jobKey = new JobKey(jobName, jobGroup);
        TriggerKey triggerKey = new TriggerKey(trigger, triggerGroup);
        if(scheduler.checkExists(jobKey) || scheduler.checkExists(triggerKey)) {
            result.put("code", 0);
            result.put("msg", "任务已经存在");
            return result;
        }
        JobDetail jobDetail = JobBuilder.newJob(OrderJob.class)
                .withIdentity(jobKey)
                .build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        result.put("code", 200);
        result.put("msg", "SUCCESS");
        return result;
    }

}
