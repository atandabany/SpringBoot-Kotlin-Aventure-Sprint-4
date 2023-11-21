package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.TypeArmeDAO
import org.ldv.springbootaventure.model.entity.Qualite
import org.ldv.springbootaventure.model.entity.TypeArme
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class TypeArmeControlleur(val typeArmeDAO : TypeArmeDAO) {


    /**
     * Affiche la liste de toutes les typeArme.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/type-arme")
    fun index(model: Model): String {

        // Récupère toutes les typeArme depuis la base de données
        val typeArme = this.typeArmeDAO.findAll()

        // Ajoute la liste des typeArmes au modèle pour affichage dans la vue
        model.addAttribute("typeArme", typeArme)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArme/index"
    }


    /**
     * Affiche les détails d'un type en particulier.
     *
     * @param id L'identifiant unique d'un type d'arme à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si le typeArme avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/type-arme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {

        // Récupère le typeArme avec l'ID spécifié depuis la base de données
        val unType = this.typeArmeDAO.findById(id).orElseThrow()

        // Ajoute typeArme au modèle pour affichage dans la vue
        model.addAttribute("typeArme", unType)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArme/show"
    }


    /**
     * Affiche le formulaire de création d'un nouveau TypeArme.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/type-arme/create")
    fun create(model: Model): String {

        // Crée une nouvelle instance de Qualite avec des valeurs par défaut
       val nouvelleTypeArme = TypeArme(null, "", 1, 6,2,20)

        // Ajoute le nouveau typeArme au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleType", nouvelleTypeArme)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/TypeArme/create"
    }


    /**
     * Gère la soumission du formulaire d'ajout de typeArme.
     *
     * @param nouvelleQualite L'objet typeArme créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après l'ajout réussi.
     */
    @PostMapping("/admin/type-arme")
    fun store(@ModelAttribute nouvelleTypeArme: TypeArme, redirectAttributes: RedirectAttributes): String {

        // Sauvegarde la nouvelle qualité dans la base de données
        val savedTypeArme = this.typeArmeDAO.save(nouvelleTypeArme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedTypeArme.nom} réussi")

        // Redirige vers la page d'administration des typeArmes
        return "redirect:/admin/type-arme"
    }

    @GetMapping("/admin/type-arme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {

        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val uneTypeArme = this.typeArmeDAO.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("typeArme", uneTypeArme)

        // Retourne le nom de la vue à afficher
        return "admin/TypeArme/edit"
    }

    @PostMapping("/admin/type-arme/update")
    fun update(@ModelAttribute typeArme: TypeArme, redirectAttributes: RedirectAttributes): String {
        // Recherche de typeArme existante dans la base de données
        val typeArmeModifier = this.typeArmeDAO.findById(typeArme.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de typeArme avec les nouvelles valeurs du formulaire
        typeArmeModifier.nom = typeArme.nom
        typeArmeModifier.nombreDes = typeArme.nombreDes
        typeArmeModifier.valeurDeMax = typeArme.valeurDeMax
        typeArmeModifier.multiplicateurCritique = typeArme.multiplicateurCritique
        typeArmeModifier.activationCritique = typeArme.activationCritique

        // Sauvegarde le typeArme modifiée dans la base de données
        val savedTypeArme = this.typeArmeDAO.save(typeArmeModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedTypeArme.nom} réussie")

        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/type-arme"
    }


    /**
     * Gère la suppression un typeArme par son identifiant.
     *
     * @param id L'identifiant du typeArme à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la suppression réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/type-arme/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {

        // Recherche de le typeArme à supprimer dans la base de données
        val typeArme: TypeArme = this.typeArmeDAO.findById(id).orElseThrow()

        // Suppression du typeArme de la base de données
        this.typeArmeDAO.delete(typeArme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${typeArme.nom} réussie")

        // Redirige vers la page d'administration des typeArmes
        return "redirect:/admin/type-arme"
    }
}
