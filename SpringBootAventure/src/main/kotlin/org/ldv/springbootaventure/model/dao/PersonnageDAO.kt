package org.ldv.springbootaventure.model.dao
import org.ldv.springbootaventure.model.entity.Personnage
import org.springframework.data.jpa.repository.JpaRepository

class PersonnageDAO(personnageDAO : JpaRepository<Personnage, Long>) {
}