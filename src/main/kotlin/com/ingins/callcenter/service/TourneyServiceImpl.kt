package com.ingins.callcenter.service

import com.ingins.callcenter.dto.`in`.CreateEventDto
import com.ingins.callcenter.entity.Events
import com.ingins.callcenter.entity.EventsData
import com.ingins.callcenter.entity.Result
import com.ingins.callcenter.entity.ResultData
import com.ingins.callcenter.entity.Tourney
import com.ingins.callcenter.repository.EventsRepository
import com.ingins.callcenter.repository.TourneyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class TourneyServiceImpl(
    private val tourneyRepository: TourneyRepository,
    private val userService: UserService,
    private val eventsService: EventsService
) : TourneyService {
    @Transactional
    override fun createTourney(tourneyEventType: String): Tourney {
        return tourneyRepository.save(Tourney(
            startTime = Instant.now(),
            endTime = null,
            type = tourneyEventType,
            result = ResultData(
                results = listOf()
            )
        ))
    }

    @Transactional
    override fun endTourney(tourneyId: UUID): Tourney {
        val tourney = tourneyRepository.getById(tourneyId)
        tourney.endTime = Instant.now()
        tourney.result = ResultData(userService.getLeaders(tourney.startTime,tourney.endTime!!,tourney.type)
            .take(3)
            .filter { it.second!=0 }
            .mapIndexed { index, pair ->
            eventsService.createEvent(
                CreateEventDto(
                userId = pair.first.id,
                type = tourney.type,
                data = EventsData(tourneyWin = tourney),
                points = 10 / (index + 1)))
                Result(
                    username = pair.first.name,
                    place = index +1,
                    earnedPoints = 10 / (index + 1)
                )
            })
        return tourneyRepository.save(tourney)
    }
}