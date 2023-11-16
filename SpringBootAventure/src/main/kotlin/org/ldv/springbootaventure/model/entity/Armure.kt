package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
@DiscriminatorValue("armure")
class Armure constructor(
    id: Long,
    nom: String,
    description: String,
    cheminImage: String?,
//TODO Attributs spécifiques aux armures
    //Association entre org.ldv.springbootaventure.model.entity.Arme et Qualite
    //Plusieurs armes peuvent être rataché a une qualite

    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    @ManyToOne
    @JoinColumn(name = "type_armure_id")
     var typeArmure: TypeArmure? = null

    //Association avec TypeArmure
):Item(id, nom, description, cheminImage){

}