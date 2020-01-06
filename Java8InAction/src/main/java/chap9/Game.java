package chap9;

import java.util.Arrays;
import java.util.List;

public class Game {

    public static void main(String[] args){
    	List<Resizable> resizableShapes=
                Arrays.asList(new Square(),new Triangle(),new Ellipse());
    	Utils.paint(resizableShapes);
    }

    public static class Utils{
        public static void paint(List<Resizable> l){
            l.forEach(
                    r->{
                        r.setAbsoluteSize(42,42);
                        r.draw();
                    }
            );
        }
    }

}
