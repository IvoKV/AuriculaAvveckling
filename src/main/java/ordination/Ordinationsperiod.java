package ordination;

import java.util.Objects;

public class Ordinationsperiod  {

    private String id;  //p.id
    private int count;

    public Ordinationsperiod(String id, int count) throws OrdinationsInitializeException {
        this.id = id;
        this.count = count;

        try {
            this.id = Objects.requireNonNullElse(id,"id is null");

        }
        catch (NullPointerException npe) {
            throw new OrdinationsInitializeException(npe.getMessage());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Ordinationsperiod{" +
                "id='" + id + '\'' +
                ", count=" + count +
                '}';
    }
}
