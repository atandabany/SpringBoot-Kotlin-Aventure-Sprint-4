package org.ldv.springbootaventure.model.dao;

import org.ldv.springbootaventure.model.entity.Armure
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ArmureDAO : JpaRepository<Armure, Long> {
    fun findByNomContains(nom: String, pageable: Pageable): Page<Armure>
}