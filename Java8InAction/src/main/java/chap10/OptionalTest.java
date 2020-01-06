package chap10;

import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args){
        Optional<Car> optCar=Optional.empty();

        Car car=null;
        Optional<Car> optCar2=Optional.of(car);
        System.out.println(optCar2.get());

        Optional<Car> optCar3=Optional.ofNullable(car);
        System.out.println(optCar3.get());



    }


    public static Optional<Integer> stringToInt(String s){
        try{
            return Optional.of(Integer.parseInt(s));
        }catch (NumberFormatException e){
            return Optional.empty();
        }
    }

















}
