package org.ldv.springbootaventure.model.entity

import jakarta.persistence.*

@Entity
@DiscriminatorValue("armure")
class Armure constructor(
    id: Long?,
    nom: String,
    description: String,
    cheminImage: String?,

    // Association entre org.ldv.springbootaventure.model.entity.Armure et Qualite
    // Plusieurs armures peuvent être rataché a une qualite
    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    // Association entre org.ldv.springbootaventure.model.entity.Armure et TypeArmure
    // Plusieurs armures peuvent être rataché a un type d'armure
    @ManyToOne
    @JoinColumn(name = "type_armure_id")
     var typeArmure: TypeArmure? = null

):Item(id, nom, description, cheminImage){
}