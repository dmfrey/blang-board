package io.pivotal.apptx.blangBoard.domain.persistence.integration.config

import org.springframework.cloud.config.java.AbstractCloudConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
@Profile( "cloud" )
class CloudDataConfig: AbstractCloudConfig() {

    @Bean
    fun dataSource(): DataSource {

        return connectionFactory().dataSource()
    }

}