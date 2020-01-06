package chap10;

import org.junit.Test;
import util.OptionalUtility;

import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;

public class ReadPositiveIntParam {

    @Test
    public void testMap(){
        Properties properties=new Properties();
        properties.setProperty("a","5");
        properties.setProperty("b","true");
        properties.setProperty("c","-3");

        assertEquals(5,readDurationImperative(properties,"a"));
    }

    public static int readDurationImperative(Properties properties,String name){
        String value=properties.getProperty(name);
        if(value!=null){
            try{
                int i=Integer.parseInt(value);
                if(i>0){
                    return i;
                }
            }catch (NumberFormatException e){}
        }
        return 0;
    }

    public static int readDurationWithOptional(Properties properties,String name){
        return ofNullable(properties.getProperty(name))
                // .flatMap(ReadPositiveIntParam::s2i)
                .flatMap(OptionalUtility::stringToInt)
                .filter(i->i>0)
                .orElse(0);
    }

    private static Optional<Integer> s2i(String s) {
        try{
            return of(Integer.parseInt(s));
        }catch (NumberFormatException e){
            return empty();
        }
    }


}
