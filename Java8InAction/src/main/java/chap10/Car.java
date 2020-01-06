package chap10;

import java.util.Optional;

public class Car {

    /*private Insurance insurance;

    public Insurance getInsurance() {
        return insurance;
    }*/

    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
