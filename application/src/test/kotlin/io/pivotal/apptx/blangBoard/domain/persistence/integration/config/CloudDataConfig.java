package io.pivotal.apptx.blangBoard.domain.persistence.integration.config;

import io.pivotal.apptx.blangBoard.domain.persistence.integration.entities.DomainEventEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.sql.DataSource;

@Configuration
@EntityScan(
        basePackageClasses = { DomainEventEntity.class, Jsr310JpaConverters.class }
)
@Profile({ "cloud" })
public class CloudDataConfig extends AbstractCloudConfig {

    @Bean
    public DataSource dataSource() {

        return connectionFactory().dataSource();
    }

}
