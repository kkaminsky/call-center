package com.ingins.callcenter.entity

import org.hibernate.annotations.Type
import java.io.Serializable
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity

@Entity
@Access(value= AccessType.FIELD)
data class User(
    val name: String,
    val rank: Int,
    @field:Type(type = "jsonb")
    val data: Data
): BaseUUIDEntity()

data class Data(
    val achieves: List<Achieve>
) : Serializable

data class Achieve(
    val name: String
): Serializable

