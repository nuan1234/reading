package chap15;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Example {
    
    public static void main(String[] args){

        IntStream.rangeClosed(2,6)
                .forEach(n->System.out.println("Hello "+n+" bottles of beer."));
    
        Set<Integer> numbers=new HashSet<>();
        Set<Integer> newNumbers= Collections.unmodifiableSet(numbers);


    }
}
