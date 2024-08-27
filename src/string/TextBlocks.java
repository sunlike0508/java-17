package string;

public class TextBlocks {

    public static void textBlocks() {
        String java11 = "{\n"
                + " \"name\" : \"shin\"\n"
                + "}";

        System.out.println(java11);

        String java17 = """
                      {
                       "name" : "shin"
                      }
                      """;

        System.out.println(java17);
    }

    public static void main(String[] args) {
        textBlocks();
    }
}
