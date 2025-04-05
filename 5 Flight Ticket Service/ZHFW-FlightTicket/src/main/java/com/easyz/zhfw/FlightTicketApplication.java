package com.easyz.zhfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FlightTicketApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightTicketApplication.class, args);
    }
}
