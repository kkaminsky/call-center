package com.ingins.callcenter.entity

import org.hibernate.annotations.Type
import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@Access(value= AccessType.FIELD)
class Events(
    @field:ManyToOne(cascade = [CascadeType.DETACH, CascadeType.REFRESH])
    @field:JoinColumn(name = "user", nullable = false)
    var user: User,
    val type: String,
    @field:Type(type = "jsonb")
    val data: EventsData,
    val points: Int
): BaseUUIDEntity()



data class EventsData(
    val game1: String? = null,
    val game2: String? = null
) : Serializable


data class SpeedGame(
    val wordPreMinute: Int,
    val characterPreMinute: Int,
    val timer: Instant,
    val errors: Int,
    val accuracy: Double
): Serializable

data class SearchGame(
    val timer: Instant,
    val errors: Int,
    val accuracy: Double
): Serializable

data class TabGame(
    val accuracy: Double,
    val timer: Instant
): Serializable

data class CookieClickerGame(
    val timer: Instant,
    val score: Int,
    val errors: Int
): Serializable

data class DocumentAnalized(
    val difficult: Int,
    val charCounts: Int,
    val hasError: Boolean,
    val isFinishedInTime: Boolean,
    val timer: Instant)