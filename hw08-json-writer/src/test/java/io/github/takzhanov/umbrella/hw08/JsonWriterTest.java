package io.github.takzhanov.umbrella.hw08;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class JsonWriterTest {
    @Test
    public void demoGson() {
        Gson gson = new Gson();
        Car audi = new Car("Audi", "A4", 1.8, false);
        Car skoda = new Car("Škoda", "Octavia", 2.0, true);
        Car[] cars = {audi, skoda};
        Person johnDoe = new Person("John", "Doe", 245987453, 35, cars);

        System.out.println(johnDoe);
        String jsonString = gson.toJson(johnDoe);
        System.out.println(jsonString);
        Person restoredJohnDoe = gson.fromJson(jsonString, Person.class);
        System.out.println(restoredJohnDoe);
        Assert.assertEquals(johnDoe, restoredJohnDoe);

        System.out.println(gson.toJson(null));
        System.out.println(gson.toJson(new A[]{new B(), new A()}));
        System.out.println(gson.fromJson("null", A.class));
    }

    @Test
    public void demoMySerializator() {
        MySerializator serializator = new MySerializator();
        Car audi = new Car("Audi", "A4", 1.8, false);
        Car skoda = new Car("Škoda", "Octavia", 2.0, true);
        Car[] cars = {audi, null};
        Person johnDoe = new Person("John", "Doe", 245987453, 35, cars);

        System.out.println(johnDoe);
        String jsonString = serializator.toJson(johnDoe);
        System.out.println(jsonString);
        Person restoredJohnDoe = serializator.fromJson(jsonString, Person.class);
        System.out.println(restoredJohnDoe);
        Assert.assertEquals(johnDoe, restoredJohnDoe);
    }
}
