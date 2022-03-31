package Person;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PersonInChargeAllTitles {
    private String title;
    private String befattningskod;

    public PersonInChargeAllTitles(String title, String befattning) throws PersonInChargeException {
        this.title = title;
        this.befattningskod = befattning;

        try {
            Objects.requireNonNullElse(title, "title is null");
            Objects.requireNonNullElse(befattningskod, "no mapping value");

        }catch (NullPointerException npe) {
            throw new PersonInChargeException(npe.getMessage());
        }


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBefattningskod() {
        return befattningskod;
    }

    public void setBefattningskod(String befattningskod) {
        this.befattningskod = befattningskod;
    }

    @Override
    public String toString() {
        return "PersonInChargeAllTitles{" +
                "title='" + title + '\'' +
                ", befattningskod='" + befattningskod + '\'' +
                '}';
    }
}
