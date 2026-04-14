package com.sortingapi.api;

import java.util.List;
import java.util.Map;

import com.sortingapi.models.FilterPredicate;

/**
 * API for filtering operations.
 */
public class Filter {

    private static List<FilterPredicate> criteria;

    public Filter() {
        criteria = List.of();
    }

    public Filter(FilterPredicate predicate) {
        criteria = List.of(predicate);
    }

    public Filter(List<FilterPredicate> predicates) {
        criteria = predicates;
    }

    /**
     * Filter implementation.
     */
    public boolean matches(Map<String, String> user) {
        boolean matchVal = criteria.stream().allMatch(predicate -> predicate.evaluate(user));

        return matchVal;
    }
}
