import java.util.concurrent.CompletionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Util {
    public static int genRandomRange(int range){
        return (int)(Math.random()*range);
    }
    public static int randomInRangeInclusive(int min, int max){
        return genRandomRange(max - min + 1) + min;
    }
    public static int[] genRandomArray(int size, int min, int max){
        int[] array = new int[size];
        for(int i = 0; i < array.length; ++i){
            array[i] = randomInRangeInclusive(min, max);
        }
        return array;
    }
    public static <T, R> Function<T, R> noteTimeTaken(String nameOfFunction, Function<T, R> function){
        return (T value) -> {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.startStopwatch();
            R result = function.apply(value);
            System.out.printf("%s за %s секунд\n", nameOfFunction, stopwatch.stopAndGetFormatedTime());
            return result;
        };
    }


    public static <T> Supplier<T> noteTimeTaken(String nameOfSupplier, Supplier<T> supplier){
        return () -> {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.startStopwatch();
            T result = supplier.get();
            System.out.printf("%s за %s секунд\n", nameOfSupplier, stopwatch.stopAndGetFormatedTime());
            return result;
        };
    }
    public static <T> Consumer<T> noteTimeTaken(String nameOfConsumer, Consumer<T> consumer){
        return (T value) -> {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.startStopwatch();
            consumer.accept(value);
            System.out.printf("%s за %s секунд\n", nameOfConsumer, stopwatch.stopAndGetFormatedTime());
        };
    }
    public static Runnable noteTimeTaken(String nameOfRunnable, Runnable runnable){

        return () -> {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.startStopwatch();
            runnable.run();
            System.out.printf("%s за %s секунд\n", nameOfRunnable, stopwatch.stopAndGetFormatedTime());
        };
    }
    public static void sleepALittle(){
        int randomDelay = Util.randomInRangeInclusive(50, 150);
        try {
            Thread.sleep(randomDelay);
        } catch (InterruptedException exception) {
            throw new CompletionException(exception);
        }
    }
}
