import java.util.concurrent.CompletableFuture;

public class Task {
    private boolean delayIsOn;

    Task(boolean delayIsOn){
        this.delayIsOn = delayIsOn;
    }
    Task(){
        this(false);
    }
    public void changeDelay(boolean delayIsOn){
        this.delayIsOn = delayIsOn;
    }
    public CompletableFuture<int[]> genNumbersAsync(int size, int min, int max) {

        if(delayIsOn) {
            return CompletableFuture.supplyAsync(Util.noteTimeTaken("Згенеровано масив чисел", () -> {
                Util.sleepALittle();
                return Util.genRandomArray(size, min, max);
            }));
        }
        return CompletableFuture.supplyAsync(Util.noteTimeTaken("Згенеровано масив чисел",
                () -> Util.genRandomArray(size, min, max)));
    }
}

