package co.runtime.lambdas.tutorial;

/**
 * The following class implements the CheckPerson interface by specifying an implementation for the method test.
 * This method filters members that are eligible for Selective Service in the United States: it returns a true value
 * if its Person parameter is male and between the ages of 18 and 25.
 *
 * @author Camilo Sarmiento
 * @since 2016-10-13
 */
class CheckPersonEligibleForSelectiveService implements CheckPerson {
    public boolean test(Person p) {
        return p.gender == Person.Sex.MALE &&
            p.getAge() >= 18 &&
            p.getAge() <= 25;
    }
}