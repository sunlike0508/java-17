package NullPoint;

import instance.Employee;
import instance.Person;

public class NullPointException {

    public static void nullPointException() {
        String str = null;

        str.toLowerCase();

        /**
         * Java11에서는 이렇게 오류
         * Exception in thread "main" java.lang.NullPointerException
         * 	at NullPoint.NullPointException.nullPointException(NullPointException.java:11)
         * 	at NullPoint.NullPointException.main(NullPointException.java:15)
         */

        /** Java 17에서는 어디서 오류났는지 자세히 오류
         * Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.toLowerCase()" because "str" is null
         * 	at NullPoint.NullPointException.nullPointException(NullPointException.java:11)
         * 	at NullPoint.NullPointException.main(NullPointException.java:24)
         */
    }

    public static void main(String[] args) {
        nullPointException();
    }
}
