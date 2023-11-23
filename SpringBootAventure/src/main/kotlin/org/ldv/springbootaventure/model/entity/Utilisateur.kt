package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Utilisateur constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var email: String,
    var mdp: String,

    //Association entre org.ldv.springbootaventure.model.entity.Utilisateur et Role
    //Un ou plusieurs utilisateur peut avoir plusieurs role et l'inverse est possible
    @ManyToMany
    @JoinTable(name = "personne_role" , joinColumns = [JoinColumn(name = "personne_id")], inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: List<Role>? = null,

    //Association entre org.ldv.springbootaventure.model.entity.Utilisateur et Campagne
    //Un utilisateur peut avoir plusieurs campagne
    @OneToMany
    @JoinColumn(name = "campagne_id")
    var campagne : MutableList<Campagne> = mutableListOf(),
){

}