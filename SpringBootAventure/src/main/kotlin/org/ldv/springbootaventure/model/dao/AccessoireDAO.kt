package org.ldv.springbootaventure.model.dao

import org.ldv.springbootaventure.model.entity.Accessoire
import org.ldv.springbootaventure.model.entity.Armure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page

interface AccessoireDAO : JpaRepository<Accessoire, Long> {
   fun findByNomContains(nom: String, pageable: Pageable): Page<Accessoire>

}