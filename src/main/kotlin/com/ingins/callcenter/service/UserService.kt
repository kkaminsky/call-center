package com.ingins.callcenter.service

import com.ingins.callcenter.entity.User
import java.time.Instant

interface UserService {
    fun createUser(username: String): User
    fun getAll(): List<User>
    fun getLeaders(fromDate: Instant, toDate: Instant, type: String? = null): List<Pair<User,Int>>
}