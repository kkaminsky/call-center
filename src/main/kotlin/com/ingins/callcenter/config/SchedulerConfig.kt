package com.ingins.callcenter.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class SchedulerConfig {

    @Bean
    @Qualifier("tourneyScheduler")
    fun notifierScheduler(): ThreadPoolTaskScheduler {
        return ThreadPoolTaskScheduler().also {
            it.poolSize = 10
            it.initialize()
        }
    }
}