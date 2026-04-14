package com.filterapi.api;

import java.util.List;
import java.util.Map;

import com.filterapi.enums.Comparisons;
import com.filterapi.models.BooleanPredicate;
import com.filterapi.models.ComparisonPredicate;
import com.filterapi.models.FilterPredicate;
import com.filterapi.models.LogicalPredicate;

/**
 * API for filtering operations.
 */
public class Filter {

  /**
   * List of filter predicates to apply on the user data. This is the internal
   * holder for the process, and will hold all predicates
   * at the base level. This does allow multiple predicates, and the default
   * assumption is that they are going to be ANDed together,
   * but this can be changed by using Logical Predicates in place of one of the
   * other two FilterPredicate types.
   * This is an instance variable, so each instance of the Filter class will have
   * its own criteria list. This is ideal, as it allows
   * for more flexible filtering operations.
   */
  private List<FilterPredicate> criteria;

  /**
   * Default constructor for the Filter class.
   */
  public Filter() {
    criteria = List.of();
  }

  /**
   * Constructor for the Filter class that takes a single predicate. This is a
   * convenience constructor, as it allows for easy creation of a
   * filter with a single predicate.
   * 
   * @param predicate - The predicate to be added to the filter criteria. This
   *                  will be added to the criteria list as the only element.
   *                  If you want to add more predicates, you can use the other
   *                  constructor or add them manually to the criteria list.
   */
  public Filter(FilterPredicate predicate) {
    criteria = List.of(predicate);
  }

  /**
   * Constructor for the Filter class that takes a list of predicates. This allows
   * for more flexible creation of filters, as you can add as
   * many predicates as you want in one go.
   * 
   * @param predicates - The list of predicates to be added to the filter
   *                   criteria. This will be added to the criteria list as the
   *                   elements
   *                   of the list. If you want to add more predicates, you can
   *                   use the other constructor or add them manually to the
   *                   criteria list.
   */
  public Filter(List<FilterPredicate> predicates) {
    criteria = predicates;
  }

  /**
   * Adding to allow possible third parties to see the predicates inside of the
   * filter
   */
  public List<FilterPredicate> getCriteria() {
    return criteria;
  }

  /**
   * Method to evaluate if a given user matches the filter criteria. This method
   * will evaluate all predicates in the criteria list against
   * the given user, and return true if all predicates evaluate to true, and false
   * otherwise. This is the main method of the Filter class,
   * as it is what allows us to filter the user data based on the criteria we have
   * defined. This does default to an AND operation, as it uses
   * the allMatch method, but this can be changed by using Logical Predicates in
   * place of one of the other two FilterPredicate types. This also
   * means that if you WANT the and Function, you can string predicates without
   * using a LogicalPredicate, and only use that for the OR and
   * NOT operations, which is a nice feature.
   * 
   * @param user - The user to be evaluated against the filter criteria. This is a
   *             Map of String key-value pairs,
   *             where the keys are the fieldnames
   */
  public boolean matches(Map<String, String> user) {
    boolean matchVal = criteria.stream().allMatch(predicate -> predicate.evaluate(user));

    return matchVal;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Filter Criteria:\n");
    for (FilterPredicate predicate : criteria) {
      sb.append("- ").append(predicate.toString()).append("\n");
    }
    return sb.toString();
  }

  public static Filter parseFromString(String input) {
    java.util.List<FilterPredicate> predicates = new java.util.ArrayList<>();

    String[] lines = input.split("\n");

    // Skip the first line "Filter Criteria:" and process predicate lines
    for (int i = 1; i < lines.length; i++) {
      String line = lines[i].trim();

      // Skip empty lines
      if (line.isEmpty()) {
        continue;
      }

      // Remove the "- " prefix
      if (line.startsWith("- ")) {
        line = line.substring(2);
      }

      // Parse the predicate based on its type
      FilterPredicate predicate = parsePredicate(line);
      if (predicate != null) {
        predicates.add(predicate);
      }
    }

    return new Filter(predicates);
  }

  private static FilterPredicate parsePredicate(String predicateStr) {
    if (predicateStr.startsWith("BooleanPredicate")) {
      return new BooleanPredicate("dummy").parseFromString(predicateStr);
    } else if (predicateStr.startsWith("ComparisonPredicate")) {
      return new ComparisonPredicate("present").parseFromString(predicateStr);
    } else if (predicateStr.startsWith("LogicalPredicate")) {
      return new LogicalPredicate("and").parseFromString(predicateStr);
    }
    return null;
  }
}
