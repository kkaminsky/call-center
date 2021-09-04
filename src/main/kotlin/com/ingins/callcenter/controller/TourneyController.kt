package com.ingins.callcenter.controller

import com.ingins.callcenter.entity.Tourney
import com.ingins.callcenter.service.TourneyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController()
@RequestMapping("/api/tourney")
class TourneyController(
    private val tourneyService: TourneyService
) {

    data class TourneyType(
        val type: String
    )

    data class TourneyTypeWithUser(
        val type: String,
        val userId: UUID
    )

    @PostMapping("/current")
    fun getCurrentTourneyByType(@RequestBody dto: TourneyType): Tourney {
        return tourneyService.getCurrentResults(dto.type)
    }

    @PostMapping("/current/with/user")
    fun getCurrentTourneyByTypeWithUser(@RequestBody dto: TourneyTypeWithUser): Tourney{
        return tourneyService.getCurrentResultsWithUser(dto.type,dto.userId)
    }
}