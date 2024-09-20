package refactor.static_object.v3;

public enum HttpStatus {

    OK(200, "Success"),
    CAEATED(201, "Created"),
    NOT_FOUND(404, "Not Found");

    private int value;
    private String description;

    HttpStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    int getValue() {
        return value;
    }

    String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Http Status (" + value + ")";
    }
}
