package org.ldv.springbootaventure.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.ldv.springbootaventure.model.entity.Item
import org.ldv.springbootaventure.model.entity.Qualite

@Entity
@DiscriminatorValue("arme")
class Arme constructor(
    id: Long?,
    nom: String,
    description: String,
    cheminImage: String?,

    //Association entre org.ldv.springbootaventure.model.entity.Arme et arme
    //Plusieurs armes peuvent être rataché à une arme
    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    @ManyToOne
    @JoinColumn(name = "typeArme_id")
    var typeArme : TypeArme? = null

) : Item(id, nom, description, cheminImage)



{

}