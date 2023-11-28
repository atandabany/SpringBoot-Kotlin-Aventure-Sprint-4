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
    var typeArmure: TypeArmure? = null,

    @OneToMany(mappedBy = "armureEquipe")
    var personnages : MutableList<Personnage> = mutableListOf()



):Item(id, nom, description, cheminImage){

    /**@author Adrien
     * @return type + rareté
     * Méthode "calculProtection" pour calculer la protection de l'armure
     */
    fun calculProtection(): Int {
        var additionProtection = this.typeArmure!!.bonusType + this.qualite!!.bonusQualite
        return additionProtection
    }


    /**
     * Équipe l'arme sur un personnage, permettant au personnage de l'utiliser pour attaquer.
     *
     * @param cible Le personnage sur lequel l'arme est équipée.
     */
    override fun utiliser(cible: Personnage):String {
        return cible.equipe(this)
    }
}