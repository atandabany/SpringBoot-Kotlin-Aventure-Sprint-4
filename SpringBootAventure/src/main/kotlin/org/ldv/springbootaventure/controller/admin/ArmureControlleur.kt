package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.ArmureDAO
import org.ldv.springbootaventure.model.dao.QualiteDAO
import org.ldv.springbootaventure.model.dao.TypeArmureDAO
import org.ldv.springbootaventure.model.entity.Armure
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class ArmureControlleur (val armureDao: ArmureDAO, val qualiteDao: QualiteDAO, val typeArmureDao: TypeArmureDAO) {


    /**
     * Affiche la liste de toutes les armures.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/armure")
    fun index(model: Model): String {

        // Récupère toutes les armures depuis la base de données
        val armures = this.armureDao.findAll()

        // Ajoute la liste des armures au modèle pour affichage dans la vue
        model.addAttribute("armures", armures)

        // Retourne le nom de la vue à afficher
        return "admin/armure/index"
    }


    /**
     * Affiche les détails d'une armure en particulier.
     *
     * @param id L'identifiant unique de l'armure à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si l'armure avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/armure/{id}")
    fun show(@PathVariable id: Long, model: Model): String {

        // Récupère l'armure avec l'ID spécifié depuis la base de données
        val uneArmure = this.armureDao.findById(id).orElseThrow()

        // Ajoute l'armure au modèle pour affichage dans la vue
        model.addAttribute("armure", uneArmure)

        // Retourne le nom de la vue à afficher
        return "admin/armure/show"
    }


    /**
     * Affiche le formulaire de création d'une nouvelle armure.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/armure/create")
    fun create(model: Model): String {

        // Crée une nouvelle instance de Armure avec des valeurs par défaut
        val nouvelleArmure = Armure(0, "", "", "")
        val qualites = qualiteDao.findAll()
        val typeArmure = typeArmureDao.findAll()

        // Ajoute la nouvelle armure, la qualite et le type armure  au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleArmure", nouvelleArmure)
        model.addAttribute("qualites",qualites)
        model.addAttribute("typeArmure" ,typeArmure)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/armure/create"
    }


    /**
     * Gère la soumission du formulaire d'ajout de armure.
     *
     * @param nouvelleArmure L'objet Armure créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des armures après l'ajout réussi.
     */
    @PostMapping("/admin/armure")
        fun store(@ModelAttribute nouvelleArmure: Armure, redirectAttributes: RedirectAttributes): String {

        // Sauvegarde la nouvelle armure dans la base de données
        val savedArmure = this.armureDao.save(nouvelleArmure)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedArmure.nom} réussi")

        // Redirige vers la page d'administration des armures
        return "redirect:/admin/armure"
    }


    @GetMapping("/admin/armure/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {

        // Récupère l'armure avec l'ID spécifié depuis la base de données
        val uneArmure = this.armureDao.findById(id).orElseThrow()
        val qualites = qualiteDao.findAll()
        val typeArmure = typeArmureDao.findAll()

        // Ajoute l'armure au modèle pour affichage dans la vue
        model.addAttribute("armure", uneArmure)
        model.addAttribute("qualites",qualites)
        model.addAttribute("typeArmure",typeArmure)

        // Retourne le nom de la vue à afficher
        return "admin/armure/edit"
    }


    /**
     * Gère la soumission du formulaire de mise à jour de armure.
     *
     * @param armure L'objet Armure mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des armures après la mise à jour réussie.
     * @throws NoSuchElementException si l'armure avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/armure/update")
    fun update(@ModelAttribute armure: Armure, redirectAttributes: RedirectAttributes): String {

        // Recherche de l'armure existante dans la base de données
        val armureModifier = this.armureDao.findById(armure.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de l'armure avec les nouvelles valeurs du formulaire
        armureModifier.nom = armure.nom
        armureModifier.description = armure.description
        armureModifier.cheminImage = armure.cheminImage

        // Récupère le qualite d'un objet "armure" depuis la base de données et le met à jour dans un autre objet "armureModifier"
        // Autre possibilité => armureModifier.qualite=armure.qualite
        val laQualite = qualiteDao.findById(armure.qualite!!.id ?:0).orElseThrow()
        armureModifier.qualite=laQualite

        // Récupère le type d'armure d'un objet "armure" depuis la base de données et le met à jour dans un autre objet "armureModifier".
        // Autre possibilité => armureModifier.typeArmure=armure.typeArmure
        val leTypeArmure = typeArmureDao.findById(armure.typeArmure!!.id ?:0).orElseThrow()
        armureModifier.typeArmure=leTypeArmure

        // Sauvegarde l'armure modifiée dans la base de données
        val savedArmure = (this.armureDao).save(armureModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedArmure.nom} réussie")

        // Redirige vers la page d'administration des armures
        return "redirect:/admin/armure"
    }


    /**
     * Gère la suppression d'une armure par son identifiant.
     *
     * @param id L'identifiant de l'armure à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des armures après la suppression réussie.
     * @throws NoSuchElementException si l'armure avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/armure/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {

        // Recherche de l'armure à supprimer dans la base de données
        val armure: Armure = this.armureDao.findById(id).orElseThrow()

        // Suppression de l'armure de la base de données
        this.armureDao.delete(armure)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${armure.nom} réussie")

        // Redirige vers la page d'administration des armures
        return "redirect:/admin/armure"
    }
}