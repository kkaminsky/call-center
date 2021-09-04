package com.ingins.callcenter.entity

import org.hibernate.annotations.Type
import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@Access(value= AccessType.FIELD)
class Events(
    @field:ManyToOne(cascade = [CascadeType.DETACH, CascadeType.REFRESH])
    @field:JoinColumn(name = "user_id", nullable = false)
    var user: User,
    val type: String,
    @field:Type(type = "jsonb")
    val data: EventsData,
    val points: Int
): BaseUUIDEntity()



data class EventsData(
    val speedGame: SpeedGame? = null,
    val searchGame: SearchGame? = null,
    val tabGame: TabGame? = null,
    val cookieClickerGame: CookieClickerGame? = null,
    val documentAnalized: DocumentAnalized? = null,
    val achivmentEarned: AchivmentEarned? = null,
    val tourneyWin: Tourney? = null
) : Serializable

data class AchivmentEarned(
    val achivement: Achivement
) : Serializable

data class SpeedGame(
    val wordPreMinute: Int,
    val characterPreMinute: Int,
    val timer: Instant,
    val errors: Int,
    val accuracy: Double
): Serializable

data class SearchGame(
    val timer: Int,
    val errors: Int,
    val accuracy: Double
): Serializable

data class TabGame(
    val accuracy: Double,
    val timer: Int
): Serializable

data class CookieClickerGame(
    val timer: Int,
    val score: Int,
    val errors: Int
): Serializable

data class DocumentAnalized(
    val difficult: Int,
    val charCounts: Int,
    val hasError: Boolean,
    val isFinishedInTime: Boolean,
    val timer: Int) : Serializable