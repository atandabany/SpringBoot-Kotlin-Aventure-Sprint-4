package org.ldv.springbootaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
@DiscriminatorValue("accessoire")
class Accessoire constructor(
    id: Long,
    nom: String,
    description: String,
    cheminImage: String?,

    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    @ManyToOne
    @JoinColumn(name = "type_Accessoire_id")
    var typeAccessoire: TypeAccessoire? = null
):Item(id, nom, description, cheminImage
){

}