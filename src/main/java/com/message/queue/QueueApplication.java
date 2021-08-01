package com.message.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.message.queue"})
@EnableScheduling
public class QueueApplication {

    private static final String SPRING_DEFAULT_PROFILE = "dev";

    public static void main(String[] args) {
        log.info("STARTING MESSAGE APPLICATION.............");
        SpringApplication app = new SpringApplication(QueueApplication.class);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        addDefaultProfile(app, source);
        app.run(args);
    }

    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {

        if (!source.containsProperty("spring.profiles.active") &&
                !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {

            app.setAdditionalProfiles(SPRING_DEFAULT_PROFILE);
        }
    }
}
