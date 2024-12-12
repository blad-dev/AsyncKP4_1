import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Joiner  {
    private final List<CompletableFuture> joined;
    Joiner(){
        joined = new ArrayList<>();
    }
    public void addToJoinList(CompletableFuture future){
        joined.add(future);
    }
    public void joinAll() {
        while (!joined.isEmpty()){
            joined.removeLast().join();
        }
    }
}
