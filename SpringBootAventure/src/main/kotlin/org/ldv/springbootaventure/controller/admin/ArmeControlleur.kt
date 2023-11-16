package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.ArmeDAO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ArmeControlleur ( val armeDao : ArmeDAO){

    /**
     * Affiche la liste de toutes les qualités.
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

}