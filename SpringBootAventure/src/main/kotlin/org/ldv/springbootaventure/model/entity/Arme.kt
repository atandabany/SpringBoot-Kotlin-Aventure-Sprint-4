package org.ldv.springbootaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.ldv.springbootaventure.model.entity.Item
import org.ldv.springbootaventure.model.entity.Qualite

@Entity
@DiscriminatorValue("arme")
class Arme constructor(
    id: Long,
    nom: String,
    description: String,
    cheminImage: String?,
//TODO Attributs spécifiques aux armes
    //Association entre org.ldv.springbootaventure.model.entity.Arme et Qualite
    //Plusieurs armes peuvent être rataché a une qualite
    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    @ManyToOne
    @JoinColumn(name = "typeArme_id")
    var typeArme : TypeArme? = null
    //TODO Faire l'association avec TypeArme
) : Item(id, nom, description, cheminImage)



{

}