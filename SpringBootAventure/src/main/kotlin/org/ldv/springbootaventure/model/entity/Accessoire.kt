package org.ldv.springbootaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
@DiscriminatorValue("accessoire")
class Accessoire constructor(
    id: Long?,
    nom: String,
    description: String,
    cheminImage: String?,


//Association entre org.ldv.springbootaventure.model.entity.accessoire et Qualite
    //Plusieurs accessoire peuvent être rataché a une qualite
    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    //Association entre org.ldv.springbootaventure.model.entity.accessoire et TypeAccessoire
    //Plusieurs armes peuvent être rataché a un accessoire
    @ManyToOne
    @JoinColumn(name = "type_Accessoire_id")
    var typeAccessoire: TypeAccessoire? = null
):Item(id, nom, description, cheminImage
){

}