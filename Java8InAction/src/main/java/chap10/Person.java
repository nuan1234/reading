package chap10;

import java.util.Optional;

public class Person {
    private int age;

    /*private Car car;

    public Car getCar() {
        return car;
    }*/

    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }

    public int getAge() {
        return age;
    }
}
