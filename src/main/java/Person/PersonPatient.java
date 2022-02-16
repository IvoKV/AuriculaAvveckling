package Person;

import java.util.Objects;

public class PersonPatient {
    private String id;
    private String firstName;
    private String lastName;

    public PersonPatient(String id, String firstName, String lastName) throws PersonInitializationException {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            this.id = Objects.requireNonNullElse(id,"id is null");
            this.firstName = Objects.requireNonNullElse(firstName, "firstName is null");
            this.lastName = Objects.requireNonNullElse(lastName, "lastName is null");
        }
        catch (NullPointerException npe) {
            throw new PersonInitializationException(npe.getMessage());
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
