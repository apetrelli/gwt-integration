package com.github.apetrelli.gwtintegration.jpa;

import java.util.List;

import javax.persistence.criteria.Predicate;

public class JpaUtil {

    public static void addWordPredicates(String wordsString, WordPredicateFactory factory, List<Predicate> predicates) {
        if (wordsString != null) {
            String[] words = wordsString.split("\\s+");
            for (String word: words) {
                predicates.add(factory.create(word));
            }
        }
    }

    public interface WordPredicateFactory {
        Predicate create(String word);
    }
}
