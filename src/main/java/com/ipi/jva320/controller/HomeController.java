package com.ipi.jva320.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ipi.jva320.service.SalarieAideADomicileService;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;


    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        String msgPluriel = "salariés";

        if (nombreSalarie == 1) {
            msgPluriel = "salarié";
        }

        modelAndView.addObject("msgHome", "Bienvenue dans l'interface d'administration RH !");
        modelAndView.addObject("nbSalarie", nombreSalarie);
        modelAndView.addObject("msgPluriel", msgPluriel);
        modelAndView.addObject("msgTitle", "Aide à domicile RH - gestion des salariés");
        return modelAndView;
    }
}
