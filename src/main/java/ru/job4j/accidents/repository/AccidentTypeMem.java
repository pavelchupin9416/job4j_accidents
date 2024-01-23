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
public class AccidentTypeMem implements AccidentTypeRepository{
    private AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, AccidentType> accidentTypes = new HashMap<>();

    private AccidentTypeMem() {
        save(new AccidentType(1, "Две машины"));
        save( new AccidentType(2, "Машина и человек"));
        save(new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public AccidentType save(AccidentType accidentType) {
        accidentType.setId(nextId.incrementAndGet());
        accidentTypes.put(accidentType.getId(), accidentType);
        return accidentType;
    }


    @Override
    public boolean deleteById(int id) {
        return accidentTypes.remove(id) != null;
    }

    @Override
    public boolean update(AccidentType accidentType) {
        return accidentTypes.computeIfPresent(accidentType.getId(), (id, oldAccident) -> new AccidentType(oldAccident.getId(),
                accidentType.getName())) != null;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypes.values();
    }
}
