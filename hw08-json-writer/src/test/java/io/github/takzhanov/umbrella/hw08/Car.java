package io.github.takzhanov.umbrella.hw08;

import java.util.Objects;

public class Car {
    private String manufacturer;
    private String model;
    private Double capacity;
    private boolean accident;

    private Car() {
    }

    public Car(String manufacturer, String model, Double capacity, boolean accident) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.capacity = capacity;
        this.accident = accident;
    }

    @Override
    public String toString() {
        return "Car{" +
                "manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", accident=" + accident +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return accident == car.accident &&
                Objects.equals(manufacturer, car.manufacturer) &&
                Objects.equals(model, car.model) &&
                Objects.equals(capacity, car.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturer, model, capacity, accident);
    }
}

