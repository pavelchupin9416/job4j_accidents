package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RuleRepository {
    Rule save(Rule rule);

    boolean deleteById(int id);

    boolean update(Rule rule);

    Optional<Rule> findById(int id);

    Collection<Rule> findAll();

    Set<Rule> findRuleByIds(String[] ids);
}
