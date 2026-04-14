package com.filterapi.models;

import com.filterapi.enums.Operators;

/**
 * A record representing a logical predicate for combining multiple filter
 * predicates.
 * This in itself doesn't do any evaluations, but chains other predicates
 * together to allow more complex evaluations. There is no limit currently to
 * how many can be chained. You can have multiple in OR or AND statements, and
 * you can nest as deeply as you wish.
 */
public record LogicalPredicate(Operators operator, FilterPredicate... predicates) implements FilterPredicate {

  /**
   * Constructor for LogicalPredicate that takes an operator and a variable number
   * of predicates.
   * This allows for creating a logical predicate with a single predicate in one
   * statement.
   * 
   * @param operator  the logical operator to be applied to the predicates (AND,
   *                  OR, NOT)
   * @param predicate the single predicate to be evaluated with the logical
   *                  operator
   */
  public LogicalPredicate(Operators operator, FilterPredicate predicate) {
    this(operator, new FilterPredicate[] { predicate });
  }

  /**
   * Constructor for LogicalPredicate that takes an operator string and a variable
   * number
   * of predicates.
   * This allows for creating a logical predicate with multiple predicates in one
   * statement.
   * 
   * @param operator   the logical operator to be applied to the predicates (AND,
   *                   OR, NOT)
   * @param predicates the predicates to be evaluated with the logical operator
   */
  public LogicalPredicate(String operator, FilterPredicate... predicates) {
    this(Operators.valueOf(operator.toUpperCase()), predicates);
  }

  /**
   * Constructor for LogicalPredicate that takes an operator string and a single
   * predicate.
   * This allows for creating a logical predicate with a single predicate in one
   * statement.
   * 
   * @param operator  the logical operator to be applied to the predicates (AND,
   *                  OR, NOT)
   * @param predicate the single predicate to be evaluated with the logical
   *                  operator
   */
  public LogicalPredicate(String operator, FilterPredicate predicate) {
    this(Operators.valueOf(operator.toUpperCase()), new FilterPredicate[] { predicate });
  }

  /**
   * Evaluate the logical predicate based on the provided context. This cycles
   * through all the Predicates and applies the logical operator to their
   * evaluations. For AND, all predicates must evaluate to true for the result to
   * be true. For OR, at least one predicate must evaluate to true for the result
   * to be true. For NOT, the single predicate must evaluate to false for the
   * result to be true. If the operator is NOT and there are not exactly one
   * predicate, an IllegalArgumentException is thrown.
   *
   * @param context The context to evaluate the predicate against.
   * @return true if the predicate evaluates to true based on the context, false
   *         otherwise.
   */
  @Override
  public boolean evaluate(Object context) {
    return switch (operator) {
      case AND -> {
        boolean result = true;
        for (FilterPredicate predicate : predicates) {
          result = result && predicate.evaluate(context);
        }
        yield result;
      }
      case OR -> {
        boolean result = false;
        for (FilterPredicate predicate : predicates) {
          result = result || predicate.evaluate(context);
        }
        yield result;
      }
      case NOT -> {
        if (predicates.length != 1) {
          throw new IllegalArgumentException("NOT operator requires exactly one operand");
        }
        yield !predicates[0].evaluate(context);
      }
    };
  }

  /**
   * Returns a string representation of the LogicalPredicate.
   *
   * @return a string representation of the LogicalPredicate.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("LogicalPredicate{operator=").append(operator).append(", predicates=[");
    for (int i = 0; i < predicates.length; i++) {
      sb.append(predicates[i].toString());
      if (i < predicates.length - 1) {
        sb.append(", ");
      }
    }
    sb.append("]}");
    return sb.toString();
  }

}
