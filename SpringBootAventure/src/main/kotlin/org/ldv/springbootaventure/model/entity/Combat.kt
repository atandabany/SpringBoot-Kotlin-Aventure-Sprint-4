package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity

class Combat constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long?,
    var nombreTours: Int,


    // Association entre org.ldv.springbootaventure.model.entity.Combat et Campagne
    // Plusieurs combats peuvent être rataché à une campagne
    @ManyToOne
    @JoinColumn(name = "campagne_id")
    var campagne: Campagne? = null,

    // Association entre org.ldv.springbootaventure.model.entity.Combat et Campagne
    // Plusieurs combats peuvent être ratachés à un personnage
    @ManyToOne
    @JoinColumn(name = "personnage_id")
    var monstre: Personnage? = null

){

}