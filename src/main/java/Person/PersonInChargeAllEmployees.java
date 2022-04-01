package Person;

import java.util.Objects;

public class PersonInChargeAllEmployees {
    private String id;
    private String title;
    private String groupId;
    private String firstName;
    private String lastName;
    private String befattningskod;

    public PersonInChargeAllEmployees(String id, String title, String groupId, String firstName, String lastName, String befattning) throws PersonInChargeException {
        this.id = id;
        this.title = title;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.befattningskod = befattning;

        try {
            Objects.requireNonNullElse(id, "id is null");
            Objects.requireNonNullElse(title, "title is null");
            Objects.requireNonNullElse(groupId, "groupId is null");
            Objects.requireNonNullElse(firstName, "firstName is null");
            Objects.requireNonNullElse(lastName, "lastName is null");
            Objects.requireNonNullElse(befattningskod, "no mapping value");

        }catch (NullPointerException npe) {
            throw new PersonInChargeException(npe.getMessage());
        }
    }

    public PersonInChargeAllEmployees(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getTitle() {
        return title;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBefattningskod() {
        return befattningskod;
    }

    public void setBefattningskod(String befattningskod) {
        this.befattningskod = befattningskod;
    }

    @Override
    public String toString() {
        return "PersonInCharge{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", groupId='" + groupId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
