package com.ingins.callcenter.mapper

import com.ingins.callcenter.dto.StateDto
import com.ingins.callcenter.entity.state.State
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface StateMapper {

    fun map(entity: State): StateDto
}