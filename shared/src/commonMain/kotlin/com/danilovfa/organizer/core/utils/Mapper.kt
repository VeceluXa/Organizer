package com.danilovfa.organizer.core.utils

interface Mapper<Entity, Domain> {
    fun fromDomain(domain: Domain): Entity
    fun toDomain(entity: Entity): Domain
}