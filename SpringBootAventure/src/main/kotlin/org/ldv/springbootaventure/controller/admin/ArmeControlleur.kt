package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.ArmeDAO
import org.ldv.springbootaventure.model.dao.QualiteDAO
import org.ldv.springbootaventure.model.entity.Arme
import org.ldv.springbootaventure.model.entity.Qualite
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class ArmeControlleur (val armeDao : ArmeDAO, private val qualiteDAO: QualiteDAO){

    /**
     * Affiche la liste de toutes les arme
     *
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/arme")
    fun index(model: Model): String {
        // Récupère toutes les qualités depuis la base de données
        val arme = this.armeDao.findAll()

        // Ajoute la liste des qualités au modèle pour affichage dans la vue
        model.addAttribute("arme", arme)

        // Retourne le nom de la vue à afficher
        return "admin/arme/index"
    }
    /**
     * Affiche les détails d'une arme en particulier.
     *
     * @param id L'identifiant unique de la qualité à afficher.
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée.
     */
    @GetMapping("/admin/arme/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val uneArme = this.armeDao.findById(id).orElseThrow()

        // Ajoute la qualité au modèle pour affichage dans la vue
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
        // Crée une nouvelle instance de Qualite avec des valeurs par défaut
        val nouvelleArme = Arme(0,"" , "" , "")
        val qualites = qualiteDAO.findAll()

        // Ajoute la nouvelle qualité au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleArme",nouvelleArme)
        model.addAttribute("qualites",qualites)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/arme/create"
    }


    /**
     * Gère la soumission du formulaire d'ajout de qualité.
     *
     * @param nouvelleArme L'objet Qualite créé à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après l'ajout réussi.
     */
    @PostMapping("/admin/arme")
    fun store(@ModelAttribute nouvelleArme:Arme, redirectAttributes: RedirectAttributes): String {
        // Sauvegarde la nouvelle qualité dans la base de données

        val savedArme = this.armeDao.save(nouvelleArme)
        //savedArme.qualite=nouvelleQualite



        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedArme.nom} réussi")
        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/arme"
    }

    @GetMapping("/admin/arme/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        // Récupère la qualité avec l'ID spécifié depuis la base de données
        val uneArme = this.armeDao.findById(id).orElseThrow()
        // Ajoute la qualité au modèle pour affichage dans la vue
        model.addAttribute("arme", uneArme)

        // Retourne le nom de la vue à afficher
        return "admin/arme/edit"
    }

    /**
     * Gère la soumission du formulaire de mise à jour de qualité.
     *
     * @param qualite L'objet Qualite mis à jour à partir des données du formulaire.
     * @param redirectAttributes Les attributs de redirection pour transmettre des messages à la vue suivante.
     * @return La redirection vers la page d'administration des qualités après la mise à jour réussie.
     * @throws NoSuchElementException si la qualité avec l'ID spécifié n'est pas trouvée dans la base de données.
     */
    @PostMapping("/admin/arme/update")
    fun update(@ModelAttribute arme: Arme, redirectAttributes: RedirectAttributes,qualite: Qualite): String {
        // Recherche de la qualité existante dans la base de données
        val armeModifier = this.armeDao.findById(arme.id ?: 0).orElseThrow()

        // Mise à jour des propriétés de la qualité avec les nouvelles valeurs du formulaire
        armeModifier.nom = arme.nom
        armeModifier.description = arme.description
        armeModifier.cheminImage = arme.cheminImage
        armeModifier.qualite!!.id= arme.qualite!!.id


        // Sauvegarde la qualité modifiée dans la base de données
        val savedArme = this.armeDao.save(armeModifier)

        // Ajoute un message de succès pour être affiché à la vue suivante
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${arme.nom} réussie")

        // Redirige vers la page d'administration des qualités
        return "redirect:/admin/arme"
    }


}