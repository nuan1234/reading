package chap14;

import java.util.stream.IntStream;

public class StreamTest {

    public static void main(String[] args){
    	IntStream numbers=numbers();
    	IntStream result=primes(numbers);
    	System.out.println(result);
    	//stream has already been operated upon or closed
    }

    static IntStream numbers(){
        return IntStream.iterate(2,n->n+1);
    }

    static int head(IntStream numbers){
        return numbers.findFirst().getAsInt();
    }

    static IntStream tail(IntStream numbers){
        return numbers.skip(1);
    }

    static IntStream primes(IntStream numbers){
        int head=head(numbers);
        return IntStream.concat(
                IntStream.of(head),
                primes(tail(numbers).filter(n->n % head !=0))
        );
    }
}
