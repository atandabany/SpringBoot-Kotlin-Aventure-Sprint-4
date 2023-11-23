package org.ldv.springbootaventure.model.dao

import org.ldv.springbootaventure.model.entity.Combat
import org.springframework.data.jpa.repository.JpaRepository

interface CombatDAO : JpaRepository<Combat, Long> {
}