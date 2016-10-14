package co.runtime.lambdas.tutorial;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Camilo Sarmiento
 * @since 2016-10-13
 */
public class GenericProcessorTest {
    private List<Person> roster;

    @Before
    public void setUp() {
        roster = new ArrayList<>();
        roster.add(new Person("Camilo Sarmiento", LocalDate.of(1979, 11, 1), Person.Sex.MALE, "camikiller@gmail.com"));
        roster.add(new Person("Maria Alex Suarez", LocalDate.of(1977, 1, 28), Person.Sex.FEMALE, "mariaalex.suarez@gmail.com"));
        roster.add(new Person("Young Person", LocalDate.of(1996, 5, 5), Person.Sex.MALE, "young.person@gmail.com"));
    }

    @Test
    public void printEmailAddressOfMembersWhoAreEligibleForSelectiveService() throws Exception {
        GenericProcessor.processElements(
            roster,
            p -> p.getAge() > 25,
            p -> p.getEmailAddress(),
            email -> System.out.println(email)
        );
    }

    @Test
    public void printEmailAddressOfMembersWhoAreEligibleForSelectiveServiceWithLambdas() throws Exception {
        GenericProcessor.processElementsWithLambdaExpressions(
            roster,
            p -> p.getAge() > 25,
            p -> p.getEmailAddress(),
            email -> System.out.println(email)
        );
    }

}