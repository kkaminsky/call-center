package com.ingins.callcenter.repository

import com.ingins.callcenter.entity.Achivement
import com.ingins.callcenter.entity.Events
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AchivementRepository: JpaRepository<Achivement, UUID>{

}