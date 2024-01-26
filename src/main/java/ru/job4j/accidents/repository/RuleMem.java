package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class RuleMem implements RuleRepository {
    private AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Rule> rules = new HashMap<>();

    private RuleMem() {
        save(new Rule(1, "Статья 1"));
        save(new Rule(2, "Статья 2"));
        save(new Rule(3, "Статья 3"));
    }

    @Override
    public Rule save(Rule rule) {
        rule.setId(nextId.incrementAndGet());
        rules.put(rule.getId(), rule);
        return rule;
    }


    @Override
    public boolean deleteById(int id) {
        return rules.remove(id) != null;
    }

    @Override
    public boolean update(Rule rule) {
        return rules.computeIfPresent(rule.getId(), (id, oldRule) -> new Rule(oldRule.getId(),
                rule.getName())) != null;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }

    @Override
    public Set<Rule> findRuleByIds(String[] ids) {
        return Arrays.stream(ids)
                .map(x -> rules.get(Integer.parseInt(x)))
                .collect(Collectors.toSet());
    }
}
