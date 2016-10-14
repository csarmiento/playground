package co.runtime.lambdas.tutorial;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class shows the evolution of the <tt>printPersons</tt> utility from no-lambdas to lambdas
 *
 * @author Camilo Sarmiento
 * @see <a href="http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html">Java 8 Lambda Expressions
 * Tutorial</a>
 * @since 2016-10-13
 */
public class PersonQueries {

    /**
     * One simplistic approach is to create several methods; each method searches for members that match one
     * characteristic, such as gender or age. The following method prints members that are older than a specified age:
     *
     * @param roster
     * @param age
     */
    public static void printPersonsOlderThan(List<Person> roster, int age) {
        for (Person p : roster) {
            if (p.getAge() >= age) {
                p.printPerson();
            }
        }
    }

    /**
     * The following method is more generic than printPersonsOlderThan; it prints members within a specified range of
     * ages:
     *
     * @param roster
     * @param low
     * @param high
     */
    public static void printPersonsWithinAgeRange(List<Person> roster, int low, int high) {
        for (Person p : roster) {
            if (low <= p.getAge() && p.getAge() < high) {
                p.printPerson();
            }
        }
    }

    /**
     * The following method prints members that match search criteria that you specify
     *
     * @param roster
     * @param tester
     */
    public static void printPersons(
        List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    /**
     * This parameterized type contains a method that has the same return type and parameters as <tt>CheckPerson.boolean
     * test(Person p)</tt>. Consequently, you can use <tt>Predicate&lt;T&gt;</tt> in place of <tt>CheckPerson</tt> as
     * the following method demonstrates:
     *
     * @param roster
     * @param tester
     */
    public static void printPersonsWithPredicate(List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    /**
     * Instead of invoking the method printPerson, you can specify a different action to perform on those Person
     * instances that satisfy the criteria specified by tester. You can specify this action with a lambda expression.
     * Suppose you want a lambda expression similar to printPerson, one that takes one argument (an object of type
     * Person) and returns void. Remember, to use a lambda expression, you need to implement a functional interface. In
     * this case, you need a functional interface that contains an abstract method that can take one argument of type
     * Person and returns void. The <tt>Consumer&lt;T&gt;</tt> interface contains the method void accept(T t), which has
     * these characteristics. The following method replaces the invocation p.printPerson() with an instance of
     * <tt>Consumer&lt;Person&gt;</tt> that invokes the method accept.
     *
     * @param roster
     * @param tester
     * @param block
     */
    public static void processPersons(List<Person> roster, Predicate<Person> tester, Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

    /**
     * What if you want to do more with your members' profiles than printing them out. Suppose that you want to validate
     * the members' profiles or retrieve their contact information? In this case, you need a functional interface that
     * contains an abstract method that returns a value. The Function<T,R> interface contains the method R apply(T t).
     * <br/>
     * The following method retrieves the data specified by the parameter mapper, and then performs an action on it
     * specified by the parameter block
     *
     * @param roster
     * @param tester
     * @param mapper
     * @param block
     */
    public static void processPersonsWithFunction(
        List<Person> roster,
        Predicate<Person> tester,
        Function<Person, String> mapper,
        Consumer<String> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }
}
