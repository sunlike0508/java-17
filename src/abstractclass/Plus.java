package abstractclass;

public class Plus implements Calculate{

    @Override
    public double calculate(double a, double b) {
        return 0;
    }


    @Override
    public void display(int a, int b) {
        Calculate.super.display(a, b);
    }
}
