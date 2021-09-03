package com.ingins.callcenter.entity.state


import com.ingins.callcenter.entity.BaseUUIDEntity
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity


@Entity
@Access(value= AccessType.FIELD)
data class State(
    var text: String
) : BaseUUIDEntity()