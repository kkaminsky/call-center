package com.ingins.callcenter.service

import com.ingins.callcenter.dto.`in`.CreateEventDto
import com.ingins.callcenter.entity.Events
import java.util.*

interface EventsService {
    fun createEvent(createEvent: CreateEventDto): Events
    fun getEventsByUser(userId: UUID): List<Events>
}