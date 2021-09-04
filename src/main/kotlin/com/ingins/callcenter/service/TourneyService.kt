package com.ingins.callcenter.service

import com.ingins.callcenter.entity.Tourney
import java.util.*

interface TourneyService {
    fun createTourney(tourneyEventType: String): Tourney
    fun endTourney(tourneyId:UUID): Tourney
}