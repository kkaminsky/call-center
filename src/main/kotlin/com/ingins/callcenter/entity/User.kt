package com.ingins.callcenter.entity

import org.hibernate.annotations.Type
import java.io.Serializable
import java.time.Instant
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity

@Entity
@Access(value = AccessType.FIELD)
class User(
    var name: String,
    var rank: Int,
    @field:Type(type = "jsonb")
    var data: Data
) : BaseUUIDEntity()

data class Data(
    val achieves: List<Achieve>
) : Serializable

data class Achieve(
    val begin: Instant,
    val end: Instant,
    val name: String,
    val description: String,
    val points: Int = 0,
    val percent: Int = 0
) : Serializable

