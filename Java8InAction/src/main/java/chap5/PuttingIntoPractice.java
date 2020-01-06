package chap5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class PuttingIntoPractice {
    
    public static void main(String[] args){
    	Trader raoul=new Trader("Raoul","Cambridge");
    	Trader mario=new Trader("Mario","Milan");
    	Trader alan=new Trader("Alan","Cambridge");
    	Trader brian=new Trader("Brian","Cambridge");
    	
    	List<Transaction> transactionList= Arrays.asList(
    	  new Transaction(brian,2011,300),
          new Transaction(raoul,2012,1000),
          new Transaction(raoul,2011,400),
          new Transaction(mario,2012,710),
          new Transaction(mario,2012,700),
          new Transaction(alan,2012,950)      
        );
    	
    	//1.
        List<Transaction> transactions=transactionList.stream()
                .filter(t->t.getYear()==2011)
                .sorted(comparing(Transaction::getValue))
				.collect(toList());
        System.out.println("2011年的交易："+transactions);
        
        //2.
        List<String> citys=transactionList.stream()
                .map(t->t.getTrader().getCity())
                //.collect(toSet());
                .distinct()
                .collect(toList());
        System.out.println("交易员工作的城市："+citys);

        //3.
        List<Trader> traders=transactionList.stream()
                .map(t->t.getTrader())
                .filter(t->t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println("来自剑桥的交易员："+traders);
        
        //4.
        String traderNames=transactionList.stream()
                .map(t->t.getTrader().getName())
                .distinct()
                .sorted()
                //.collect(joining())内部使用StringBuilder
                .reduce("",(n1,n2)->n1+" "+n2);
        System.out.println("所有交易员："+traderNames);
        
        //5.
        Optional<Trader> trader=transactionList.stream()
                .filter(t->t.getTrader().getCity().equals("Milan"))
                .map(t->t.getTrader())
                .findAny();
        //答案
        boolean milanBased=transactionList.stream()
                .anyMatch(t->t.getTrader().getCity().equals("Milan"));
        System.out.print(milanBased);
        System.out.println("米兰工作的交易员："+trader);

        //6.
        int count=transactionList.stream()
                .filter(t->t.getTrader().getCity().equals("Cambridge"))
                .map(t->t.getValue())
                //.forEach(System.out::println)
                .reduce(0,(t1,t2)->t1+t2);
        System.out.println("生活在剑桥的交易员的交易额之和："+count);
        
        //7.
        int max=transactionList.stream()
                // .map(t->t.getValue())
                .map(Transaction::getValue)
                .reduce(0,Integer::max);
        System.out.println("最高的一项交易额："+max);

        //8.
        //错误
        // int min=transactionList.stream()
        //         .map(t->t.getValue())
        //         .reduce(0,Integer::min);
        Optional<Transaction> smallestTransaction=
                transactionList.stream()
                .reduce((t1,t2)->t1.getValue()<t2.getValue()?t1:t2);
        Optional<Transaction> smallestTransaction2=
                transactionList.stream()
                .min(comparing(Transaction::getValue));
        System.out.println("最小的一项交易额："+smallestTransaction);
    }
}
