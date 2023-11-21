package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class TypeAccessoire constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id : Long? = null,
    var nom : String,
    var bonusType: String,
    @OneToMany(mappedBy = "typeAccessoire", cascade = [CascadeType.REMOVE])
    var accessoires: MutableList<Accessoire> = mutableListOf()
) {

}