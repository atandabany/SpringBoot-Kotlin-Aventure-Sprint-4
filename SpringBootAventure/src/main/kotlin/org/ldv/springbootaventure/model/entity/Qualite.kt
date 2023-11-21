package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

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
    var bonusQualite: Int,

    //Association entre Qualite et org.ldv.springbootaventure.model.entity.Arme
    //Une qualite peut avoir plusieurs armes
    @OneToMany(mappedBy = "qualite", cascade = [CascadeType.REMOVE])
    var armes: MutableList<Arme> = mutableListOf(),

    @OneToMany(mappedBy = "qualite", cascade = [CascadeType.REMOVE])
    var armures: MutableList<Armure> = mutableListOf(),

    @OneToMany(mappedBy = "qualite", cascade = [CascadeType.REMOVE])
    var accessoires: MutableList<Accessoire> = mutableListOf(),
) {
}
