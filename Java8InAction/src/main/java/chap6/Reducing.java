package chap6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static chap6.Dish.menu;
import static java.util.stream.Collectors.reducing;

public class Reducing {

    public static void main(String[] args){
    	Stream<Integer> stream= Arrays.asList(1,2,3,4,5,6).stream();

    	List<Integer> numbers=stream.reduce(
                new ArrayList<Integer>(),
                (List<Integer> l,Integer e)->{
                    l.add(e);
                    return l;
                },
                (List<Integer> l1,List<Integer> l2)->{
                    l1.addAll(l2);
                    return l1;
                }
        );
        System.out.println(numbers);
    }


    private static int calculateTotalCalories(){
        return menu.stream().
                collect(reducing(0,Dish::getCalories,(Integer i,Integer j)->i+j));
    }

    private static int calculateTotalCaloriesWithMethodReference(){
        return menu.stream().collect(reducing(0,Dish::getCalories,Integer::sum));
    }

    private static int calculateTotalCaloriesWithoutCollectors(){
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    private static int calculateTotalCaloriesUsingSum(){
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }









}
