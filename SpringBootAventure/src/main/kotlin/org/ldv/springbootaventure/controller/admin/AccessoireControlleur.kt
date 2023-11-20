package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.AccessoireDAO
import org.ldv.springbootaventure.model.entity.Accessoire
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class AccessoireControlleur(val accessoireDAO : AccessoireDAO) {
    /**
     * Affiche la liste de toutes les accessoires
     * @param model Le modèle utilisé pour transmettre les données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/accessoire")
    fun index(model: Model): String {
        // Récupère toutes les qualités depuis la base de données
        val accessoire = this.accessoireDAO.findAll()

        // Ajoute la liste des qualités au modèle pour affichage dans la vue
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
        // Récupère l'armure avec l'ID spécifié depuis la base de données
        val unAccessoire = this.accessoireDAO.findById(id).orElseThrow()

        // Ajoute l'armure au modèle pour affichage dans la vue
        model.addAttribute("accessoire", unAccessoire)

        // Retourne le nom de la vue à afficher
        return "admin/accessoire/show"
    }

    @GetMapping("/admin/accesoire/create")
    fun create(model: Model): String {
        // Crée une nouvelle instance de Qualite avec des valeurs par défaut
        val nouvelleAccessoire = Accessoire(0,"" , "" , "")
        val qualites = accessoireDAO.findAll()

        // Ajoute la nouvelle qualité au modèle pour affichage dans le formulaire de création
        model.addAttribute("nouvelleAccessoire",nouvelleAccessoire)
        model.addAttribute("qualites",qualites)

        // Retourne le nom de la vue à afficher (le formulaire de création)
        return "admin/accessoire/create"
    }


}