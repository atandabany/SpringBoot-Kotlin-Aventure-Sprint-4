package org.ldv.springbootaventure.model.dao

import org.ldv.springbootaventure.model.entity.Armure
import org.ldv.springbootaventure.model.entity.Campagne
import org.ldv.springbootaventure.model.entity.Combat
import org.springframework.data.jpa.repository.JpaRepository

interface CampagneDAO : JpaRepository<Campagne, Long> {
}