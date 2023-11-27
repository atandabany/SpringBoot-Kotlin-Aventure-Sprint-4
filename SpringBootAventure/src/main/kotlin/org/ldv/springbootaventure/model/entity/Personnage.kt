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
    var accesoireEquipe: Accessoire? = null,

    @ManyToOne
    var armeEquipe: Arme? = null,

    @ManyToOne
    var armureEquipe: Armure? = null,

    @OneToMany
    var compagne: MutableList<Campagne> = mutableListOf(),

    @OneToMany
    var combat: MutableList<Combat> = mutableListOf(),

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

    fun equipe(uneArme: Arme): String {
//        val listItem:MutableList<Item> = mutableListOf()
//        for (ligneItem in this.ligneInventaire){
//            listItem.add(ligneItem.item!!)
//      }


//      val listeItem: List<Item?> =ligneInventaire.map { ligneInventaire -> ligneInventaire.item }
//        if (uneArme in listeItem) {
//            armeEquipe = uneArme
//            println("$nom équipe « ${uneArme.nom} ».")
//        }

        if (ligneInventaire.any { ligneInventaire -> ligneInventaire.item == uneArme }) {
            armeEquipe = uneArme
            //println("$nom équipe « ${uneArme.nom} ».")
        }
        return "${nom} équipe << ${uneArme.nom} >>."
    }


    fun equipe(uneArmure: Armure): String {
        val listItem:MutableList<Item> = mutableListOf()
        for (ligneItem in this.ligneInventaire){
            listItem.add(ligneItem.item!!)
        }
        if (uneArmure in listItem){
            armureEquipe = uneArmure
        }
        return "${nom} équipe << ${uneArmure.nom} >>."

    }

    
   // Parcourir la liste de l'inventaire item et équipe un accessoire si un accessoire est dans l'inventaire
    fun equipe(unAccessoire: Accessoire) : String {
        if (ligneInventaire.any { ligneInventaire -> ligneInventaire.item == unAccessoire }) {
            accesoireEquipe = unAccessoire
        }
        return "${nom} équipe << ${unAccessoire.nom} >>"
    }

    /**
     * Vérification si le personnage a une potion dans son inventaire.
     *
     * @return true si le personnage a une potion, false sinon.
     */
    fun aUnePotion(): Boolean {
        // Utiliser la méthode any pour vérifier si une ligne d'inventaire contient une Potion
        return this.ligneInventaire.any { ligneInventaire -> ligneInventaire.item is Potion }
    }

    /**
     * Méthode pour boire une potion de l'inventaire du personnage.
     *
     * @param consomer Spécifie si la potion doit être consommée (décrémentant la quantité) ou non.
     *                 Par défaut, la potion est consommée.
     * @return Un message décrivant l'action effectuée, tel que boire la potion ou l'absence de potion.
     */
    fun boirePotion(consomer: Boolean = true): String {
        // Message par défaut si le personnage n'a pas de potion dans son inventaire
        var msg = "$nom n'a pas de potion dans son inventaire."

        // Vérifier si le personnage a une potion dans son inventaire
        if (this.aUnePotion()) {
            // Filtrer les lignes d'inventaire pour obtenir celles qui contiennent des potions
            val lignePotions: List<LigneInventaire> =
                this.ligneInventaire.filter { ligneInventaire -> ligneInventaire.item is Potion }

            // Utiliser la première potion dans la liste et obtenir le message résultant de l'utilisation
            msg = lignePotions[0].item!!.utiliser(this)

            // Si consomer est false, augmenter la quantité de potions dans l'inventaire
            if (!consomer) {
                lignePotions[0].quantite += 1
            }
        }

        // Retourner le message décrivant l'action effectuée
        return msg
    }

}


