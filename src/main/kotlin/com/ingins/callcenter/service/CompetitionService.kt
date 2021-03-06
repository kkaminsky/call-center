package com.ingins.callcenter.service

import com.ingins.callcenter.entity.Competition
import com.ingins.callcenter.entity.Events
import java.util.*

interface CompetitionService {
    fun startCompetition(userId: UUID): Competition
    fun getCompetition(competitionId: UUID): Competition
    fun fixResult(userId: UUID): Competition
}