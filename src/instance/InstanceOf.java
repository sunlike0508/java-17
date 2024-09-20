package instance;


public class InstanceOf {

    public static void instanceOf() {
        Person person = new Employee("java 11");

        if(person instanceof Employee) {
            System.out.println(person.getName() + "에서 비교");
        }

        if(person instanceof Employee employee) {
            employee.setName("java 17");
            System.out.println(employee.getName() + "에서 비교");
        }
    }

    public static void main(String[] args) {
        instanceOf();
    }
}
