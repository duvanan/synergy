package org.example.synergy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("org.example.synergy.model")
@ConfigurationPropertiesScan(basePackages = {
        "org.example.synergy.config",
        "org.example.synergy.configprops"
})
@EnableScheduling
public class SynergyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynergyApplication.class, args);
    }

}
