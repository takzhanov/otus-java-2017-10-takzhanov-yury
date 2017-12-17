package io.github.takzhanov.umbrella.hw08;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class JsonWriterTest {
    private MySerializator serializator = new MySerializator();

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
        System.out.println(gson.toJson(""));
        System.out.println(gson.toJson(3));
        System.out.println(gson.toJson(3f));
        System.out.println(gson.toJson(3.5));
        System.out.println(gson.toJson(new A[]{new B(), new A(), null}));
        System.out.println(gson.toJson(new Integer[]{1, 2, 3}));
        System.out.println(gson.toJson(new Long[]{1L, 2L, 3L}));
        System.out.println(serializator.toJson(false));

        System.out.println(gson.fromJson("null", A.class));
        System.out.println(gson.fromJson("\"\"", String.class));
        System.out.println(gson.fromJson("", String.class));
        System.out.println(gson.fromJson("   ", String.class));
        System.out.println(gson.fromJson("[1,2,3]", Integer[].class));
        System.out.println(gson.fromJson("[1,2,3]", Long[].class));
    }

    @Test
    public void demoMySerializator() {
        Car audi = new Car("Audi", "A4", 1.8, false);
        Car skoda = new Car("Škoda", "Octavia", 2.0, true);
        Car[] cars = {audi, skoda};
        Person johnDoe = new Person("John", "Doe", 245987453, 35, cars);

        System.out.println(johnDoe);
        String jsonString = serializator.toJson(johnDoe);
        System.out.println(jsonString);
        Person restoredJohnDoe = serializator.fromJson(jsonString, Person.class);
        System.out.println(restoredJohnDoe);
        Assert.assertEquals(johnDoe, restoredJohnDoe);

        System.out.println(serializator.toJson(null));
        System.out.println(serializator.toJson(""));
        System.out.println(serializator.toJson(3));
        System.out.println(serializator.toJson(3f));
        System.out.println(serializator.toJson(3.5));
        System.out.println(serializator.toJson(new A[]{new B(), new A(), null}));
        System.out.println(serializator.toJson(new Integer[]{1, 2, 3}));
        System.out.println(serializator.toJson(new Long[]{1L, 2L, 3L}));
        System.out.println(serializator.toJson(false));

        System.out.println(serializator.fromJson("null", A.class));
        System.out.println(serializator.fromJson("\"\"", String.class));
        System.out.println(serializator.fromJson("", String.class));
        System.out.println(serializator.fromJson("   ", String.class));
        System.out.println(serializator.fromJson("[1,2,3]", Integer[].class));
        System.out.println(serializator.fromJson("[1,2,3]", Long[].class));
    }

    @Test
    public void testFromJsonNull() {
        Assert.assertNull(serializator.fromJson("null", Object.class));
        Assert.assertNull(serializator.fromJson("", Object.class));
        Assert.assertNull(serializator.fromJson("   ", Object.class));
    }

    @Test
    public void testFromJsonString() {
        Assert.assertEquals("", serializator.fromJson("\"\"", String.class));
        Assert.assertEquals("abc", serializator.fromJson("\"abc\"", String.class));
    }

    @Test
    public void testFromJsonBoolean() {
        Assert.assertTrue(serializator.fromJson("true", Boolean.class));
        Assert.assertFalse(serializator.fromJson("  false  ", Boolean.class));
    }

    @Test
    public void testFromJsonLong() {
        Assert.assertEquals(Byte.valueOf((byte) 122), serializator.fromJson("122", Byte.class));
        Assert.assertEquals(Short.valueOf((short) 543), serializator.fromJson("543", Short.class));
        Assert.assertEquals((Integer) 543, serializator.fromJson("543", Integer.class));
        Assert.assertEquals((Long) 543L, serializator.fromJson("543", Long.class));
    }

    @Test
    public void testFromJsonDouble() {
        Assert.assertEquals(543d, serializator.fromJson("543.0", Double.class), 1e-9);
        Assert.assertEquals(543f, serializator.fromJson("543.0", Float.class), 1e-9);
        Assert.assertEquals(543f, serializator.fromJson("543.0", float.class), 1e-9);
    }

    @Test
    public void testFromJsonArray() {
        Assert.assertArrayEquals(new Long[]{1L, 2L, 3L}, serializator.fromJson("[1,2,3]", Long[].class));
        Assert.assertArrayEquals(new Integer[]{1, 2, 3}, serializator.fromJson("[1,2,3]", Integer[].class));
        Assert.assertArrayEquals(new int[]{1, 2, 3}, serializator.fromJson("[1,2,3]", int[].class));
        Assert.assertArrayEquals(new String[]{"1", "2", null}, serializator.fromJson("[\"1\",\"2\",null]", String[].class));
    }

    @Test
    public void testToJson() {
        Assert.assertEquals("true", serializator.toJson(true));
        Assert.assertEquals("1", serializator.toJson(1L));
        Assert.assertEquals("1.0", serializator.toJson(1f));
        Assert.assertEquals("null", serializator.toJson(null));
        Assert.assertEquals("\"\"", serializator.toJson(""));
        Assert.assertEquals("\"   \"", serializator.toJson("   "));
        Assert.assertEquals("[1,2,3]", serializator.toJson(new int[]{1, 2, 3}));
        Assert.assertEquals("[\"1\",\"2\",null]", serializator.toJson(new String[]{"1", "2", null}));
    }
}
