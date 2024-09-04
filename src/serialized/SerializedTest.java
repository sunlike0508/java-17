package serialized;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;


public class SerializedTest {

    public static void main(String[] args) {
        //conductSerializing();
        conductDeserializing();
    }

    public static void conductSerializing() {


        try {
            FileOutputStream fos = new FileOutputStream("user.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream out = new ObjectOutputStream(bos);

            Member member = new Member();
            member.setName("shin");
            member.setEmail("sunlke0508@gmail.com");
            member.setAge(35);

            out.writeObject(member);

            out.close();
            System.out.println("직렬화 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void conductDeserializing() {
        try {
            FileInputStream fis = new FileInputStream("user.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream in = new ObjectInputStream(bis);

            Member member = (Member) in.readObject();

            System.out.println(member.toString());

            in.close();
            System.out.println("역직렬화 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    @ToString
    static class Member implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;
        private String email;
        private int age;
        //private String address;
    }
}
