package com.ingins.callcenter.entity

import org.hibernate.annotations.Type
import java.io.Serializable
import java.time.Instant
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity


@Entity
@Access(value= AccessType.FIELD)
class Tourney(
    val startTime: Instant,
    val endTime: Instant,
    val type: String,
    @field:Type(type = "jsonb")
    val result: ResultData
) : BaseUUIDEntity(){
}

data class ResultData(
    val results: List<Result>
): Serializable

data class Result(
    val username: String ,
    val place: Int,
    val earnedPoints: Int
) : Serializable