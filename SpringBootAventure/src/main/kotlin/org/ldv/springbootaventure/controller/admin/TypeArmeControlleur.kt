package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.TypeArmeDAO
import org.ldv.springbootaventure.model.entity.TypeArme
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class TypeArmeControlleur(val typeArmeDAO : TypeArmeDAO) {


    /**
     * Affiche la liste de tous les types d'arme.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/type-arme")
    fun index(model: Model): String {

        // Récupère tous les types d'arme depuis la base de données
        val typeArme = this.typeArmeDAO.findAll()

        // Ajoute la liste des types d'arme au modèle pour affichage dans la vue
        model.addAttribute("typeArme", typeArme)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArme/index"
    }


    /**
     * Affiche les détails d'un type d'arme en particulier.
     *
     * @param id L'identifiant unique du type d'arme à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si le type d'arme avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/type-arme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {

        // Récupère le type d'arme avec l'ID spécifié depuis la base de données
        val unType = this.typeArmeDAO.findById(id).orElseThrow()

        // Ajoute le type d'arme au modèle pour affichage dans la vue
        model.addAttribute("typeArme", unType)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArme/show"
    }


    /**
     * Affiche le formulaire de création d'un nouveau type d'arme.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/type-arme/create")
    fun create(model: Model): String {

        // Crée une nouvelle instance de TypeArme avec des valeurs par défaut
       val nouvelleTypeArme = TypeArme(null, "", 1, 6,2,20)

        // Ajoute le nouveau type d'arme au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleType", nouvelleTypeArme)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/TypeArme/create"
    }


    /**
     * Gère la soumission du formulaire d'ajout du type d'arme.
     *
     * @param nouveauTypeArme L'objet typeArme créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des types d'arme après l'ajout réussi.
     */
    @PostMapping("/admin/type-arme")
    fun store(@ModelAttribute nouvelleTypeArme: TypeArme, redirectAttributes: RedirectAttributes): String {

        // Sauvegarde le nouveau type d'arme dans la base de données
        val savedTypeArme = this.typeArmeDAO.save(nouvelleTypeArme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeArme.nom} réussi")

        // Redirige vers la page d'administration des types d'arme
        return "redirect:/admin/type-arme"
    }


    @GetMapping("/admin/type-arme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {

        // Récupère le type d'arme avec l'ID spécifié depuis la base de données
        val uneTypeArme = this.typeArmeDAO.findById(id).orElseThrow()

        // Ajoute le type d'arme au modèle pour affichage dans la vue
        model.addAttribute("typeArme", uneTypeArme)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArme/edit"
    }


    /**
     * Gère la soumission du formulaire de mise à jour de typeArme.
     *
     * @param typeArme L'objet TypeArme mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des types d'arme après la mise à jour réussie.
     * @throws NoSuchElementException si le type d'arme avec l'ID spécifié n'est pas trouvé dans la base de données.
     */
    @PostMapping("/admin/type-arme/update")
    fun update(@ModelAttribute typeArme: TypeArme, redirectAttributes: RedirectAttributes): String {

        // Recherche du type d'arme existant dans la base de données
        val typeArmeModifier = this.typeArmeDAO.findById(typeArme.id ?: 0).orElseThrow()

        // Mise à jour des propriétés du type d'arme avec les nouvelles valeurs du formulaire
        typeArmeModifier.nom = typeArme.nom
        typeArmeModifier.nombreDes = typeArme.nombreDes
        typeArmeModifier.valeurDeMax = typeArme.valeurDeMax
        typeArmeModifier.multiplicateurCritique = typeArme.multiplicateurCritique
        typeArmeModifier.activationCritique = typeArme.activationCritique

        // Sauvegarde le type d'arme modifié dans la base de données
        val savedTypeArme = this.typeArmeDAO.save(typeArmeModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeArme.nom} réussie")

        // Redirige vers la page d'administration des types d'arme
        return "redirect:/admin/type-arme"
    }


    /**
     * Gère la suppression d'un typeArme par son identifiant.
     *
     * @param id L'identifiant du typeArme à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des types d'arme après la suppression réussie.
     * @throws NoSuchElementException si le type d'arme avec l'ID spécifié n'est pas trouvé dans la base de données.
     */
    @PostMapping("/admin/type-arme/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {

        // Recherche le typeArme à supprimer dans la base de données
        val typeArme: TypeArme = this.typeArmeDAO.findById(id).orElseThrow()

        // Suppression du typeArme de la base de données
        this.typeArmeDAO.delete(typeArme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typeArme.nom} réussie")

        // Redirige vers la page d'administration des types d'arme
        return "redirect:/admin/type-arme"
    }
}
