package chap11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BestPriceFinder {

    private final List<Shop> shops= Arrays.asList(
            new Shop("BestShop"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy")
    );
    private final Executor executor= Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t=new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public List<String> findPricesSequential(String product){
        return shops.stream()
                .map(shop->shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }
    public List<String> findPricesParallel(String product){
        return shops.parallelStream()
                .map(shop->shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    public List<String> findPricesFuture(String product){
        List<CompletableFuture<String>> priceFutures=findPricesStream(product)
                .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    private Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop->CompletableFuture.supplyAsync(()->shop.getPrice(product),executor))
                .map(future->future.thenApply(Quote::parse))
                .map(future->future.thenCompose(quote->CompletableFuture.supplyAsync(()->Discount.applyDiscount(quote))));
    }

    public void printPricesStream(String product){
        long start=System.nanoTime();
        CompletableFuture[] futures=findPricesStream(product)
                .map(f->f.thenAccept(s->System.out.println(s+" (done in "+((System.nanoTime()-start)/1_000_000)+" msecs")))
                .toArray(size->new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in "+((System.nanoTime()-start)/1_000_000)+" msecs");
    }
}
