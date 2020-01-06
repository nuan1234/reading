package chap8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Debugging {

    public static void main(String[] args){
    	List<Point> pointList= Arrays.asList(new Point(12,2), null);
    	pointList.stream().map(p->p.getX()).forEach(System.out::println);
    }

    public static class Point{
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Point moveRightBy(int x){
            return new Point(this.x+x,this.y);
        }

        public final static Comparator<Point> compareByXAndThenY=
                comparing(Point::getX).thenComparing(Point::getY);

        public static List<Point> moveAllPointsRightBy(List<Point> points,int x){
            return points.stream()
                    .map(p->new Point(p.getX()+x,p.getY()))
                    .collect(toList());
        }

        @Override
        public boolean equals(Object obj) {
            if(this==obj) return true;
            if(obj==null) return false;
            if(obj instanceof Point){
                Point other=(Point) obj;
                if(other.getX()==this.getX() && other.getY()==this.getY()){
                    return true;
                }
            }
            return false;
        }
    }



}
