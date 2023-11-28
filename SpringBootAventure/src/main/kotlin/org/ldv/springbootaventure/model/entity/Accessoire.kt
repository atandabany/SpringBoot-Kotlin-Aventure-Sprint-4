package org.ldv.springbootaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

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
    var typeAccessoire: TypeAccessoire? = null,


    @OneToMany(mappedBy = "accesoireEquipe")
    val personnages: MutableList<Personnage> = mutableListOf()


):Item(id, nom, description, cheminImage
){

    /**
     * Équipe l'arme sur un personnage, permettant au personnage de l'utiliser pour attaquer.
     *
     * @param cible Le personnage sur lequel l'arme est équipée.
     */
    override fun utiliser(cible: Personnage):String {
        return cible.equipe(this)
    }

}