package org.ldv.springbootaventure.controller.admin


import org.ldv.springbootaventure.model.dao.TypeAccessoireDAO
import org.ldv.springbootaventure.model.entity.TypeAccessoire
import org.ldv.springbootaventure.model.entity.TypeArmure
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class TypeAccessoireControlleur(val typeAccessoireDAO: TypeAccessoireDAO) {
    /**
     * Affiche la liste de toutes les qualités.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/type-accessoire")
    fun index(model: Model): String {
        // Récupère toutes les accessoires depuis la base de données
        val typeAccessoire = this.typeAccessoireDAO.findAll()

        // Ajoute la liste des qualités au modèle pour affichage dans la vue
        model.addAttribute("typeAccessoire", typeAccessoire)

        // Retourne le nom de la vue à afficher
        return "admin/TypeAccessoire/index"
    }

    /**
     * Affiche les détails d'une qualité en particulier.
     *
     * @param id L'identifiant unique de la qualité à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/type-accessoire/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val unAccessoire = this.typeAccessoireDAO.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("typeAccessoire", unAccessoire)

        // Retourne le nom de la vue à afficher
        return "admin/TypeAccessoire/show"
    }

    /**
     * Affiche le formulaire de création d'une nouvelle qualité.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/type-accessoire/create")
    fun create(model: Model): String {
        // Crée une nouvelle instance de Qualite avec des valeurs par défaut
        val nouveauTypeAccessoire = TypeAccessoire(null, "", "")

        // Ajoute le nouveau type accessoire au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouveauTypeAccessoire", nouveauTypeAccessoire)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/TypeAccessoire/create"
    }

    /**
     * Gère la soumission du formulaire d'ajout de type d'armure.
     *
     * @param nouveau type d'accessoire L'objet TypeAccessoire créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des types d'accessoire après l'ajout réussi.
     */
    @PostMapping("/admin/type-accessoire")
    fun store(@ModelAttribute nouveauTypeAccessoire: TypeAccessoire, redirectAttributes: RedirectAttributes): String {
        // Sauvegarde la nouveau type d'accessoire dans la base de données
        val savedTypeAccessoire = this.typeAccessoireDAO.save(nouveauTypeAccessoire)
        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeAccessoire.nom} réussi")
        // Redirige vers la page d'administration des types d'accessoire
        return "redirect:/admin/type-accessoire"
    }

    @GetMapping("/admin/type-accessoire/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        // Récupère le type d'accessoire avec l'ID spécifié depuis la base de données
        val unAccessoire = this.typeAccessoireDAO.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("typeAccessoire", unAccessoire)

        // Retourne le nom de la vue à afficher
        return "admin/TypeAccessoire/edit"
    }

    /**
     * Gère la soumission du formulaire de mise à jour de qualité.
     *
     * @param TypeAccessoire L'objet TypeAccessoire mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la mise à jour réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/type-accessoire/update")
    fun update(@ModelAttribute typeAccessoire: TypeAccessoire, redirectAttributes: RedirectAttributes): String {
        // Recherche du type accessoire existante dans la base de données
        val typeAccessoireModifier = this.typeAccessoireDAO.findById(typeAccessoire.id ?: 0).orElseThrow()

        // Mise à jour des propriétés du type d'accessoire avec les nouvelles valeurs du formulaire
        typeAccessoireModifier.nom = typeAccessoire.nom
        typeAccessoireModifier.bonusType = typeAccessoire.bonusType

        // Sauvegarde la qualité modifiée dans la base de données
        val savedTypeAccessoire = this.typeAccessoireDAO.save(typeAccessoireModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeAccessoire.nom} réussie")

        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/type-accessoire"
    }

    /**
     * Gère la suppression d'une qualité par son identifiant.
     *
     * @param id L'identifiant du type d'accessoire à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la suppression réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/type-accessoire/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        // Recherche de la qualité à supprimer dans la base de données
        val typeAccessoire: TypeAccessoire = this.typeAccessoireDAO.findById(id).orElseThrow()

        // Suppression du type d'accessoire de la base de données
        this.typeAccessoireDAO.delete(typeAccessoire)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typeAccessoire.nom} réussie")

        // Redirige vers la page d'administration du type d'accessoire
        return "redirect:/admin/type-accessoire"
    }
}
