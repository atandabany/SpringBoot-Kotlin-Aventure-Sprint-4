package org.ldv.springbootaventure.model.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import org.ldv.springbootaventure.model.entity.Item

// Déclare la classe comme une entité JPA.
@Entity
// Spécifie la valeur du discriminateur associée à cette sous-classe.

@DiscriminatorValue("potion")
class Potion(

    // Déclaration des propriétés de la classe org.ldv.springbootaventure.model.entity.Potion, héritées de la classe org.ldv.springbootaventure.model.entity.Item
    // L'identifiant unique de la potion
    id: Long? = null,

    // Le nom de la potion
    nom: String,

    // La description de la potion
    description: String,

    //Chemin ver l'image
    cheminImage:String?,

    // La propriété spécifique à la potion, le soin qu'elle procure
    val soin: Int

) : Item(id, nom, description,cheminImage) {
    // TODO Sprint 5 : méthode à implémenter pour la fonctionnalité "utiliser"
}
