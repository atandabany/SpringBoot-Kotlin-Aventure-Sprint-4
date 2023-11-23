package org.ldv.springbootaventure.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Personnage constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long,
    var nom: String,
    var attaque: Int,
    var defense: Int,
    var endurance: Int,
    var vitesse: Int,
    var cheminImage: String,

    @OneToMany(mappedBy = "personnage")
    val ligneInventaire: MutableList<LigneInventaire> = mutableListOf(),


    @ManyToOne
    val accesoireEquipe: Accessoire? = null,

    @ManyToOne
    var armeEquipe: Arme? = null,

    @ManyToOne
    var armureEquipe: Armure? = null


) {
    val pointDeVieMax: Int
        get() = 50 + (10 * (this.endurance))

    var pointDeVie: Int = this.pointDeVieMax
        set(value) {
            field = minOf(value, this.pointDeVieMax)
        }


    /**
     * @author Adrien
     * Méthode qui permet d'additionner les caractéristiques de l'armure à la défense total du personnage
     * @return result
     */
    fun calculeDefense(): Int {
        var result = this.defense / 2
        if (this.armureEquipe != null) {
            result = result + this.armureEquipe!!.calculProtection()
        }
        return result;
    }


    /**
     * @author Steeven
     * @param adversaire
     * Méthode pour attaquer un adversaire
     * if armeEquipe vérifie si le joueur possède une arme ou non si c'est le cas calcule les degats
     * A savoir : degat est toujours = 1
     */
    fun attaquer(adversaire: Personnage): String {

        // Vérifier si le personnage a une arme équipée
        var degats = this.attaque / 2
        if (armeEquipe != null) {

            // Calculer les dégâts en utilisant les attributs du personnage et la méthode calculerDegat de l'arme
            degats += this.armeEquipe!!.calculerDegats()
        }

        // Appliquer la défense de l'adversaire (au minimum au moins 1 de dégat)
        val degatsInfliges = maxOf(1, degats - adversaire.calculeDefense())

        // Appliquer les dégâts à l'adversaire
        adversaire.pointDeVie = adversaire.pointDeVie - degatsInfliges

        return ("$nom attaque ${adversaire.nom} avec ${armeEquipe?.nom ?: "une attaque de base"} et inflige $degatsInfliges points de dégâts.")
    }


    /**
     * Ajoute une ligne d'inventaire pour l'item spécifié avec la quantité donnée.
     * Si une ligne d'inventaire pour cet item existe déjà, met à jour la quantité.
     * Si la quantité résultante est inférieure ou égale à zéro, la ligne d'inventaire est supprimée.
     *
     * @param unItem L'item pour lequel ajouter ou mettre à jour la ligne d'inventaire.
     * @param uneQuantite La quantité à ajouter à la ligne d'inventaire existante ou à la nouvelle ligne.
     */
    fun ajouterLigneInventaire(unItem: Item, uneQuantite: Int) {
        // Chercher une ligne d'inventaire existante pour l'item spécifié
        val ligneItem = this.ligneInventaire.find { ligneInventaire -> ligneInventaire.item == unItem }

        // Si aucune ligne d'inventaire n'est trouvée, en créer une nouvelle
        if (ligneItem == null) {
            // Créer un nouvel identifiant pour la ligne d'inventaire
            val ligneInventaireId = LigneInventaireId(this.id!!, unItem.id!!)

            // Ajouter une nouvelle ligne d'inventaire à la liste
            this.ligneInventaire.add(LigneInventaire(ligneInventaireId, this, unItem, uneQuantite))
        } else {
            // Si une ligne d'inventaire existante est trouvée, mettre à jour la quantité
            ligneItem.quantite += uneQuantite

            // Si la quantité résultante est inférieure ou égale à zéro, supprimer la ligne d'inventaire
            if (ligneItem.quantite <= 0) {
                this.ligneInventaire.remove(ligneItem)
            }
        }
    }


    /**
     * Loot l'inventaire de la cible en transférant les items et équipements dans l'inventaire du looteur.
     *
     * @param cible Le personnage dont l'inventaire sera looted.
     * @return Un message décrivant les items looted et les actions effectuées.
     */
    fun loot(cible: Personnage): String {
        // Déséquiper l'arme et l'armure de la cible
        cible.armeEquipe = null
        cible.armureEquipe = null
        // Variable pour stocker les messages générés pendant le loot
        var msg = ""
        // Parcourir chaque ligne d'inventaire de la cible
        for (uneLigne: LigneInventaire in cible.ligneInventaire) {
            // Ajouter les items et quantités lootés à l'inventaire du looteur
            this.ajouterLigneInventaire(uneLigne.item!!, uneLigne.quantite)

            // Construire un message décrivant l'action pour chaque item looté
            msg += "${this.nom} récupère ${uneLigne.quantite} ${uneLigne.item} <br>"
        }
        // Retourner le message global décrivant l'action de loot
        return msg
    }
}


