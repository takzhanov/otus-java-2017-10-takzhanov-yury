package io.github.takzhanov.umbrella.hw08;

import java.util.Arrays;
import java.util.Objects;

public class Person {
    private String name;
    private String surname;
    private Car[] cars;
    private int phone;
    private transient int age;

    private Person() {
    }

    public Person(String name, String surname, int phone, int age, Car[] cars) {
        this.name = name;
        this.surname = surname;
        this.cars = cars;
        this.phone = phone;
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: " + name + " " + surname + "\n");
        sb.append("Phone: " + phone + "\n");
        sb.append("Age: " + age + "\n");

        int i = 0;
        for (Car item : cars) {
            i++;
            sb.append("Car " + i + ": " + item + "\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return phone == person.phone &&
                Objects.equals(name, person.name) &&
                Objects.equals(surname, person.surname) &&
                Arrays.equals(cars, person.cars);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, surname, phone);
        result = 31 * result + Arrays.hashCode(cars);
        return result;
    }
}
