package com.ingins.callcenter.controller

import com.ingins.callcenter.entity.Tourney
import com.ingins.callcenter.service.TourneyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/tourney")
class TourneyController(
    private val tourneyService: TourneyService
) {

    data class TourneyType(
        val type: String
    )

    @PostMapping("/current")
    fun getCurrentTourneyByType(@RequestBody dto: TourneyType): Tourney {
        return tourneyService.getCurrentResults(dto.type)
    }
}