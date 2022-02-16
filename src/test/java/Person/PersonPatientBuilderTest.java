package Person;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonPatientBuilderTest {

    private final String connectionString = "src/resource/ConnectionString.txt";
    private Connection connection = null;

    PersonPatientBuilder underTest = new PersonPatientBuilder(connectionString);

    public PersonPatientBuilderTest() throws IOException {
    }

    @Test
    @DisplayName("it Should return valid connection")
    void itShouldReturnConnection() throws SQLException, ClassNotFoundException {
        // given

        // when
        Connection con = underTest.getConnection();

        // then
        assertThat(con).isNotNull();
        //assertThat(underTest.comparePersonListCountWithJsonPersonCount()).isTrue();
    }

    @Test
    @DisplayName("itemsCount")
    void listPersonItemsShouldBeEqualToJSONPersonId() throws ClassNotFoundException, SQLException, PersonInitializationException, IOException {
        // when
        underTest.buildPersonPatient(true);

        // then
        assertThat(underTest.comparePersonListCountWithJsonPersonCount()).isTrue();
    }

}