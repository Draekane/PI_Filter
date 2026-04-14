package com.sortingapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sortingapi.api.Filter;
import com.sortingapi.models.BooleanPredicate;
import com.sortingapi.models.ComparisonPredicate;
import com.sortingapi.models.LogicalPredicate;

public class LogicalPredicateTests {
    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalNotPredicate V1")
    public void testLogicalPredicateSingleUser_LogicalNotPredicate_v1() {
        BooleanPredicate truePredicate = new BooleanPredicate("true");
        LogicalPredicate notTruePredicate = new LogicalPredicate("not", truePredicate);

        Filter filter = new Filter(notTruePredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(false, filter.matches(user), "Expected user to not match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalNotPredicate V2")
    public void testLogicalPredicateSingleUser_LogicalNotPredicate_v2() {
        BooleanPredicate truePredicate = new BooleanPredicate("true");
        LogicalPredicate notTruePredicate = new LogicalPredicate("not", truePredicate);

        Filter filter = new Filter(notTruePredicate);

        assertEquals(true, filter.matches(null), "Expected user to not match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalAndPredicate V1")
    public void testLogicalPredicateSingleUser_LogicalAndPredicate_v1() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("present", "firstName");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("present", "lastName");
        LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

        Filter filter = new Filter(andPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalAndPredicate V2")
    public void testLogicalPredicateSingleUser_LogicalAndPredicate_v2() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("present", "firstName");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("present", "nonExistentKey");
        LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

        Filter filter = new Filter(andPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(false, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalAndPredicate V3")
    public void testLogicalPredicateSingleUser_LogicalAndPredicate_v3() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "35");
        LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

        Filter filter = new Filter(andPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalAndPredicate V4")
    public void testLogicalPredicateSingleUser_LogicalAndPredicate_v4() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "30");
        LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

        Filter filter = new Filter(andPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(false, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalAndPredicate V5")
    public void testLogicalPredicateSingleUser_LogicalAndPredicate_v5() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "35");
        ComparisonPredicate thirdPredicate = new ComparisonPredicate("equals", "role", "Admin");
        ComparisonPredicate fourthPredicate = new ComparisonPredicate("matches_regex", "email", ".*@ghosted\\.com");
        LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate, thirdPredicate,
                fourthPredicate);

        Filter filter = new Filter(andPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalOrPredicate V1")
    public void testLogicalPredicateSingleUser_LogicalOrPredicate_v1() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("present", "firstName");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("present", "lastName");
        LogicalPredicate orPredicate = new LogicalPredicate("or", firstPredicate, secondPredicate);

        Filter filter = new Filter(orPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalOrPredicate V2")
    public void testLogicalPredicateSingleUser_LogicalOrPredicate_v2() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("present", "firstName");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("present", "nonExistentKey");
        LogicalPredicate orPredicate = new LogicalPredicate("or", firstPredicate, secondPredicate);

        Filter filter = new Filter(orPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalOrPredicate V3")
    public void testLogicalPredicateSingleUser_LogicalOrPredicate_v3() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("present", "anotherNonExistentKey");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("present", "nonExistentKey");
        LogicalPredicate orPredicate = new LogicalPredicate("or", firstPredicate, secondPredicate);

        Filter filter = new Filter(orPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(false, filter.matches(user), "Expected user to notmatch the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalOrPredicate V4")
    public void testLogicalPredicateSingleUser_LogicalOrPredicate_v4() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "35");
        LogicalPredicate orPredicate = new LogicalPredicate("or", firstPredicate, secondPredicate);

        Filter filter = new Filter(orPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalOrPredicate V5")
    public void testLogicalPredicateSingleUser_LogicalOrPredicate_v5() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "30");
        LogicalPredicate orPredicate = new LogicalPredicate("or", firstPredicate, secondPredicate);

        Filter filter = new Filter(orPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalOrPredicate V6")
    public void testLogicalPredicateSingleUser_LogicalOrPredicate_v6() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "40");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "30");
        LogicalPredicate orPredicate = new LogicalPredicate("or", firstPredicate, secondPredicate);

        Filter filter = new Filter(orPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(false, filter.matches(user), "Expected user to not match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - LogicalOrPredicate V7")
    public void testLogicalPredicateSingleUser_LogicalOrPredicate_v7() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "35");
        ComparisonPredicate thirdPredicate = new ComparisonPredicate("equals", "role", "Admin");
        ComparisonPredicate fourthPredicate = new ComparisonPredicate("matches_regex", "email", ".*@ghosted\\.com");
        LogicalPredicate orPredicate = new LogicalPredicate("or", firstPredicate, secondPredicate, thirdPredicate,
                fourthPredicate);

        Filter filter = new Filter(orPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }

    @Test
    @DisplayName("Test LogicalPredicate with single user context - MultiLevel Logical Predicate")
    public void testLogicalPredicateSingleUser_MultiLevelLogicalPredicate() {
        ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
        ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "35");
        LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

        ComparisonPredicate thirdPredicate = new ComparisonPredicate("equals", "role", "User");
        ComparisonPredicate fourthPredicate = new ComparisonPredicate("matches_regex", "email", ".*@ghosted\\.com");
        LogicalPredicate orPredicate = new LogicalPredicate("or", thirdPredicate, fourthPredicate);

        LogicalPredicate multiLevelPredicate = new LogicalPredicate("and", andPredicate, orPredicate);

        Filter filter = new Filter(multiLevelPredicate);

        Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
                "jimbob@ghosted.com");

        assertEquals(true, filter.matches(user), "Expected user to match the filter");
    }
}
