package java17.string;

public class TransformMethod {

    public static void transformMethod() {
        StringBuilder transform = "Geekific".transform(value -> new StringBuilder(value).delete(1, 7));

        System.out.println(transform);
    }

    public static void main(String[] args) {
        transformMethod();
    }
}
