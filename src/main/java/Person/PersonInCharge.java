package Person;

import java.util.Objects;

public class PersonInCharge {
    private String id;
    private String title;
    private String groupId;
    private String firstName;
    private String lastName;

    public PersonInCharge(String id, String title, String groupId, String firstName, String lastName) throws PersonInChargeException {
        this.id = id;
        this.title = title;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            Objects.requireNonNullElse(id, "id is null");
            Objects.requireNonNullElse(title, "title is null");
            Objects.requireNonNullElse(groupId, "groupId is null");
            Objects.requireNonNullElse(firstName, "firstName is null");
            Objects.requireNonNullElse(lastName, "lastName is null");

        }catch (NullPointerException npe) {
            throw new PersonInChargeException(npe.getMessage());
        }
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
