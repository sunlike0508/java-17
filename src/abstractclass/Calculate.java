package abstractclass;

public interface Calculate {

    double calculate(double a, double b);

    default void display(int a, int b) {
        System.out.println("계산 : " + a + b);
    }
}