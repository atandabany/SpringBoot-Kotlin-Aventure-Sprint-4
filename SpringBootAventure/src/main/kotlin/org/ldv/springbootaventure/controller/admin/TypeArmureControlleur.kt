package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.TypeArmureDAO
import org.ldv.springbootaventure.model.entity.TypeArmure
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class TypeArmureControlleur(val typeArmureDao: TypeArmureDAO) {


    /**
     * Affiche la liste de tous les types d'armure.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/type-armure")
    fun index(model: Model): String {

        // Récupère toutes les types d'armures depuis la base de données
        val typeArmures = this.typeArmureDao.findAll()

        // Ajoute la liste des qualités au modèle pour affichage dans la vue
        model.addAttribute("typeArmures", typeArmures)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArmure/index"
    }


    /**
     * Affiche les détails d'un type d'armure en particulier.
     *
     * @param id L'identifiant unique du type d'armure à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si le type d'armure avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/type-armure/{id}")
    fun show(@PathVariable id: Long, model: Model): String {

        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val unTypeArmure = this.typeArmureDao.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("typeArmure", unTypeArmure)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArmure/show"
    }


    /**
     * Affiche le formulaire de création d'un nouveau type d'armure.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/type-armure/create")
    fun create(model: Model): String {

        // Crée une nouvelle instance de TypeArmure avec des valeurs par défaut
        val nouveauTypeArmure = TypeArmure(null, "",0)

        // Ajoute la nouveau type d'armure au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouveauTypeArmure", nouveauTypeArmure)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/TypeArmure/create"
    }


    /**
     * Gère la soumission du formulaire d'ajout de type d'armure.
     *
     * @param nouveau type d'armure L'objet TypeArmure créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des types d'armure après l'ajout réussi.
     */
    @PostMapping("/admin/type-armure")
    fun store(@ModelAttribute nouveauTypeArmure: TypeArmure, redirectAttributes: RedirectAttributes): String {

        // Sauvegarde la nouveau type d'armure dans la base de données
        val savedTypeArmure = this.typeArmureDao.save(nouveauTypeArmure)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeArmure.nom} réussi")

        // Redirige vers la page d'administration des types d'armure
        return "redirect:/admin/type-armure"
    }


    @GetMapping("/admin/type-armure/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {

        // Récupère le type d'armure avec l'ID spécifié depuis la base de données
        val unTypeArmure = this.typeArmureDao.findById(id).orElseThrow()

        // Ajoute la type d'armure au modèle pour affichage dans la vue
        model.addAttribute("typeArmure", unTypeArmure)

        // Retourne le nom de la vue à afficher
        return "/admin/TypeArmure/edit"
    }


    /**
     * Gère la soumission du formulaire de mise à jour de typeArmure.
     *
     * @param typeArmure L'objet TypeArmure mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des types d'armure après la mise à jour réussie.
     * @throws NoSuchElementException si le type d'armure avec l'ID spécifié n'est pas trouvé dans la base de données.
     */
    @PostMapping("/admin/type-armure/update")
    fun update(@ModelAttribute typeArmure: TypeArmure, redirectAttributes: RedirectAttributes): String {

        // Recherche du type d'armure existant dans la base de données
        val typeArmureModifier = this.typeArmureDao.findById(typeArmure.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de la qualité avec les nouvelles valeurs du formulaire
        typeArmureModifier.nom = typeArmure.nom
        typeArmureModifier.bonusType = typeArmure.bonusType

        // Sauvegarde le type d'armure modifié dans la base de données
        val savedTypeArmure = this.typeArmureDao.save(typeArmureModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeArmure.nom} réussie")

        // Redirige vers la page d'administration des types d'armure
        return "redirect:/admin/type-armure"
    }


    /**
     * Gère la suppression d'un type d'armure par son identifiant.
     *
     * @param id L'identifiant du type d'armure à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des types d'armure après la suppression réussie.
     * @throws NoSuchElementException si le type d'armure avec l'ID spécifié n'est pas trouvé dans la base de données.
     */
    @PostMapping("/admin/type-armure/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {

        // Recherche du type d'armure à supprimer dans la base de données
        val typeArmure: TypeArmure = this.typeArmureDao.findById(id).orElseThrow()

        // Suppression du type d'armure de la base de données
        this.typeArmureDao.delete(typeArmure)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typeArmure.nom} réussie")

        // Redirige vers la page d'administration des types d'armure
        return "redirect:/admin/type-armure"
    }
}