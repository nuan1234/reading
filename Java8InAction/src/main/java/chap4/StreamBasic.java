package chap4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamBasic {
    public static void main(String[] args){
    	//java7
        getLowCaloricDishesNamesInJava7(Dish.menu).forEach(System.out::println);

        //java8
        getLowCaloricDishesNamesInJava8(Dish.menu).forEach(System.out::println);
    }

    private static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes) {
        // return dishes.stream()
        return dishes.parallelStream()
                .filter(d->d.getCalories()<400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
    }

    private static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes) {
        List<Dish> lowCaloricDishes=new ArrayList<>();
        for(Dish d:dishes){
            if(d.getCalories()<400){
                lowCaloricDishes.add(d);
            }
        }
        List<String> lowCaloricDishesName=new ArrayList<>();
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(),o2.getCalories());
            }
        });
        for(Dish d:lowCaloricDishes){
            lowCaloricDishesName.add(d.getName());
        }
        return lowCaloricDishesName;
    }
}
