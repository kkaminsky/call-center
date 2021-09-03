package com.ingins.callcenter.controller

import com.ingins.callcenter.entity.User
import com.ingins.callcenter.service.UserService
import org.springframework.web.bind.annotation.*
import java.time.Instant


@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {

    @PostMapping("/create")
    fun createUser(@RequestBody username: String) : User {
        return userService.createUser(username)
    }

    @PostMapping("/all")
    fun getAllUsers(): List<User>{
        return userService.getAll()
    }


    data class GetLeadersDto(
        val fromDate: Instant,
        val toDate: Instant,
        val type: String? = null
    )

    @PostMapping("/leaders")
    fun getLeadersBoard(@RequestBody request: GetLeadersDto): List<Pair<User,Int>> {
        return userService.getLeaders(
            request.fromDate,
            request.toDate,
            request.type
        )
    }
}