package com.ingins.callcenter.controller

import com.ingins.callcenter.dto.`in`.CreateAchivementDto
import com.ingins.callcenter.entity.Achivement
import com.ingins.callcenter.service.AchivementService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/achivement")
class AchivementController(
    private val achivementService: AchivementService
) {

    @PostMapping("/create")
    fun createAchivement(@RequestBody request: CreateAchivementDto): Achivement {
        return achivementService.createAchivement(request)
    }

    @PostMapping("/all")
    fun allAchivement(): List<Achivement> {
        return achivementService.getAllAchivement()
    }

}