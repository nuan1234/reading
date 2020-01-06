package chap2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {

    public static void main(String[] args){
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        List<Apple> greenApples=filterGreenApples(inventory);
        List<Apple> redApples=filterApplesByColor(inventory,"red");
        List<Apple> greenApples2=filterApples(inventory,"green",0,true);
        List<Apple> heavyApples2=filterApples(inventory,"",150,false);
        List<Apple> redAndHeavyApples=filterApples(inventory,new AppleRedAndHeavyPredicate());

        //5 匿名类
        List<Apple> redApples2=filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println(redApples2);
        
        //6 Lambda
        List<Apple> result=filterApples(inventory,(Apple apple)->"red".equals(apple.getColor()));
        
        //7.泛型化
        List<Apple> redApples3=filter(inventory,(Apple apple)->"red".equals(apple.getColor()));
        List<Integer> numbers=new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        List<Integer> evenNumbers=filter(numbers,(Integer i)->i%2==0);
        System.out.println(evenNumbers);
    }

    //1
    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result=new ArrayList<>();
        for(Apple apple:inventory){
            if("green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }
    //2
    public static List<Apple> filterApplesByColor(List<Apple> inventory,
                                                  String color){
        List<Apple> result=new ArrayList<>();
        for(Apple apple:inventory){
            if(apple.getColor().equals(color)){
                result.add(apple);
            }
        }
        return result;
    }
    public static List<Apple> filterApplesByWeight(List<Apple> inventory,
                                                  int weight){
        List<Apple> result=new ArrayList<>();
        for(Apple apple:inventory){
            if(apple.getWeight()>weight){
                result.add(apple);
            }
        }
        return result;
    }

    //3
    public static List<Apple> filterApples(List<Apple> inventory,String color,
                                            int weight,boolean flag){
        List<Apple> result=new ArrayList<>();
        for(Apple apple:inventory){
            if((flag && apple.getColor().equals(color)) ||
                    (!flag && apple.getWeight()>weight)){
                result.add(apple);
            }
        }
        return result;
    }
    //4
    public static List<Apple> filterApples(List<Apple> inventory,
                                           ApplePredicate p){
        List<Apple> result=new ArrayList<>();
        for(Apple apple:inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
    //5 匿名类
    
    //6 Lambda
    
    //7.List类型抽象化
    public interface Predicate<T>{
        boolean test(T t);
    }
    public static <T> List<T> filter(List<T> list,Predicate<T> p){
        List<T> result=new ArrayList<>();
        for(T e:list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }







    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
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

        @Override
        public String toString() {
            return "Apple{" +
                    "weight=" + weight +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    public interface ApplePredicate{
        boolean test(Apple apple);
    }
    public class AppleHeavyWeightPredicate implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight()>150;
        }
    }
    public class AppleGreenColorPredicate implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }
    public static class AppleRedAndHeavyPredicate implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor())
                    && apple.getWeight()>150;
        }
    }

}
