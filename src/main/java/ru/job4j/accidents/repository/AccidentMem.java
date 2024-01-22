package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {
    private AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Accident> accidents = new HashMap<>();

    private AccidentMem() {
        save(new Accident(0, "T567DF", "Задел бампер", "г. Пенза ул. Байдукова", new AccidentType(1, "Две машины")));
        save(new Accident(0, "M9456FD", "Разбита фара", "г. Пенза ул. Горная",new AccidentType(2, "Машина и человек")));
        save(new Accident(0, "U455GF", "Парковка на газоне", "г. Пенза ул. Нейтральная",new AccidentType(3, "Машина и велосипед")));
        save(new Accident(0, "O745VD", "Проезд на красный", "г. Пенза ул. Рябова",new AccidentType(1, "Две машины")));
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
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType())) != null;
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
