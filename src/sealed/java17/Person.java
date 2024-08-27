package sealed.java17;


import lombok.Getter;

/**
 * 인터페이스도 가능
 */
@Getter
public abstract sealed class Person permits Customer, Employee {

    private String name;

    protected Person(String name) {
        this.name = name;
    }

    public void checkPerson() {
        switch(this) {
            case Customer ignored -> System.out.println("이건 customer");
            case Employee ignored -> System.out.println("이건 Employee");
            default -> throw new IllegalStateException("오류");
        }
    }
}
