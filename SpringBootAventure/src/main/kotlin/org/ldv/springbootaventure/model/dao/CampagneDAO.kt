package org.ldv.springbootaventure.model.dao

import org.ldv.springbootaventure.model.entity.Campagne
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


import org.springframework.data.geo.GeoResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CampagneDAO : JpaRepository<Campagne, Long> {
    fun findByNomContainsIgnoreCaseAndAuteur_Email(nom: String, email: String, pageable: Pageable): Page<Campagne>

}