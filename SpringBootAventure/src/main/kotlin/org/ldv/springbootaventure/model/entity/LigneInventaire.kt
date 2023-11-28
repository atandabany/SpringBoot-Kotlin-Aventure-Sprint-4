package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
class LigneInventaire (

    // Utilisation d'une clé composite pour l'entité LigneInventaire
    @EmbeddedId
    var ligneInventaireId : LigneInventaireId ,

    // Association Many-to-One avec l'entité Personnage en utilisant la clé composite
    // La clé étrangère personnage_id est mappée sur la clé composite personnageId de LigneInventaireId
    @MapsId("personnageId")
    @ManyToOne
    @JoinColumn(name="personnage_id")
    var personnage : Personnage? = null,

    // Association Many-to-One avec l'entité Item en utilisant la clé composite
    // La clé étrangère item_id est mappée sur la clé composite itemId de LigneInventaireId
    @MapsId("itemId")
    @ManyToOne
    @JoinColumn(name="item_id")
    var item : Item? = null,

    var quantite : Int){

}