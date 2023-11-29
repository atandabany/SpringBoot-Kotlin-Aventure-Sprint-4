package org.ldv.springbootaventure.controller

import org.ldv.springbootaventure.model.dao.UtilisateurDAO
import org.ldv.springbootaventure.model.entity.Utilisateur
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.regex.Pattern

@Controller
class MainControlleur(private val utilisateurDAO: UtilisateurDAO,val encoder: PasswordEncoder) {
    @GetMapping("/accueil")
    fun index():String{
        return "accueil"
    }


    @GetMapping("/login")
    fun login(@RequestParam error: Boolean?, model: Model): String {
        // Ajoute un attribut "error" au modèle si la requête contient une erreur
        model.addAttribute("error", error == true)
        return "visiteur/login"
    }

    /**
     * Méthode pour afficher la page d'inscription.
     *
     * @param model Le modèle Spring utilisé pour passer des données à la vue Thymeleaf.
     * @return Le nom de la vue Thymeleaf à afficher pour la page d'inscription.
     */
    @GetMapping("/inscription")
    fun inscription(@RequestParam error: Boolean?, model: Model): String {
        // Crée un nouvel utilisateur avec des valeurs par défaut et l'ajoute au modèle
        val utilisateur = Utilisateur(null, "", "")

        model.addAttribute("utilisateur", utilisateur)
        model.addAttribute("erreur", error == true)
        return "/visiteur/inscription"
    }


    @PostMapping("/inscription")
    fun traitementInscription(
        @ModelAttribute utilisateur: Utilisateur,
        @RequestParam confirmationMdp: String,
        model: Model,
        redirectAttributes: RedirectAttributes
    ): String {
        // Liste pour stocker les messages d'erreur potentiels
        var erreurs: MutableList<String> = mutableListOf()

        // Vérification si le mot de passe et la confirmation correspondent
        if (utilisateur.mdp != confirmationMdp) {
            erreurs.add("Le mot de passe et la confirmation ne correspondent pas")
        }
        // Expression régulière pour valider la complexité du mot de passe
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +     	// au moins 1 chiffre
                    "(?=.*[a-z])" +     	// au moins 1 lettre minuscule
                    "(?=.*[A-Z])" +     	// au moins 1 lettre majuscule
                    "(?=.*[a-zA-Z])" +  	// n'importe quelle lettre
                    "(?=.*[@#$%^&+=])" +    // au moins 1 caractère spécial
                    "(?=\\S+$)" +       	// pas d'espaces blancs
                    ".{8,}" +           	// au moins 8 caractères
                    "$"
        )

        // Vérification de la complexité du mot de passe
        if (!passwordREGEX.matcher(utilisateur.mdp).matches()) {
            erreurs.add("Le mot de passe doit contenir un nombre, une lettre minuscule, une lettre majuscule, au moins un caractère spécial et une longueur d'au moins 8 caractères")
        }

        // Vérification si l'adresse email est déjà utilisée
        if (utilisateurDAO.findByEmail(utilisateur.email) != null) {
            erreurs.add("Ce compte existe déjà")
        }

        // Si des erreurs sont détectées, les ajouter au modèle et retourner à la page d'inscription
        if (erreurs.size != 0) {
            model.addAttribute("erreurs", erreurs)
            model.addAttribute("utilisateur", utilisateur)
            return "/visiteur/inscription"
        } else {
            // Si aucune erreur, encoder le mot de passe, sauvegarder l'utilisateur,
            // et rediriger vers la page de connexion avec un message de succès
            utilisateur.mdp = this.encoder.encode(utilisateur.mdp)
            val nouvelleUtilisateur = utilisateurDAO.save(utilisateur)
            redirectAttributes.addFlashAttribute("msgSuccess", "Inscription réussie")
            return "redirect:/login"
        }
    }

}