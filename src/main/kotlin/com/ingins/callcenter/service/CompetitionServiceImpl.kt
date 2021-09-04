package com.ingins.callcenter.service

import com.ingins.callcenter.entity.*
import com.ingins.callcenter.repository.CompetitionRepository
import com.ingins.callcenter.repository.EventsRepository
import com.ingins.callcenter.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CompetitionServiceImpl(
    private val competitionRepository: CompetitionRepository,
    private val userRepository: UserRepository,
    private val eventsRepository: EventsRepository
) : CompetitionService {

    @Transactional
    override fun startCompetition(userId: UUID): Competition {
        val existingCompetition = competitionRepository.findAll()
                .filter { it.user1 !=null && !it.isFinished}

        return if(existingCompetition.isEmpty()){
            competitionRepository.save(Competition(
                user1 = CompetitionUser(
                    userId = userId,
                    health = 3,
                    lastResult = null
                ),
                user2 = null,
                isFinished = false,
                result = null
            ))
        } else {
            val competition = existingCompetition.first()
            competition.user2 = CompetitionUser(
                userId = userId,
                health = 3,
                lastResult = null
            )
            competitionRepository.save(competition)
        }
    }


    @Transactional(readOnly = true)
    override fun getCompetition(competitionId: UUID): Competition {
        return competitionRepository.getById(competitionId)
    }

    @Transactional
    override fun fixResult(userId: UUID,eventId: UUID): Competition {
        val event = eventsRepository.getById(eventId)
        val currentCompetition = competitionRepository.findAll()
            .first { !it.isFinished
                    && (it.user1?.let { it.userId == event.user.id} ?: false
                    || it.user2?.let { it.userId == event.user.id} ?: false) }


        val (user, opponent) = if (currentCompetition.user1!!.userId == event.user.id) {
            currentCompetition.user1 to currentCompetition.user2!!
        } else {
            currentCompetition.user2!! to currentCompetition.user1
        }

        user.lastResult = (event.points/event.data.documentAnalized!!.timer
                *event.data.documentAnalized.difficult).toDouble()
        if(opponent.lastResult!=null){
            if(opponent.lastResult!! >=user.lastResult!!){
                user.health -=1
            } else {
                opponent.health -=1
            }
            if (user.health==0){
                currentCompetition.isFinished = true
                currentCompetition.result = CompetitionResult(
                    winnerUserId = opponent.userId,
                    earnedPoints = 5
                )
            } else if (opponent.health==0){
                currentCompetition.isFinished = true
                currentCompetition.result = CompetitionResult(
                    winnerUserId = user.userId,
                    earnedPoints = 5
                )
            }
        }
        return competitionRepository.save(currentCompetition)
    }

    /*@Transactional
    override fun reduceHealth(event: Events): Competition? {
        val currentCompetition = competitionRepository.findAll()
            .firstOrNull { !it.isFinished
                    && (it.user1?.let { it.userId == event.user.id} ?: false
                    || it.user2?.let { it.userId == event.user.id} ?: false) }

        if(currentCompetition!=null) {
            val (currentCompetitionUser, opponent) = if (currentCompetition.user1!!.userId == event.user.id) {
                currentCompetition.user1 to currentCompetition.user2!!
            } else {
                currentCompetition.user2!! to currentCompetition.user1
            }

            val currentUserTime = event.data.documentAnalized!!.charCounts

            val opponentTime = eventsRepository.findAll()
                .filter { events -> events.user.id == opponent.userId && event.type == "WORK" }
                .maxByOrNull { it.createTime }!!.data.documentAnalized!!.charCounts

            if(currentUserTime>opponentTime){
                opponent.health =-1
            } else {
                currentCompetitionUser.health =-1
            }

            if (opponent.health==0){
                currentCompetition.isFinished = true
                currentCompetition.result = CompetitionResult(
                    winnerUserId = currentCompetitionUser.userId,
                    earnedPoints = 5
                )
            } else if (currentCompetitionUser.health==0){
                currentCompetition.isFinished = true
                currentCompetition.result = CompetitionResult(
                    winnerUserId = opponent.userId,
                    earnedPoints = 5
                )
            }
            return competitionRepository.save(currentCompetition)
        } else {
            return null
        }
    }*/
}