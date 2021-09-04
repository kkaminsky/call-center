package com.ingins.callcenter.entity

import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity

@Entity
@Access(value = AccessType.FIELD)
class Achivement(
    var name: String,
    var eventType: String,
    var description: String,
    var duration: String,
    var points: Int
) : BaseUUIDEntity()