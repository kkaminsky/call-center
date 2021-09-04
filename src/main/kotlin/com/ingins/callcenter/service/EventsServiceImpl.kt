package com.ingins.callcenter.service

import com.ingins.callcenter.dto.`in`.CreateEventDto
import com.ingins.callcenter.entity.Achieve
import com.ingins.callcenter.entity.Achivement
import com.ingins.callcenter.entity.Events
import com.ingins.callcenter.repository.AchivementRepository
import com.ingins.callcenter.repository.EventsRepository
import com.ingins.callcenter.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.*
import java.time.temporal.ChronoField

@Service
class EventsServiceImpl(
    private val eventsRepository: EventsRepository,
    private val userRepository: UserRepository,
    private val competitionService: CompetitionService,
    private val achivementRepository: AchivementRepository
) : EventsService {

    @Transactional
    override fun createEvent(createEvent: CreateEventDto): Events {
        val user = userRepository.findById(createEvent.userId).get()

        val addList = mutableListOf<Achieve>()

        val allAchivement = achivementRepository.findAll()
        allAchivement.filter { ach ->
            !(user.data.achieves.map {
                it.name
            }.contains(ach.name))
        }.forEach {

            val points = if (it.eventType == createEvent.type) {
                createEvent.points
            } else {
                0
            }

            val percent = if (it.eventType == createEvent.type) {
                (createEvent.points.toDouble() / it.points.toDouble() * 100.0).toInt()
            } else {
                0
            }

            addToList(
                it = it,
                addList = addList,
                points = points,
                percent = percent
            )
        }

        val updatedList = user.data.achieves.map { achieve ->
            val firstAch = allAchivement.firstOrNull {
                it.name == achieve.name
            }

            firstAch?.let {
                if (achieve.end.isAfter(Instant.now())) {
                    if (it.eventType == createEvent.type) {
                        achieve.copy(
                            points = achieve.points + createEvent.points,
                            percent = ((achieve.points + createEvent.points).toDouble()
                                    / it.points.toDouble() * 100.0).toInt()
                        )
                    } else {
                        achieve
                    }
                } else {
                    addChiv(it, createEvent, addList, achieve)
                }
            } ?: achieve
        }

        user.data = user.data.copy(
            achieves = updatedList.plus(
                addList
            )
        )

        val savedUser = userRepository.save(user)

        val newEvent = eventsRepository.save(
            Events(
                user = savedUser,
                type = createEvent.type,
                data = createEvent.data,
                points = createEvent.points
            )
        )
        return newEvent
    }

    private fun addChiv(
        it: Achivement,
        createEvent: CreateEventDto,
        addList: MutableList<Achieve>,
        achieve: Achieve
    ): Achieve {
        val points = if (it.eventType == createEvent.type) {
            createEvent.points
        } else {
            0
        }

        val percent = if (it.eventType == createEvent.type) {
            (createEvent.points.toDouble() / it.points.toDouble() * 100.0).toInt()
        } else {
            0
        }

        addToList(it, addList, points, percent)
        return achieve
    }

    private fun addToList(
        it: Achivement,
        addList: MutableList<Achieve>,
        points: Int,
        percent: Int
    ) {
        when (it.duration) {
            "month" -> {
                addList.add(
                    Achieve(
                        begin = YearMonth.from(Instant.now().atZone(ZoneId.of("UTC")))
                            .atDay(1)
                            .atStartOfDay(ZoneId.of("UTC")).toInstant(),
                        end = YearMonth.from(Instant.now().atZone(ZoneId.of("UTC")))
                            .atEndOfMonth()
                            .atTime(23, 59).toInstant(ZoneOffset.UTC),
                        name = it.name,
                        description = it.description,
                        points = points,
                        percent = percent
                    )
                )
            }
            "week" -> {
                addList.add(
                    Achieve(
                        begin = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1)
                            .atStartOfDay(ZoneId.of("UTC")).toInstant(),
                        end = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 7)
                            .atTime(23, 59).toInstant(ZoneOffset.UTC),
                        name = it.name,
                        description = it.description,
                        points = points,
                        percent = percent
                    )
                )
            }
            "day" -> {
                addList.add(
                    Achieve(
                        begin = LocalDate.now()
                            .atStartOfDay(ZoneId.of("UTC")).toInstant(),
                        end = LocalDate.now()
                            .atTime(23, 59).toInstant(ZoneOffset.UTC),
                        name = it.name,
                        description = it.description,
                        points = points,
                        percent = percent
                    )
                )
            }
            else -> {
                // Empty
            }
        }
    }
}