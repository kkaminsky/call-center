package com.ingins.callcenter.repository

import com.ingins.callcenter.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {
}