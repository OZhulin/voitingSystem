package ru.zhulin.oleg.restsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class RestSystemApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RestSystemApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RestSystemApp.class);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
