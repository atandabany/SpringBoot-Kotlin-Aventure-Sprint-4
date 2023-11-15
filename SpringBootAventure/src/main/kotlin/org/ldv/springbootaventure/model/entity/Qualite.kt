package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
class Qualite constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,

    //@Column(name = "truc") (Permet de renommer la colonne couleur -> truc)
    var couleur: String,

    //@Transient (Permet de supprimer la colonne bonusQualite)
    var bonusQualite: Int
) {

}