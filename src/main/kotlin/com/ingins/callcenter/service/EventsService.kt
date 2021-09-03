package com.ingins.callcenter.service

import com.ingins.callcenter.dto.`in`.CreateEventDto
import com.ingins.callcenter.entity.Events

interface EventsService {
    fun createEvent(createEvent: CreateEventDto): Events
}