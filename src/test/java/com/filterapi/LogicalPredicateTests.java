package com.filterapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.filterapi.api.Filter;
import com.filterapi.models.BooleanPredicate;
import com.filterapi.models.ComparisonPredicate;
import com.filterapi.models.LogicalPredicate;

/**
 * Tests for the LogicalPredicate class. As there is a lot more to test here,
 * and not something that is nearly as easily put into a ParameterizedTest, this
 * test doc has a LOT more tests in it. I am not going to go through and
 * documnet ALL of them. I tried to keep variables and things of that sort named
 * and coding clear enough that it should be relatively easy to read. I have
 * tested probably 90% of the use cases I could come up with. I could have kept
 * writing for a few more hours and covered 99%, but thought this would at least
 * get the point across of the basics we were trying to cover.
 */
public class LogicalPredicateTests {
  @Test
  @DisplayName("Test LogicalPredicate with single user context - Logical Not Predicate V1 - User Exists")
  public void testLogicalPredicateSingleUser_LogicalNotPredicate_v1() {
    BooleanPredicate truePredicate = new BooleanPredicate("true");
    LogicalPredicate notTruePredicate = new LogicalPredicate("not", truePredicate);

    Filter filter = new Filter(notTruePredicate);

    Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
        "jimbob@ghosted.com");

    assertEquals(false, filter.matches(user), "Expected user to not match the filter");
  }

  @Test
  @DisplayName("Test LogicalPredicate with single user context - Logical NotPredicate V2 - No User Context")
  public void testLogicalPredicateSingleUser_LogicalNotPredicate_v2() {
    BooleanPredicate truePredicate = new BooleanPredicate("true");
    LogicalPredicate notTruePredicate = new LogicalPredicate("not", truePredicate);

    Filter filter = new Filter(notTruePredicate);

    assertEquals(true, filter.matches(null), "Expected user to not match the filter");
  }

  @Test
  @DisplayName("Test LogicalPredicate with single user context - Logical And Predicate V1 - First Name and Last Name Present")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical And Predicate V2 - First Name Present, nonExistentKey Not Present")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical And Predicate V3 - Greater and Less Than Age")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical And Predicate V4 - Greater Than but Not Less Than Age")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical And Predicate V5 - Combining most of the Comparison Predicate Types")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical Or Predicate V1 - First Name or Last Name Present")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical Or Predicate V2 - First Name or NonExistentKey Present")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical Or Predicate V3 - Neither key present in user context")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical Or Predicate V4 - Greater Than or Less Than Age")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical Or Predicate V5 - Greater Than True, Less Than False")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical Or Predicate V6 - Both Greater Than and Less Than False")
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
  @DisplayName("Test LogicalPredicate with single user context - Logical Or Predicate V7 - Combining most of the Comparison Predicate Types")
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

  /**
   * Tests the LogicalPredicate with a multi-level logical structure. This was the
   * big test, where we see that yes, you can create complex logical expressions,
   * and they still function. As I am typing this, I think I might add one more
   * that adds a couple more layers, as this only shows one layer, and I want to
   * verify I can make more complex logical expressions with this.
   */
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

  @Test
  @DisplayName("Test LogicalPreicate with single user context - Multi-MultiLevel Logical Predicate - Complexity Test - True")
  public void testLogicalPredicateSingleUser_MultiMultiLevelLogicalPredicate_v1() {
    ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
    ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "35");
    LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

    ComparisonPredicate thirdPredicate = new ComparisonPredicate("equals", "role", "User");
    ComparisonPredicate fourthPredicate = new ComparisonPredicate("matches_regex", "email", ".*@ghosted\\.com");
    LogicalPredicate orPredicate = new LogicalPredicate("or", thirdPredicate, fourthPredicate);

    LogicalPredicate multiLevelAnd = new LogicalPredicate("and", andPredicate, orPredicate);

    ComparisonPredicate fifthPredicate = new ComparisonPredicate("present", "firstName");
    ComparisonPredicate sixthPredicate = new ComparisonPredicate("present", "lastName");
    LogicalPredicate multiLevelOr = new LogicalPredicate("or", fifthPredicate, sixthPredicate);

    LogicalPredicate multiMultiLevelAnd = new LogicalPredicate("and", multiLevelAnd, multiLevelOr);

    ComparisonPredicate seventhPredicate = new ComparisonPredicate("equals", "lastName", "Litcherholt");
    LogicalPredicate finalPredicate = new LogicalPredicate("and", multiMultiLevelAnd, seventhPredicate);

    Filter filter = new Filter(finalPredicate);

    Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
        "jimbob@ghosted.com");

    assertEquals(true, filter.matches(user), "Expected user to match the filter");
  }

  @Test
  @DisplayName("Test LogicalPreicate with single user context - Multi-MultiLevel Logical Predicate - Complexity Test - FALSE")
  public void testLogicalPredicateSingleUser_MultiMultiLevelLogicalPredicate_v2() {
    ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
    ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "28");
    LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

    ComparisonPredicate thirdPredicate = new ComparisonPredicate("equals", "role", "User");
    ComparisonPredicate fourthPredicate = new ComparisonPredicate("matches_regex", "email", ".*@ghosted\\.com");
    LogicalPredicate orPredicate = new LogicalPredicate("or", thirdPredicate, fourthPredicate);

    LogicalPredicate multiLevelAnd = new LogicalPredicate("and", andPredicate, orPredicate);

    ComparisonPredicate fifthPredicate = new ComparisonPredicate("present", "firstName");
    ComparisonPredicate sixthPredicate = new ComparisonPredicate("present", "lastName");
    LogicalPredicate multiLevelOr = new LogicalPredicate("or", fifthPredicate, sixthPredicate);

    LogicalPredicate multiMultiLevelAnd = new LogicalPredicate("and", multiLevelAnd, multiLevelOr);

    ComparisonPredicate seventhPredicate = new ComparisonPredicate("equals", "lastName", "Litcherholt");
    LogicalPredicate finalPredicate = new LogicalPredicate("and", multiMultiLevelAnd, seventhPredicate);

    Filter filter = new Filter(finalPredicate);

    Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
        "jimbob@ghosted.com");

    assertEquals(false, filter.matches(user), "Expected user to match the filter");
  }

  @Test
  @DisplayName("Test LogicalPreicate with single user context - ParseFromString Test")
  public void testLogicalPredicateSingleUser_ParseFromString() {
    ComparisonPredicate firstPredicate = new ComparisonPredicate("greater_than", "age", "25");
    ComparisonPredicate secondPredicate = new ComparisonPredicate("less_than", "age", "28");
    LogicalPredicate andPredicate = new LogicalPredicate("and", firstPredicate, secondPredicate);

    ComparisonPredicate thirdPredicate = new ComparisonPredicate("equals", "role", "User");
    ComparisonPredicate fourthPredicate = new ComparisonPredicate("matches_regex", "email", ".*@ghosted\\.com");
    LogicalPredicate orPredicate = new LogicalPredicate("or", thirdPredicate, fourthPredicate);

    LogicalPredicate multiLevelAnd = new LogicalPredicate("and", andPredicate, orPredicate);

    ComparisonPredicate fifthPredicate = new ComparisonPredicate("present", "firstName");
    ComparisonPredicate sixthPredicate = new ComparisonPredicate("present", "lastName");
    LogicalPredicate multiLevelOr = new LogicalPredicate("or", fifthPredicate, sixthPredicate);

    LogicalPredicate multiMultiLevelAnd = new LogicalPredicate("and", multiLevelAnd, multiLevelOr);

    ComparisonPredicate seventhPredicate = new ComparisonPredicate("equals", "lastName", "Litcherholt");
    LogicalPredicate finalPredicate = new LogicalPredicate("and", multiMultiLevelAnd, seventhPredicate);

    Filter filter = new Filter(finalPredicate);

    String filterString = filter.toString();

    Filter parsedFilter = Filter.parseFromString(filterString);

    Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
        "jimbob@ghosted.com");

    assertEquals(false, parsedFilter.matches(user), "Expected user to match the filter");

    assertEquals(filterString, parsedFilter.toString(),
        "Expected the original filter string and the parsed filter string to be the same");
  }
}
