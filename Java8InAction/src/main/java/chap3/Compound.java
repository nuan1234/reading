package chap3;

import java.util.function.Function;

public class Compound {
    
    public static void main(String[] args){
        Function<Integer,Integer> f=x->x+1;
        Function<Integer,Integer> g=x->x*2;
        Function<Integer,Integer> h=f.andThen(g);
        
        int result=h.apply(1);
        System.out.println(result);
        
        Function<Integer,Integer> i=f.compose(g);
        int result2=i.apply(1);
        System.out.println(result2);


        Function<String,String> addHeader=Letter::addHeader;
        Function<String,String> transformationPipeline=
                addHeader.andThen(Letter::checkSpelling)
                         .andThen(Letter::addFooter);
    }

    static class Letter{
        public static String addHeader(String text){
            return "From Raoul, Mario and Alan: "+text;
        }
        public static String addFooter(String text){
            return text+" Kind regards";
        }
        public static String checkSpelling(String text){
            return text.replaceAll("labdda","lambda");
        }
    }
}
