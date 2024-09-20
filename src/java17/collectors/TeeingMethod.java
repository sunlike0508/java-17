package java17.collectors;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeeingMethod {

    public static void teeingMethod() {

//        Collector<T, ?, R> teeing(
//                Collector<? super T, ?, R1> downstream1,
//                Collector<? super T, ?, R2> downstream2,
//                BiFunction<? super R1, ? super R2, R> merger);

        AtomicInteger minNumber = new AtomicInteger();
        AtomicInteger maxNumber = new AtomicInteger();

        Integer collect = Stream.of(1, 2, 3, 4, 5).collect(
                Collectors.teeing(Collectors.minBy(Integer::compareTo), Collectors.maxBy(Integer::compareTo),
                        (min, max) -> {
                            minNumber.set(min.orElse(0));
                            maxNumber.set(max.orElse(0));
                            return min.orElse(0) + max.orElse(0);
                        }));

        System.out.println(minNumber.get());
        System.out.println(maxNumber.get());
        System.out.println(collect);
    }

    public static void main(String[] args) {
        teeingMethod();
    }
}


