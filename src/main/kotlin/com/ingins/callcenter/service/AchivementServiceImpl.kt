package com.ingins.callcenter.service

import com.ingins.callcenter.dto.`in`.CreateAchivementDto
import com.ingins.callcenter.entity.Achivement
import com.ingins.callcenter.repository.AchivementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AchivementServiceImpl(
    private val achivementRepository: AchivementRepository
) : AchivementService {

    @Transactional(readOnly = false)
    override fun createAchivement(createAchivementDto: CreateAchivementDto): Achivement {
        return achivementRepository.save(
            Achivement(
                name = createAchivementDto.name,
                description = createAchivementDto.description,
                duration = createAchivementDto.duration,
                points = createAchivementDto.points,
                eventType = createAchivementDto.eventType
            )
        )
    }

    @Transactional(readOnly = true)
    override fun getAllAchivement(): List<Achivement> {
        return achivementRepository.findAll()
    }
}