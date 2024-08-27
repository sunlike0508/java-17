package string;

public class StringMethod {

    public static void indentMethod() {
        String text = "Like and subscribe";
        System.out.println(text = text.indent(4));
        System.out.println(text.indent(-2));
    }

    public static void main(String[] args) {
        indentMethod();
    }
}
