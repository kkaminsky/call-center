package com.ingins.callcenter.runner

import com.ingins.callcenter.service.TourneyService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class TourneyRunner(
    @Qualifier("tourneyScheduler")
    private val tourneyScheduler: ThreadPoolTaskScheduler,
    private val tourneyService: TourneyService
) : CommandLineRunner {

    val tourneyMap = mutableMapOf<String,UUID>()
    val eventsType = listOf("driversLicense",
            "passportRF",

            "zagranpasport",

            "snils"
         )

    override fun run(vararg args: String?) {
        val delay = TimeUnit.MINUTES.toMillis(10*1000*1000)
        tourneyScheduler.scheduleWithFixedDelay(this::runNewTourneys,delay)
    }

    fun runNewTourneys(){
        eventsType.forEach {
            val oldTourney  = tourneyMap.get(it)
            if(oldTourney!=null){
                tourneyService.endTourney(oldTourney)
            }
            val newTourney = tourneyService.createTourney(it)
            tourneyMap[it] = newTourney.id
        }
    }
}