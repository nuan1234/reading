package chap4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Iteration {

    public static void main(String[] args){
    	//1 for-each，背后使用Iterator
        List<String> names=new ArrayList<>();
        for(Dish d:Dish.menu){
            names.add(d.getName());
        }

        //2. Iterator
        Iterator<Dish> iterator=Dish.menu.iterator();
        while(iterator.hasNext()){
            Dish d=iterator.next();
            names.add(d.getName());
        }

        //3. 内部迭代
        List<String> names2=Dish.menu.stream()
                .map(Dish::getName)
                .collect(toList());
        
        
        //4. 中间操作
        List<String> names3=Dish.menu.stream()
                .filter(d->{
                    System.out.println("filtering "+d.getName());
                    return d.getCalories()>300;
                })
                .map(d->{
                    System.out.println("mapping "+d.getName());
                    return d.getName();
                })
                .limit(3)
                .collect(toList());
        System.out.println(names3);
    }
}
