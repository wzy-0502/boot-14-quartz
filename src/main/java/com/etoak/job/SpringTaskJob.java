package com.etoak.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SpringTaskJob {

    // @Scheduled(cron = "0/5 * * * * ?")
    public void printHello() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Hello - " + sdf.format(new Date()) + Thread.currentThread().getName());
    }

    // @Scheduled(cron = "0/3 * * * * ?")
    public void printHello2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Hello22222 - " + sdf.format(new Date()) + Thread.currentThread().getName());
    }
}
