package com.ingins.callcenter.dto.`in`

import com.ingins.callcenter.entity.EventsData
import com.ingins.callcenter.entity.User
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


data class CreateAchivementDto(
    var name: String,
    var eventType: String,
    var description: String,
    var duration: String,
    var points: Int
)
