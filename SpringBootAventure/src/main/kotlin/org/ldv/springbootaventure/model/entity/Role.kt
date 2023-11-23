package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Role constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,

    //Association entre org.ldv.springbootaventure.model.entity.Utilisateur et Role
    //Plusieurs role peut avoir plusieurs compte (admin ou joueur)
    @ManyToMany(mappedBy = "roles" , cascade = [CascadeType.REMOVE])
    var utilisateurs: MutableList<Utilisateur> = mutableListOf(),
){

}