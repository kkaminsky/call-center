package com.ingins.callcenter.repository

import com.ingins.callcenter.entity.Events
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventsRepository: JpaRepository<Events, UUID>{

}