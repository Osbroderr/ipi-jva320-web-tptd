package com.ipi.jva320.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
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
    @ResponseBody
    public ModelAndView list(@RequestParam("page") Optional<Integer> page, 
    @RequestParam("size") Optional<Integer> size) {
        ModelAndView modelAndView = new ModelAndView("list");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<SalarieAideADomicile> salariePage = salarieAideADomicileService.getSalaries(PageRequest.of(currentPage - 1, pageSize));

        modelAndView.addObject("salariePage", salariePage);
        int totalPages = salariePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
                modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        
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
    public ModelAndView update(@PathVariable Long id, SalarieAideADomicile salarieAideADomicile)
            throws EntityExistsException, SalarieException {
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
        ModelAndView modelAndView404 = new ModelAndView("404");
        List<SalarieAideADomicile> salariesFound = salarieAideADomicileService.getSalaries(name);
        Long nombreSalarie = salarieAideADomicileService.countSalaries();
        if (salariesFound.isEmpty()) {
            modelAndView404.addObject("nbSalarie", nombreSalarie);
            return modelAndView404;
        }
        modelAndView.addObject("salaries", salariesFound);
        modelAndView.addObject("nbSalarie", nombreSalarie);
        return modelAndView;
    }
}
