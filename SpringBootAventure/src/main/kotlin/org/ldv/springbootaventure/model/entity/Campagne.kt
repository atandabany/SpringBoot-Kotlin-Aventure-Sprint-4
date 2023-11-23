package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class Campagne constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var etat: String,
    var dernierScore: Int,
    var meilleurScore: Int,

    @OneToMany(mappedBy = "campagne", cascade = [CascadeType.REMOVE])
    var combats: MutableList<Combat> = mutableListOf(),

    // Association entre org.ldv.springbootaventure.model.entity.Combat et Campagne
    // Plusieurs campagnes peuvent être ratachés à un personnage
    @ManyToOne
    @JoinColumn(name = "personnage_id")
    var hero: Personnage? = null
) {
}