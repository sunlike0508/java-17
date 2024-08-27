package records;

import java.util.Objects;

public class Records {

    private final String id;

    public Records(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        Records records = (Records) o;
        return Objects.equals(id, records.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Records{" + "id='" + id + '\'' + '}';
    }
}
