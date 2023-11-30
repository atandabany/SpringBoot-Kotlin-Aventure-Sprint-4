package org.ldv.springbootaventure.model.dao;

import org.ldv.springbootaventure.model.entity.Arme
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable

interface ArmeDAO : JpaRepository<Arme, Long> {
     fun findByNomContainsIgnoreCase(nom: String , pageable: Pageable): Page<Arme>


}