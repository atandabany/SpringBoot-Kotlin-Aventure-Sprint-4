package org.ldv.springbootaventure.controller.admin

import org.ldv.springbootaventure.model.dao.TypeArmeDAO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TypeArmeControlleur(val typeArmeDAO : TypeArmeDAO) {

    @GetMapping("/admin/type-arme")
    fun index(model: Model): String {
        // Récupère toutes les qualités depuis la base de données
        val typeArme = this.typeArmeDAO.findAll()

        // Ajoute la liste des qualités au modèle pour affichage dans la vue
        model.addAttribute("typeArme", typeArme)

        // Retourne le nom de la vue à afficher
        return "admin/typeArme/index"
    }

}