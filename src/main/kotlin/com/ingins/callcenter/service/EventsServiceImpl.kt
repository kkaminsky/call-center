package com.ingins.callcenter.service

import com.ingins.callcenter.dto.`in`.CreateEventDto
import com.ingins.callcenter.entity.Events
import com.ingins.callcenter.repository.EventsRepository
import com.ingins.callcenter.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventsServiceImpl(
    private val eventsRepository: EventsRepository,
    private val userRepository: UserRepository
) : EventsService {

    @Transactional
    override fun createEvent(createEvent: CreateEventDto): Events {
        val user = userRepository.findById(createEvent.userId).get()
        return eventsRepository.save(
            Events(
                user = user,
                type = createEvent.type,
                data = createEvent.data,
                points = createEvent.points
            )
        )
    }
}