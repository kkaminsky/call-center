package com.ingins.callcenter.repository

import com.ingins.callcenter.entity.Competition
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CompetitionRepository: JpaRepository<Competition,UUID> {
}