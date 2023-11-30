package org.ldv.springbootaventure.controller.admin
import org.ldv.springbootaventure.model.dao.CampagneDAO
import org.ldv.springbootaventure.model.dao.PersonnageDAO
import org.springframework.data.domain.Pageable

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CampagneControlleur(val campagneDao: CampagneDAO, val personnageDAO: PersonnageDAO) {

    @GetMapping("/joueur/campagne")
    fun index(
        model: Model,
        @RequestParam(required = false) search: String?,
        pageable: Pageable
    ): String {

        // Récupérer l'objet Principal
        val authentication: org.springframework.security.core.Authentication = SecurityContextHolder.getContext().authentication
        // Récupérer le nom d'utilisateur à partir de l'objet Principal
        val email: String = authentication.getName()

        // Utiliser la méthode du DAO pour récupérer les campagnes par utilisateur avec pagination et recherche
        val nomRechercher= if(search.isNullOrBlank()){""}else{search}
        val lesCampagnes = campagneDao.findByNomContainsIgnoreCaseAndAuteur_Email(nomRechercher, email, pageable)


        model.addAttribute("campagnes", lesCampagnes)
        // Ajouter le terme de recherche au modèle pour pré-remplir le champ de recherche dans la vue
        model.addAttribute("search", search)
        return "joueur/campagne/index"
    }
}