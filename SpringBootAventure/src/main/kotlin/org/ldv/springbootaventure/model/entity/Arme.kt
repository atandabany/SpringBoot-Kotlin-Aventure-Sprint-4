package org.ldv.springbootaventure.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.ldv.springbootaventure.model.entity.Item
import org.ldv.springbootaventure.model.entity.Qualite

@Entity
@DiscriminatorValue("arme")
class Arme constructor(
    id: Long?,
    nom: String,
    description: String,
    cheminImage: String?,

    // Association entre Arme et Qualite
    // Plusieurs armes peuvent être rattachées à une qualité
    @ManyToOne
    @JoinColumn(name = "qualite_id")
    var qualite: Qualite? = null,

    // association entre Arme et type Arme
    // Plusieurs armes peuvent partager le même type d'arme.
    @ManyToOne
    @JoinColumn(name = "typeArme_id")
    var typeArme: TypeArme? = null,

    // Association entre Arme et Personnage
    // Une arme peut être équipée par plusieurs personnages
    @OneToMany(mappedBy = "armeEquipe")
    var personnages: MutableList<Personnage> = mutableListOf()


) : Item(id, nom, description, cheminImage) {

    /**
     * @author Steeven
     * @param
     * @return resultat + qualite (bonus rareté)
     * Méthode pour calculer les dégats de l'arme en fonction du dès lancer.
     */
    fun calculerDegats(): Int {
        var resultat = TirageDes(this.typeArme!!.nombreDes, this.typeArme!!.valeurDeMax).lance()
        val desCritique = TirageDes(1, 20).lance()

        if (desCritique >= this.typeArme!!.activationCritique) {
            resultat = typeArme!!.activationCritique * this.typeArme!!.multiplicateurCritique
        }
        return resultat + this.qualite!!.bonusQualite
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