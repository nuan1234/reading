package util;

import java.util.Optional;

public final class OptionalUtility {

    /**
     * 使用Optional封装的String转int的工具
     * @param s
     * @return
     */
    public static Optional<Integer> stringToInt(String s){
        try{
            return Optional.of(Integer.parseInt(s));
        }catch (NumberFormatException e){
            return Optional.empty();
        }
    }
}
