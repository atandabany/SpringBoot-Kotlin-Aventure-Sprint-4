package org.ldv.springbootaventure.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainControlleur {
    @GetMapping
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