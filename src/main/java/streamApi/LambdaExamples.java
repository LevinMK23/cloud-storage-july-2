package streamApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javafx.util.Pair;

public class LambdaExamples {

    public static void main(String[] args) {

        Func func = new Func() {
            @Override
            public int foo(int a, int b) {
                return a * b;
            }
        };

        Func lambda = (x, y) -> x * y;
        Func sumReference = Integer::sum;
        System.out.println(lambda.foo(2, 3));
        System.out.println(sumReference.foo(1, 2));

        Consumer<Boolean> printer = System.out::println;
        Consumer<String> lambdaPrint = arg -> System.out.println("Hello " + arg + "!");
        // printer.accept("Hello world!");
        lambdaPrint.accept("Mike");

        Predicate<Integer> predicate = arg -> arg > 0;
        printer.accept(predicate.test(-5));

        Predicate<Pair<Integer, Integer>> bigger = LambdaExamples::bigger;

        Function<String, List<String>> mapToList = Collections::singletonList;
        System.out.println(mapToList.apply("123"));

        Function<String, Integer> wordLength = String::length;
        System.out.println(wordLength.apply("Word"));

        Supplier<String> pathSupplier = () -> "/dir";

        System.out.println(pathSupplier.get());
    }

    static boolean bigger(Pair<Integer, Integer> pair) {
        return pair.getKey() > pair.getValue();
    }
}
