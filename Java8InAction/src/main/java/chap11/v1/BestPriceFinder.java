package chap11.v1;

import chap11.ExchangeService;
import chap11.ExchangeService.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

public class BestPriceFinder {

    private final List<Shop> shops= Arrays.asList(
            new Shop("BestShop"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy")
    );

    private final Executor executor= Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread=new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    });

    public List<String> findPricesSequential(String product){
        return shops.stream()
                .map(shop ->shop.getName()+" price is "+shop.getPrice(product))
                .collect(toList());
    }

    public List<String> findPricesParallel(String product){
        return shops.parallelStream()
                .map(shop->shop.getName()+" price is "+shop.getPrice(product))
                .collect(toList());
    }

    public List<String> findPricesFuture(String product){
        List<CompletableFuture<String>> priceFutures=
                shops.stream()
                .map(shop->CompletableFuture.supplyAsync(
                        ()->shop.getName()+" price is "+shop.getPrice(product),executor
                ))
                .collect(toList());
        List<String> prices=priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
        return prices;
    }

    public List<String> findPricesInUSD(String product){
        List<CompletableFuture<Double>> priceFutures=new ArrayList<>();
        for(Shop shop:shops){
            CompletableFuture<Double> futurePriceInUSD=
                    CompletableFuture.supplyAsync(()->shop.getPrice(product))
                    .thenCombine(
                            CompletableFuture.supplyAsync(
                                    ()-> ExchangeService.getRate(Money.EUR, Money.USD)
                            ),(price,rate)->price*rate
                    );
            priceFutures.add(futurePriceInUSD);
        }
        List<String> prices=priceFutures.stream()
                .map(CompletableFuture::join)
                .map(price->" price is "+price)
                .collect(toList());
        return prices;
    }

    public List<String> findPricesInUSDJava7(String product){
        ExecutorService executor=Executors.newCachedThreadPool();
        List<Future<Double>> priceFutures=new ArrayList<>();
        for(Shop shop:shops){
            final Future<Double> futureRate=executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    return ExchangeService.getRate(Money.EUR,Money.USD);
                }
            });
            Future<Double> futurePriceInUSD=executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    double priceInEUR=shop.getPrice(product);
                    return priceInEUR*futureRate.get();
                }
            });
            priceFutures.add(futurePriceInUSD);
        }
        List<String> prices=new ArrayList<>();
        for(Future<Double> priceFuture:priceFutures){
            try {
                prices.add("price is "+priceFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return prices;
    }
}
