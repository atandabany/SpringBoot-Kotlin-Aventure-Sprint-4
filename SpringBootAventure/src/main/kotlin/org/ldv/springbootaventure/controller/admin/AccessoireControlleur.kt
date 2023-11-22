package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.AccessoireDAO
import org.ldv.springbootaventure.model.dao.QualiteDAO
import org.ldv.springbootaventure.model.dao.TypeAccessoireDAO
import org.ldv.springbootaventure.model.entity.Accessoire
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class AccessoireControlleur(val accessoireDAO : AccessoireDAO, val qualiteDAO: QualiteDAO, val typeAccessoireDAO: TypeAccessoireDAO) {


    /**
     * Affiche la liste de toutes les accessoires
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/accessoire")
    fun index(model: Model): String {

        // Récupère toutes les accessoires depuis la base de données
        val accessoire = this.accessoireDAO.findAll()

        // Ajoute la liste des accessoires au modèle pour affichage dans la vue
        model.addAttribute("accessoire", accessoire)

        // Retourne le nom de la vue à afficher
        return "admin/accessoire/index"
    }


    /**
     * Affiche les détails d'un accessoire en particulier.
     *
     * @param id L'identifiant unique de l'accessoire à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si l'accessoire avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/accessoire/{id}")
    fun show(@PathVariable id: Long, model: Model): String {

        // Récupère l'accessoire avec l'ID spécifié depuis la base de données
        val unAccessoire = this.accessoireDAO.findById(id).orElseThrow()

        // Ajoute l'accessoire au modèle pour affichage dans la vue
        model.addAttribute("accessoire", unAccessoire)

        // Retourne le nom de la vue à afficher
        return "admin/accessoire/show"
    }


    /**
     * Affiche le formulaire de création d'un accessoire.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/accessoire/create")
    fun create(model: Model): String {

        // Crée une nouvelle instance d'accessoire avec des valeurs par défaut
        val nouvelleAccessoire = Accessoire(0, "", "", "")
        val qualites = qualiteDAO.findAll()
        val typeAccessoire = typeAccessoireDAO.findAll()

        // Ajoute le nouveau accessoire au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleAccessoire", nouvelleAccessoire)
        model.addAttribute("qualites", qualites)
        model.addAttribute("typeAccessoire", typeAccessoire)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/accessoire/create"
    }


    /**
     * Gère la soumission du formulaire d'ajout de accessoire.
     *
     * @param nouvelleAccessoire L'objet Accessoire créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des accessoires après l'ajout réussi.
     */
    @PostMapping("/admin/accessoire")
    fun store(@ModelAttribute nouvelleAccessoire: Accessoire, redirectAttributes: RedirectAttributes): String {

        // Sauvegarde le nouveau accessoire dans la base de données
        val savedAccessoire = this.accessoireDAO.save(nouvelleAccessoire)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedAccessoire.nom} réussi")

        // Redirige vers la page d'administration des accessoires
        return "redirect:/admin/accessoire"
    }


    @GetMapping("/admin/accessoire/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {

        // Récupère l'accessoire avec l'ID spécifié depuis la base de données
        val unAccessoire = this.accessoireDAO.findById(id).orElseThrow()
        val qualites = qualiteDAO.findAll()
        val typeAccessoire = typeAccessoireDAO.findAll()

        // Ajoute l'accessoire au modèle pour affichage dans la vue
        model.addAttribute("accessoire", unAccessoire)
        model.addAttribute("qualites", qualites)
        model.addAttribute("typeAccessoire", typeAccessoire)

        // Retourne le nom de la vue à afficher
        return "admin/accessoire/edit"
    }


    /**
     * Gère la soumission du formulaire de mise à jour de accessoire.
     *
     * @param accessoire L'objet accessoire mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des accessoires après la mise à jour réussie.
     * @throws NoSuchElementException si la accessoire avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/accessoire/update")
    fun update(@ModelAttribute accessoire: Accessoire, redirectAttributes: RedirectAttributes): String {

        // Recherche de l'accessoire existante dans la base de données
        val accessoireModifier = this.accessoireDAO.findById(accessoire.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de l'accessoire avec les nouvelles valeurs du formulaire
        accessoireModifier.nom = accessoire.nom
        accessoireModifier.description = accessoire.description
        accessoireModifier.cheminImage = accessoire.cheminImage

        // Récupère le qualite d'un objet "accessoire" depuis la base de données et le met à jour dans un autre objet "accessoireModifier"
        // Autre possibilité => accessoireModifier.qualite=accessoire.qualite
        val laQualite = qualiteDAO.findById(accessoire.qualite!!.id ?: 0).orElseThrow()
        accessoireModifier.qualite = laQualite

        // Récupère le type d'accessoire d'un objet "accessoire" depuis la base de données et le met à jour dans un autre objet "accessoireModifier"
        // Autre possibilité => accessoireModifier.qualite=accessoire.qualite
        val leTypeAccessoire = typeAccessoireDAO.findById(accessoire.typeAccessoire!!.id ?: 0).orElseThrow()
        accessoireModifier.typeAccessoire = leTypeAccessoire

        // Sauvegarde l'accessoire modifiée dans la base de données
        val savedAccessoire = (this.accessoireDAO).save(accessoireModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedAccessoire.nom} réussie")

        // Redirige vers la page d'administration de l'accessoires
        return "redirect:/admin/accessoire"
    }


    /**
     * Gère la suppression d'un accessoire par son identifiant.
     *
     * @param id L'identifiant de l'accessoire à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des accessoires après la suppression réussie.
     * @throws NoSuchElementException si l'armure avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/accessoire/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {

        // Recherche de l'accessoire à supprimer dans la base de données
        val accessoire: Accessoire = this.accessoireDAO.findById(id).orElseThrow()

        // Suppression de l'accessoire de la base de données
        this.accessoireDAO.delete(accessoire)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${accessoire.nom} réussie")

        // Redirige vers la page d'administration des accessoires
        return "redirect:/admin/accessoire"
    }
}