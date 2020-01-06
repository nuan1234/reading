package chap3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateTest {
    
    public static void main(String[] args){
        List<String> listOfString= Arrays.asList("Java","","8","","in","","action");

        Predicate<String> nonEmptyStringPredicate=(String s)->!s.isEmpty();

        List<String> nonEmpty=filter(listOfString, nonEmptyStringPredicate);
        System.out.println(nonEmpty);
    }
    
    
    public static <T> List<T> filter(List<T> list,Predicate<T> p){
        List<T> results=new ArrayList<>();
        for(T s:list){
            if(p.test(s)){
                results.add(s);
            }
        }
        return results;
    }
}
