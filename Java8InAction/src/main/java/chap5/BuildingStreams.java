package chap5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreams {

    public static void main(String[] args){
        //值
        Stream<String> stream=Stream.of("Java 8 ","Lambda ","In ","Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        Stream<String> emptyStream=Stream.empty();

        //数组
        int[] numbers={2,3,5,7,11,13};
        int sum= Arrays.stream(numbers).sum();
        System.out.println(sum);

        //函数-迭代
        Stream.iterate(0,n->n+2)
                .limit(10)
                .forEach(System.out::println);
        Stream.iterate(new int[]{0,1},t->new int[]{t[1],t[0]+t[1]})
                .limit(20)
                .forEach(t->System.out.println("("+t[0]+","+t[1]+")"));
        //函数-生成
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
        IntSupplier fib=new IntSupplier() {
            private int previous=0;
            private int current=1;
            @Override
            public int getAsInt() {
                int oldPrevious=this.previous;
                int nextValue=this.previous+this.current;
                this.previous=this.current;
                this.current=nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);

        //文件
        long uniqueWords=0;

        try(Stream<String> lines = Files.lines(Paths.get("E:\\codes\\IJ\\Java8InAction\\src\\main\\resources\\lambdasinaction\\chap3\\data.txt"), Charset.defaultCharset())){
            uniqueWords=lines.flatMap(line->Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("There are " + uniqueWords + " unique words in data.txt");

    }
}
