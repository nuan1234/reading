package chap8;

import java.util.ArrayList;
import java.util.List;

public class ObserverMain {

    public static void main(String[] args){
    	Feed feed=new Feed();
    	feed.registerObserver(new NYTimes());
    	feed.registerObserver(new Guardian());
    	feed.registerObserver(new LeMonde());
        feed.notifyObserver("The queen said her favourite book is Java 8 in Action!");

        Feed feedLambda=new Feed();
        feedLambda.registerObserver(
                (String tweet)->{
                    if(tweet!=null && tweet.contains("money")){
                        System.out.println("Breaking news in NY!"+tweet);
                    }
                }
        );
        feedLambda.registerObserver(
                (String tweet)->{
                    if(tweet!=null && tweet.contains("queen")){
                        System.out.println("Yet another news in London..."+tweet);
                    }
                }
        );

        feedLambda.notifyObserver("Money money money, give me money!");
    }

    interface Observer{
        void inform(String tweet);
    }

    interface Subject{
        void registerObserver(Observer o);
        void notifyObserver(String tweet);
    }

    static private class NYTimes implements Observer{
        @Override
        public void inform(String tweet) {
            if(tweet!=null && tweet.contains("money")){
                System.out.println("Breaking news in NY!"+tweet);
            }
        }
    }
    static private class Guardian implements Observer{
        @Override
        public void inform(String tweet) {
            if(tweet!=null && tweet.contains("queen")){
                System.out.println("Yet another news in London..."+tweet);
            }
        }
    }

    static private class LeMonde implements Observer{
        @Override
        public void inform(String tweet) {
            if(tweet!=null && tweet.contains("wine")){
                System.out.println("Today cheese, wine and news!"+tweet);
            }
        }
    }
    static private class Feed implements Subject{

        private final List<Observer> observerList=new ArrayList<>();

        @Override
        public void registerObserver(Observer o) {
            this.observerList.add(o);
        }

        @Override
        public void notifyObserver(String tweet) {
            observerList.forEach(o->o.inform(tweet));
        }
    }


}
