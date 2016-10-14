package co.runtime.lambdas.tutorial;

/**
 * Interface to specify the search criteria for a person
 *
 * @author Camilo Sarmiento
 * @since 2016-10-13
 */
interface CheckPerson {
    boolean test(Person p);
}