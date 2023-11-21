package org.ldv.springbootaventure.controller

import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainControlleur {
    @GetMapping("/accueil")
    fun index():String{
        return "accueil"
    }

    @GetMapping("/login")
    fun login():String{
        return "visiteur/login"
    }

    @GetMapping("/inscription")
    fun inscription():String{
        return "visiteur/inscription"
    }


}