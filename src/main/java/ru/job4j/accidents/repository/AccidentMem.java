package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class AccidentMem implements AccidentRepository {
    private AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Accident> accidents = new HashMap<>();

    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    private AccidentMem(AccidentTypeRepository accidentTypeRepository, RuleRepository ruleRepository) {
        this.accidentTypeRepository = accidentTypeRepository;
        this.ruleRepository = ruleRepository;
        save(new Accident(0, "T567DF", "Задел бампер", "г. Пенза ул. Байдукова", accidentTypeRepository.findById(1).get(),
                Set.of(ruleRepository.findById(1).get(), ruleRepository.findById(2).get())));
        save(new Accident(0, "M9456FD", "Разбита фара", "г. Пенза ул. Горная", accidentTypeRepository.findById(2).get(),
                Set.of(ruleRepository.findById(1).get(), ruleRepository.findById(3).get())));
        save(new Accident(0, "U455GF", "Парковка на газоне", "г. Пенза ул. Нейтральная", accidentTypeRepository.findById(3).get(),
                Set.of(ruleRepository.findById(2).get())));
        save(new Accident(0, "O745VD", "Проезд на красный", "г. Пенза ул. Рябова", accidentTypeRepository.findById(1).get(),
                Set.of(ruleRepository.findById(1).get())));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }


    @Override
    public boolean deleteById(int id) {
        return accidents.remove(id) != null;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> new Accident(oldAccident.getId(),
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType(), accident.getRules())) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
