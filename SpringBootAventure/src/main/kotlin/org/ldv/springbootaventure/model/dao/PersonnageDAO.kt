package org.ldv.springbootaventure.model.dao
import org.ldv.springbootaventure.model.entity.Personnage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonnageDAO : JpaRepository<Personnage, Long> {
}