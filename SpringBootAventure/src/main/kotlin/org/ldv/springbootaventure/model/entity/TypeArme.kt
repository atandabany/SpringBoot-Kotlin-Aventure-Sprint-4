package org.ldv.springbootaventure.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class TypeArme constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,
    var nombreDes: Int,
    var valeurDeMax:Int,
    var multiplicateurCritique:Int,
    var activationCritique:Int,

    @OneToMany(mappedBy = "typeArme", cascade = [CascadeType.REMOVE])
    var armes: MutableList<Arme> = mutableListOf(),

) {

}