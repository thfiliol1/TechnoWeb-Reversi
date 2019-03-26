package fr.isima.data;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("fr.isima.data")
@EntityScan("fr.isima.data")
@Configuration
public class DataConfiguration {
}
