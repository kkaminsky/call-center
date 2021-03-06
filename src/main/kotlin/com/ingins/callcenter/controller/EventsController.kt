package com.ingins.callcenter.controller

import com.ingins.callcenter.dto.`in`.CreateEventDto
import com.ingins.callcenter.entity.Events
import com.ingins.callcenter.service.EventsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/events")
class EventsController(
    private val eventsService: EventsService
) {

    @PostMapping("/create")
    fun createEvent(@RequestBody request: CreateEventDto): Events {
        return eventsService.createEvent(request)
    }

    @PostMapping("/get/by/user")
    fun getEventsForUsers(@RequestBody dto: CompetitionController.UserIdDto): List<Events> {
        return eventsService.getEventsByUser(dto.userId)
    }

}