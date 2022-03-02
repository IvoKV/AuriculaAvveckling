package ordination.Waran;

import Person.PersonInitializationException;
import Person.PersonPatientBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HemorrhagesBuilderTest {

    private final String connectionString = "src/resource/ConnectionString.txt";
    private Connection connection = null;

    HemorrhagesBuilder underTest = new HemorrhagesBuilder(connectionString);

    HemorrhagesBuilderTest() throws IOException {
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
    @DisplayName("Count of hemorrhages")
    void listHemorrhagesItemsShouldBeEqualToJSONObjectCount() throws ClassNotFoundException, SQLException, PersonInitializationException, IOException, OrdinationsperiodInitializeException {
        // given
        String centreId = "11012AK";
        //int regpatId = 0;
        int regpatId = 54241;

        // when
        underTest.buildHemorrhages(centreId, regpatId, false);

        // then
        assertThat(underTest.compareResults()).isTrue();
    }

}