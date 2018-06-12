package io.pivotal.apptx.blangBoard.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType

@Configuration
@EnableHypermediaSupport( type = [ HypermediaType.HAL ] )
class HalConfig {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {

        var objectMapper = jacksonObjectMapper()
        objectMapper.registerKotlinModule()
        objectMapper.registerModule( Jdk8Module() )
        objectMapper.registerModule( JavaTimeModule() )
        objectMapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false )
        objectMapper.configure( SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false )

        return objectMapper
    }

}