package com.sortingapi.models;

import com.sortingapi.enums.Operators;

public record LogicalPredicate(Operators operator, FilterPredicate... predicates) implements FilterPredicate {
    public LogicalPredicate(Operators operator, FilterPredicate predicate) {
        this(operator, new FilterPredicate[] { predicate });
    }

    public LogicalPredicate(String operator, FilterPredicate... predicates) {
        this(Operators.valueOf(operator.toUpperCase()), predicates);
    }

    public LogicalPredicate(String operator, FilterPredicate predicate) {
        this(Operators.valueOf(operator.toUpperCase()), new FilterPredicate[] { predicate });
    }

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
