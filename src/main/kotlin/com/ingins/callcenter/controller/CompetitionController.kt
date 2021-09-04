package com.ingins.callcenter.controller

import com.ingins.callcenter.entity.Competition
import com.ingins.callcenter.service.CompetitionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/competition")
class CompetitionController(
    private val competitionService: CompetitionService
) {

    data class UserIdDto(
        val userId: UUID
    )

    data class CompetitionIdDto(
        val competitionId: UUID
    )

    @PostMapping("/start")
    fun startCompetition(@RequestBody dto: UserIdDto): Competition{
        return competitionService.startCompetition(dto.userId)
    }


    @PostMapping("/get")
    fun getCompetition(@RequestBody dto: CompetitionIdDto): Competition {
        return competitionService.getCompetition(dto.competitionId)
    }


    data class FixResultDto(
        val eventId: UUID,
        val userId: UUID
    )

    @PostMapping("/fix")
    fun fixResult(@RequestBody dto: FixResultDto): Competition {
        return competitionService.fixResult(dto.userId,dto.eventId)
    }
}