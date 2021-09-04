package com.ingins.callcenter.service

import com.ingins.callcenter.dto.`in`.CreateAchivementDto
import com.ingins.callcenter.entity.Achivement

interface AchivementService {
    fun createAchivement(createAchivementDto: CreateAchivementDto): Achivement
    fun getAllAchivement(): List<Achivement>
}