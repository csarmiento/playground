package co.runtime.lambdas.tutorial;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

/**
 * @author Camilo Sarmiento
 * @since 2016-10-13
 */
public class Person {

    public Person(String name, LocalDate birthday, Sex gender, String emailAddress) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.emailAddress = emailAddress;
    }

    public enum Sex {
        MALE, FEMALE
    }

    String    name;
    LocalDate birthday;
    Sex       gender;
    String    emailAddress;

    public int getAge() {
        return (int) YEARS.between(birthday, LocalDate.now());
    }

    public Sex getGender() {
        return gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void printPerson() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            ", birthday=" + birthday +
            ", gender=" + gender +
            ", emailAddress='" + emailAddress + '\'' +
            '}';
    }
}