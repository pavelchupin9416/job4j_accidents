package ru.job4j.accidents.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidents;
    private final AccidentTypeService accidentTypes;
    private final RuleService rules;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidents.findAll());
        return "accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types",  accidentTypes.findAll());
        model.addAttribute("rules",  rules.findAll());
        return "createAccident";
    }

    @PostMapping("/createAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.save(accident, ids);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String getById(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidents.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("types", accidentTypes.findAll());
        model.addAttribute("rules",  rules.findAll());
        model.addAttribute("accident", accidentOptional.get());
        return "editAccident";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, Model model, HttpServletRequest req) {
            String[] ids = req.getParameterValues("rIds");
            var isUpdated = accidents.update(accident, ids);
            if (!isUpdated) {
                model.addAttribute("message", "Инцидент с указанным идентификатором не найдена");
                return "errors/404";
            }
            return "redirect:/accidents";
    }


    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = accidents.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Инцидент с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/accidents";
    }
}