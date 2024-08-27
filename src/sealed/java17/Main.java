package sealed.java17;

public class Main {

    /**
     * <p> Sealed 클래스를 사용하면 클래스를 확장할 수 있는 클래스를 더 효과적으로 제어할 수 있다. </p>
     * <p> java 11에서는 클래스가 final 이거나 확장될수 있다. </p>
     * 어떤 제어를 하기 위해서 모든 클래스를 같은 패키지에 넣고 슈퍼 클래스 패키지 기사성(visibility)를 제공할 수 있었다.
     */
    public static void main(String[] args) {
        Person customer = new Customer("customer");
        Person employee = new Employee("employee");
        
        Person person1 = customer;
        System.out.println(person1.getName());
        person1.checkPerson();

        Person person2 = employee;

        System.out.println(person2.getName());
        person2.checkPerson();

        Person person3 = new Waiter("웨이터");
        System.out.println(person3.getName());
        person3.checkPerson();
    }

    /**
     * manager 클래스는 봉인되지 않았으므로 확장할 수 없는 클래스로 구분 가능
     */
    //    class Manager extends Person {
//
//        public Manager(String name) {
//            super(name);
//        }
//    }
}
