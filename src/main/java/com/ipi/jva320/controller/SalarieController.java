package com.ipi.jva320.controller;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;

@Controller
public class SalarieController {
    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    static final String DETAIL_SALARIE = "detail_Salarie";
    

    @GetMapping("/salaries/{id}")
    public ModelAndView getOneSalarie(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(DETAIL_SALARIE);
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("salarie", salarie);
        modelAndView.addObject("urlAddOrUpdate", "http://localhost:8080/salaries/" + id);
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }

    @GetMapping("/salaries")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("list");
        List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("salaries", salaries);
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }

    @GetMapping("/salaries/aide/new")
    public ModelAndView salarieVide() {
        ModelAndView modelAndView = new ModelAndView(DETAIL_SALARIE);
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("salarie", salarie);
        modelAndView.addObject("urlAddOrUpdate", "http://localhost:8080/salaries/save");
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }

    @PostMapping("/salaries/save")
    public ModelAndView add(SalarieAideADomicile salarieAideADomicile) throws EntityExistsException, SalarieException {
        ModelAndView modelAndView = new ModelAndView(DETAIL_SALARIE);
        SalarieAideADomicile newSalarie = new SalarieAideADomicile();
        newSalarie = salarieAideADomicileService.creerSalarieAideADomicile(salarieAideADomicile);
        Long id = newSalarie.getId();
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("salarie", newSalarie);
        modelAndView.addObject("urlAddOrUpdate", "http://localhost:8080/salaries/" + id);
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }
    @PostMapping("/salaries/{id}")
    public ModelAndView update(@PathVariable Long id, SalarieAideADomicile salarieAideADomicile) throws EntityExistsException, SalarieException {
        ModelAndView modelAndView = new ModelAndView(DETAIL_SALARIE);
        SalarieAideADomicile updateSalarie = new SalarieAideADomicile();
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        updateSalarie = salarieAideADomicileService.updateSalarieAideADomicile(salarieAideADomicile);
        modelAndView.addObject("salarie", updateSalarie);
        modelAndView.addObject("urlAddOrUpdate", "http://localhost:8080/salaries/" + id);
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }

    @GetMapping("/salaries/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) throws EntityExistsException, SalarieException {
        ModelAndView modelAndView = new ModelAndView("redirect:/salaries");
        SalarieAideADomicile deleteSalarie = new SalarieAideADomicile();
        salarieAideADomicileService.deleteSalarieAideADomicile(id);
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("salarie", deleteSalarie);
        modelAndView.addObject("urlAddOrUpdate", "http://localhost:8080/salaries/save");
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }

    @GetMapping("/salarie/findByNom")
    public ModelAndView findByNom(String name) {
        ModelAndView modelAndView = new ModelAndView("list");
        List<SalarieAideADomicile> salariesFound = salarieAideADomicileService.getSalaries(name);
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("salaries", salariesFound);
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }
}
