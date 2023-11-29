package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.ArmeDAO
import org.ldv.springbootaventure.model.dao.QualiteDAO
import org.ldv.springbootaventure.model.dao.TypeArmeDAO
import org.ldv.springbootaventure.model.entity.Arme
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class ArmeControlleur (val armeDao : ArmeDAO, private val qualiteDAO: QualiteDAO, private val typeArmeDAO: TypeArmeDAO){


    /**
     * Affiche la liste de toutes les armes
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/arme")
    fun index(model: Model): String {

        // Récupère toutes les armes depuis la base de données
        val arme = this.armeDao.findAll()

        // Ajoute la liste des armes au modèle pour affichage dans la vue
        model.addAttribute("arme", arme)

        // Retourne le nom de la vue à afficher
        return "admin/arme/index"
    }


    /**
     * Affiche les détails d'une arme en particulier.
     *
     * @param id L'identifiant unique de l'arme à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si l'arme avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/arme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {

        // Récupère l'arme avec l'ID spécifié depuis la base de données
        val uneArme = this.armeDao.findById(id).orElseThrow()

        // Ajoute l'arme au modèle pour affichage dans la vue
        model.addAttribute("arme", uneArme)

        // Retourne le nom de la vue à afficher
        return "admin/arme/show"
    }


    /**
     * Affiche le formulaire de création d'une nouvelle arme.
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher (le formulaire de création).
     */
    @GetMapping("/admin/arme/create")
    fun create(model: Model): String {

        // Crée une nouvelle instance de Arme avec des valeurs par défaut
        val nouvelleArme = Arme(0,"" , "" , "")
        val qualites = qualiteDAO.findAll()
        val typeArme = typeArmeDAO.findAll()
        // Ajoute la nouvelle arme, la qualité et le type d'arme au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleArme",nouvelleArme)
        model.addAttribute("qualites",qualites)
        model.addAttribute("typeArme" ,typeArme)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/arme/create"
    }


    /**
     * Gère la soumission du formulaire d'ajout d'arme.
     *
     * @param nouvelleArme L'objet Arme créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des armes après l'ajout réussi.
     */
    @PostMapping("/admin/arme")
    fun store(@ModelAttribute nouvelleArme:Arme, redirectAttributes: RedirectAttributes): String {

        // Sauvegarde la nouvelle arme dans la base de données
        val savedArme = this.armeDao.save(nouvelleArme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedArme.nom} réussi")

        // Redirige vers la page d'administration des armes
        return "redirect:/admin/arme"
    }


    @GetMapping("/admin/arme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {

        // Récupère l'arme avec l'ID spécifié depuis la base de données
        val uneArme = this.armeDao.findById(id).orElseThrow()
        val qualites = qualiteDAO.findAll()
        val typeArme = typeArmeDAO.findAll()


        // Ajoute l'arme au modèle pour affichage dans la vue
        model.addAttribute("arme", uneArme)
        model.addAttribute("qualites",qualites)
        model.addAttribute("typeArme",typeArme)

        // Retourne le nom de la vue à afficher
        return "admin/arme/edit"
    }


    /**
     * Gère la soumission du formulaire de mise à jour de l'arme.
     *
     * @param arme L'objet Arme mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des armes après la mise à jour réussie.
     * @throws NoSuchElementException si l'arme avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/arme/update")
    fun update(@ModelAttribute arme: Arme, redirectAttributes: RedirectAttributes): String {

        // Recherche de l'arme existante dans la base de données
        val armeModifier = this.armeDao.findById(arme.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de l'arme avec les nouvelles valeurs du formulaire
        armeModifier.nom = arme.nom
        armeModifier.description = arme.description
        armeModifier.cheminImage = arme.cheminImage

        // Récupère le qualite d'un objet "arme" depuis la base de données et le met à jour dans un autre objet "armeModifier"
        // Autre possibilité => armeModifier.qualite=arme.qualite
        val laQualite = qualiteDAO.findById(arme.qualite!!.id ?:0).orElseThrow()
        armeModifier.qualite=laQualite

        // Récupère le type d'arme d'un objet "arme" depuis la base de données et le met à jour dans un autre objet "armeModifier".
        // Autre possibilité => armeModifier.typeArme=arme.typeArme
        val leTypeArme= typeArmeDAO.findById(arme.typeArme!!.id ?:0).orElseThrow()
        armeModifier.typeArme= leTypeArme

        // Sauvegarde l'arme modifiée dans la base de données
        val savedArme = this.armeDao.save(armeModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${arme.nom} réussie")

        // Redirige vers la page d'administration des armes
        return "redirect:/admin/arme"
    }


    /**
     * Gère la suppression d'une arme par son identifiant.
     *
     * @param id L'identifiant de l'arme à supprimer.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la suppression réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/arme/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {

        // Recherche de l'arme à supprimer dans la base de données
        val arme: Arme = this.armeDao.findById(id).orElseThrow()

        // Suppression de l'arme de la base de données
        this.armeDao.delete(arme)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${arme.nom} réussie")

        // Redirige vers la page d'administration des armes
        return "redirect:/admin/arme"
    }
}