package org.ldv.springbootaventure.model.dao

import org.ldv.springbootaventure.model.entity.Utilisateur
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UtilisateurDAO : JpaRepository<Utilisateur, Long> {
    @Query("select u from Utilisateur u where upper(u.email) = upper(?1)")
    fun findByEmail(email: String): Utilisateur?
}