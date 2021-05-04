package com.pranay.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, QuartzAutoConfiguration.class})
@Configuration("classpath:application-" + "${spring.profiles.active}" + ".properties")
public class QuartzSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzSchedulerApplication.class, args);
    }

}
