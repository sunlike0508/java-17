package java17.collectors;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToList {

    public static void toList() {

        List<String> java11 = Stream.of("a", "b", "c").collect(Collectors.toList());

        java11.forEach(System.out::println);

        List<String> java17 = Stream.of("a", "b", "c").toList();

        java17.forEach(System.out::println);
    }

    public static void main(String[] args) {
        toList();
    }
}
