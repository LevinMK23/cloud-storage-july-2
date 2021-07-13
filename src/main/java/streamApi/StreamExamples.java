package streamApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExamples {

    public static void main(String[] args) {
        List<Integer> integers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(x -> x % 2 == 0)
                .map(x -> x * 2)
                .collect(Collectors.toList());
        System.out.println(integers);

        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(x -> x % 2 == 0)
                .map(x -> x * 2)
                .forEach(x -> System.out.print(x + " "));
        System.out.println();

        Integer sum = Stream.of(1, 2, 3, 4, 5)
                .reduce(0, Integer::sum);
        // x + y + identity = x + y
        // x * y * identity = x * y
        System.out.println(sum);

        HashMap<Integer, Integer> result = Stream.of(1, 1, 1, 2, 2, 3)
                .reduce(new HashMap<>(),
                        (map, value) -> {
                            map.put(value, map.getOrDefault(value, 0) + 1);
                            return map;
                        },
                        (left, right) -> {
                            left.putAll(right);
                            return left;
                        });
        System.out.println(result);

        Stream.of(1, 1, 1, 2, 2, 3)
                .collect(Collectors.toMap(
                        Function.identity(),
                        arg -> 1,
                        Integer::sum
                )).forEach((key, val) -> System.out.println(key + ": " + val));

        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .collect(Collectors.toMap(
                        arg -> arg % 2,
                        arg -> { // [1]
                            List<Integer> list = new ArrayList<>();
                            list.add(arg);
                            return list;
                        },
                        (left, right) -> {
                            left.addAll(right);
                            return left;
                        }
                )).forEach((k, v) -> System.out.println(k + " : " + v));
    }
}
