package java17.sealed.java11;

public class Main {

    public static void main(String[] args) {
        Customer customer = new Customer("customer");
        Employee employee = new Employee("employee");

        Person person1 = customer;
        System.out.println(person1.getName());

        Person person2 = employee;

        System.out.println(person2.getName());
    }

    class Manager extends Person {

        public Manager(String name) {
            super(name);
        }
    }
}
