package com.ingins.callcenter.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Access(value= AccessType.FIELD)
class Competition(
    @field:Type(type = "jsonb")
    val user1: CompetitionUser? = null,
    @field:Type(type = "jsonb")
    var user2: CompetitionUser? = null,
    var isFinished: Boolean,
    @field:Type(type = "jsonb")
    var result: CompetitionResult? = null
): BaseUUIDEntity() {
}

data class CompetitionResult(
    val winnerUserId: UUID,
    val earnedPoints: Int
)

data class CompetitionUser(
    val userId: UUID,
    var health: Int
)