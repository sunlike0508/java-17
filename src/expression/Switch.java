package expression;


import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

public class Switch {

    public static void switchExpressions(int today) {

        int result = switch(today) {
            case MONDAY, TUESDAY, WEDNESDAY -> 1;
            case THURSDAY, FRIDAY -> 2;
            case SATURDAY -> 3;
            default -> 0;
        };

        System.out.println(result);
    }

    public static void main(String[] args) {
        switchExpressions(MONDAY);
    }
}
