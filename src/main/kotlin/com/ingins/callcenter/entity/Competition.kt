package com.ingins.callcenter.entity

import org.hibernate.annotations.Type
import javax.persistence.*

@Entity
@Access(value= AccessType.FIELD)
class Competition(
    @field:Type(type = "jsonb")
    val user1: CompetitionUser,
    @field:Type(type = "jsonb")
    val user2: CompetitionUser,
    val isFinished: Boolean,
    @field:Type(type = "jsonb")
    val result: CompetitionResultData
): BaseUUIDEntity() {
}

data class CompetitionResultData(
    val competitionResults: List<CompetitionResult>
)

data class CompetitionResult(
    val username: String,
    val place: Int,
    val earnedPoints: Int
)

data class CompetitionUser(
    val username: String,
    val health: Int
)