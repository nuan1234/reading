package chap4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamVsCollection {

    public static void main(String[] args){
    	List<String> title= Arrays.asList("Java8","In","Action");
        Stream<String> s=title.stream();
        s.forEach(System.out::println);
        //流只能被消费一次
        // s.forEach(System.out::println);
    }
}
