package ru.job4j.accidents.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidents.findAll());
        return "accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "createAccident";
    }

    @PostMapping("/createAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var accidentOptional = accidents.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "editAccident";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, Model model) {
        try {
            var isUpdated = accidents.update(accident);
            if (!isUpdated) {
                model.addAttribute("message", "Инцидент с указанным идентификатором не найдена");
                return "errors/404";
            }
            return "redirect:/accidents";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
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