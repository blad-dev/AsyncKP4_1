import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class Main {
    public static Consumer<int[]> curryPrintArray(String note) {
        return (int[] array) -> {
            printArray(note, array);
        };
    }

    private static void printArray(String note, int[] array) {
        System.out.print(note);
        for (int element : array) {
            System.out.printf("%d ", element);
        }
        System.out.println();
    }

    private static void asyncProgram() {
        Joiner joiner = new Joiner();
        Task task = new Task(true);
        CompletableFuture<int[]> numbers = task.genNumbersAsync(10, 0, 100);

        joiner.addToJoinList(numbers);
        joiner.addToJoinList(numbers.thenAcceptAsync(Util.noteTimeTaken("Виведено початковий масив", curryPrintArray("Щойно створений масив: "))));

        joiner.addToJoinList(numbers.thenApplyAsync(Util.noteTimeTaken("Знайдено суму початкового масиву", array -> {
            Util.sleepALittle();
            int sum = 0;
            for (int element : array) {
                sum += element;
            }
            return sum;
        })).thenAcceptAsync(Util.noteTimeTaken("Виведено суму початкового масиву", sum -> {
            System.out.printf("Сума початкового масиву: %d\n", sum);
        })).thenRunAsync(Util.noteTimeTaken("Виведено повідомлення про закінчення дій з початковим масивом",
                () -> {
                    System.out.println("Завершено усі операції з початковим масивом");
                }
        )));

        CompletableFuture<int[]> modifiedNumbers = numbers.thenApplyAsync(Util.noteTimeTaken("Додано 5 до кожного елемента масива", _numbers -> {
            Util.sleepALittle();
            int[] newNumbers = new int[_numbers.length];
            for (int i = 0; i < _numbers.length; ++i) {
                newNumbers[i] = _numbers[i] + 5;
            }
            return newNumbers;
        }));
        joiner.addToJoinList(modifiedNumbers);
        joiner.addToJoinList(modifiedNumbers.thenAcceptAsync(
                Util.noteTimeTaken("Виведено модифікований масив",
                        curryPrintArray("Масив після додавання 5 до кожного елемента: "))
        ));

        joiner.addToJoinList(modifiedNumbers.thenApplyAsync(Util.noteTimeTaken("Знайдено суму модифікованого масиву", array -> {
            Util.sleepALittle();
            int sum = 0;
            for (int element : array) {
                sum += element;
            }
            System.out.println(sum);
            return sum;
        })).thenApplyAsync(Util.noteTimeTaken("Знайдено факторіал від суми модифікованого масиву", sum -> {
            BigInteger factorial = BigInteger.ONE;
            for (int i = 2; i <= sum; ++i) {
                factorial = factorial.multiply(BigInteger.valueOf(i));
            }
            return factorial;
        })).thenAcceptAsync(Util.noteTimeTaken("Виведено факторіл", factorial -> {
            System.out.println("Факторіал суми модифікованого масиву: " + factorial.toString());
        })).thenRunAsync(
                Util.noteTimeTaken("Виведено повідомлення про закінчення дій з модифікованим масивом",
                        () -> {
                            System.out.println("Завершено усі операції з модифікованим масивом");
                        }
                )));

        joiner.joinAll();
    }

    public static void main(String[] args) {
        CompletableFuture<Void> program = CompletableFuture.runAsync(
                Util.noteTimeTaken("Виконано усю програму", Main::asyncProgram)
        );
        program.join();
    }
}