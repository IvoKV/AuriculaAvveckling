package Person;

import java.util.Objects;

public class PersonPatient {
    private int id;
    private String SSN;
    private String firstName;
    private String lastName;

    public PersonPatient(int id, String ssn, String firstName, String lastName) throws PersonInitializationException {
        this.id = id;
        this.SSN = ssn;
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            this.id = Objects.requireNonNullElse(id,0);
            this.SSN = Objects.requireNonNullElse(SSN, "SSN is null");
            this.firstName = Objects.requireNonNullElse(firstName, "firstName is null");
            this.lastName = Objects.requireNonNullElse(lastName, "lastName is null");
        }
        catch (NullPointerException npe) {
            throw new PersonInitializationException(npe.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "PersonPatient{" +
                "id=" + id +
                ", SSN='" + SSN + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
