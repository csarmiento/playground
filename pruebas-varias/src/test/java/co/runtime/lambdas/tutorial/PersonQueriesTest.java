package co.runtime.lambdas.tutorial;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static co.runtime.lambdas.tutorial.PersonQueries.printPersons;
import static co.runtime.lambdas.tutorial.PersonQueries.processPersons;
import static co.runtime.lambdas.tutorial.PersonQueries.processPersonsWithFunction;

/**
 * This class shows the evolution of the <tt>printPersons</tt> utility from no-lambdas to lambdas using tests
 *
 * @author Camilo Sarmiento
 * @see <a href="http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html">Java 8 Lambda Expressions
 * Tutorial</a>
 * @since 2016-10-13
 */
public class PersonQueriesTest {
    private List<Person> roster;

    @Before
    public void setUp() {
        roster = new ArrayList<>();
        roster.add(new Person("Camilo Sarmiento", LocalDate.of(1979, 11, 1), Person.Sex.MALE, "camikiller@gmail.com"));
        roster.add(new Person("Maria Alex Suarez", LocalDate.of(1977, 1, 28), Person.Sex.FEMALE, "mariaalex.suarez@gmail.com"));
        roster.add(new Person("Young Person", LocalDate.of(1996, 5, 5), Person.Sex.MALE, "young.person@gmail.com"));
    }

    /**
     * To use this class, you create a new instance of it and invoke the printPersons method:
     */
    @Test
    public void specifySearchCriteriaCodeInALocalClass() throws Exception {
        printPersons(roster, new CheckPersonEligibleForSelectiveService());
    }

    /**
     * This approach reduces the amount of code required because you don't have to create a new class for each search
     * that you want to perform. However, the syntax of anonymous classes is bulky considering that the CheckPerson
     * interface contains only one method.
     */
    @Test
    public void specifySearchCriteriaCodeInAnAnonymousClass() {
        printPersons(
            roster,
            new CheckPerson() {
                public boolean test(Person p) {
                    return p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25;
                }
            }
        );
    }

    /**
     * The CheckPerson interface is a functional interface. A functional interface is any interface that contains only
     * one abstract method. (A functional interface may contain one or more default methods or static methods.) Because
     * a functional interface contains only one abstract method, you can omit the name of that method when you implement
     * it. To do this, instead of using an anonymous class expression, you use a lambda expression, which is highlighted
     * in the following method invocation.
     *
     * @see <a href="http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#syntax">Syntax of Lambda
     * Expressions</a>
     */
    @Test
    public void specifySearchCriteriaCodeWithALambdaExpression() {
        printPersons(
            roster,
            (Person p) -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25
        );
    }

    /**
     * The following method invocation is the same as when you invoked printPersons in Approach 3:
     * Specify Search Criteria Code in a Local Class, to obtain members who are eligible for Selective Service.
     */
    @Test
    public void obtainMembersWhoAreEligibleForSelectiveService() {
        processPersons(
            roster,
            p -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.printPerson()
        );
    }

    @Test
    public void applyCustomFunctionToMembersWhoAreElegibleForSelectiveService() {
        processPersonsWithFunction(
            roster,
            p -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.getEmailAddress(),
            email -> System.out.println(email)
        );

    }
}