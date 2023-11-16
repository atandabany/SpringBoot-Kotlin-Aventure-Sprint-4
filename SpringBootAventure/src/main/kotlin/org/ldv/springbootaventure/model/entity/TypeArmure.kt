package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class TypeArmure constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,
    var bonusType: Int,

    @OneToMany(mappedBy = "typeArmure", cascade = [CascadeType.REMOVE])
     var armures: MutableList<Armure> = mutableListOf()
) {

}