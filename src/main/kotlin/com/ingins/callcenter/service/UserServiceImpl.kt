package com.ingins.callcenter.service

import com.ingins.callcenter.entity.Data
import com.ingins.callcenter.entity.User
import com.ingins.callcenter.repository.EventsRepository
import com.ingins.callcenter.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val eventsRepository: EventsRepository
) : UserService {

    @Transactional
    override fun createUser(username: String): User {
        return userRepository.save(User(
            name = username,
            rank = 0,
            data = Data(
                achieves = listOf()
            )
        ))
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    @Transactional(readOnly = true)
    override fun getLeaders(fromDate: Instant, toDate: Instant, type: String?): List<Pair<User, Int>> {
        return eventsRepository.findAll().filter {
            it.createTime.isAfter(fromDate) && it.createTime.isBefore(toDate) && type?.let { tp ->
               it.type == tp
            } ?: true
        }.groupBy { it.user }.map {
            it.key to it.value.fold(0) { r, t ->
                r + t.points
            }
        }.sortedByDescending { it.second }
    }
}