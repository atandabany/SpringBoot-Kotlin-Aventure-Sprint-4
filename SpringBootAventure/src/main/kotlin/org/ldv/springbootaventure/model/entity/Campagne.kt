package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Campagne constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,
    var etat: String,
    var dernierScore: Int,
    var meilleurScore: Int,


    @OneToMany(mappedBy = "campagne", cascade = [CascadeType.REMOVE])
    var combats: MutableList<Combat> = mutableListOf(),


    // Association entre org.ldv.springbootaventure.model.entity.Campagne et Personnage
    // Plusieurs campagnes peuvent être ratachés à un personnage
    @ManyToOne
    @JoinColumn(name = "personnage_id")
    var hero: Personnage? = null,

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    var auteur:Utilisateur?=null,
) {
}