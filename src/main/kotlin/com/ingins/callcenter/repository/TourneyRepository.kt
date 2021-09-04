package com.ingins.callcenter.repository

import com.ingins.callcenter.entity.Tourney
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TourneyRepository: JpaRepository<Tourney,UUID> {
}