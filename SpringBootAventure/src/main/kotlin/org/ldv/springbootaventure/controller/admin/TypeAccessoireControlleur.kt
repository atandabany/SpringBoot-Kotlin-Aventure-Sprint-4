package org.ldv.springbootaventure.controller.admin


import org.ldv.springbootaventure.model.dao.TypeAccessoireDAO
import org.ldv.springbootaventure.model.entity.TypeAccessoire
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.Model

@Controller
class TypeAccessoireControlleur(val typeAccessoireDAO: TypeAccessoireDAO) {
    @GetMapping("/admin/type-accessoire")
    fun index(model: Model): String{
        // Récupère toutes les accessoires depuis la base de données
        val typeAccessoire=this.typeAccessoireDAO.findAll()

        // Ajoute la liste des qualités au modèle pour affichage dans la vue
        model.addAttribute("typeAccessoire", typeAccessoire)

        // Retourne le nom de la vue à afficher
        return "admin/type-accessoire/index"
    }


}
