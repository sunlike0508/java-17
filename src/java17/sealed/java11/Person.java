package java17.sealed.java11;


import lombok.Getter;

@Getter
public abstract class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }
}
