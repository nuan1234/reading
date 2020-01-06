package chap3;

import chap1.FilteringApples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.function.Predicate;

public class Sorting {

    public static void main(String[] args){
    	List<Apple> inventory=new ArrayList<>();
    	inventory.addAll(Arrays.asList(
    	        new Apple(80,"green"),
                new Apple(155,"green"),
                new Apple(120,"red")));
    	//1 传递代码
    	inventory.sort(new AppleComparator());
    	System.out.println(inventory);
    	//2 匿名类
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(inventory);
        //3. Lambda表达式
        inventory.sort((Apple a1, Apple a2)->a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);
        
        // Comparator<Apple> c=Comparator.comparing((Apple a)->a.getWeight());
        inventory.sort(comparing((a)->a.getWeight()));
    	System.out.println(inventory);
    	
    	//4.
        inventory.sort(comparing(Apple::getWeight));
        System.out.println(inventory);
        
        //逆序
        inventory.sort(comparing(Apple::getWeight).reversed());
        System.out.println(inventory);

        //比较器链
        inventory.sort(comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getColor));

        /*Predicate<Apple> notRedApple= redApple.negate();
        Predicate<Apple> redAndHeavyApple=redApple.and(a->a.getWeight()>150);
        Predicate<Apple> redAndHeavyAppleOrGreen=
                redApple.and(a->a.getWeight()>150)
                        .or(a->"green".equals(a.getColor()));*/

    }

    //1
    static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }


    public static class Apple {
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }


}
