package com.ingins.callcenter.entity

import java.util.*


abstract class BaseUUIDEntity : BaseEntity<UUID> {

    constructor(id : UUID) : super(id)

    constructor() : super()

    override fun idGenerator(): UUID = UUID.randomUUID()
}
