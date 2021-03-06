package Person;

import java.util.Objects;

public class GeneralBefattning {
    private String hsaId;
    private String firstName;
    private String lastName;
    private String titel;

    public GeneralBefattning(String hsaId, String firstName, String lastName, String titel) throws GeneralBefattningException {
        this.hsaId = hsaId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.titel = titel;

        try{
            Objects.requireNonNullElse(hsaId, "hsaId is null");
            Objects.requireNonNullElse(firstName, "firstName is null");
            Objects.requireNonNullElse(lastName, "lastName is null");
            Objects.requireNonNullElse(titel, "titel is null");

        }catch (Exception exp){
            throw new GeneralBefattningException(exp.getMessage());
        }
    }

    public GeneralBefattning() {
    }

    public String getHsaId() {
        return hsaId;
    }

    public void setHsaId(String hsaId) {
        this.hsaId = hsaId;
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

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Override
    public String toString() {
        return "GeneralBefattning{" +
                "hsaId='" + hsaId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", titel='" + titel + '\'' +
                '}';
    }
}
