package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository accidentRepository;
    private final RuleRepository ruleRepository;

    public SimpleAccidentService(AccidentRepository accidentRepository, RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.ruleRepository = ruleRepository;
    }


    @Override
    public Accident save(Accident accident, String[] ids) {
        accident.setRules(ruleRepository.getSetRule(ids));
        return accidentRepository.save(accident);
    }

    @Override
    public boolean deleteById(int id) {
        return accidentRepository.deleteById(id);
    }

    @Override
    public boolean update(Accident accident, String[] ids) {
        accident.setRules(ruleRepository.getSetRule(ids));
        return accidentRepository.update(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }
}
