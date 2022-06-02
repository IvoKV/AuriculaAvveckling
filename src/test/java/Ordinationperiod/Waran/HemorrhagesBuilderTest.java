package Ordinationperiod.Waran;

import auxilliary.MyConnection;
import com.jcraft.jsch.JSchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class HemorrhagesBuilderTest {
    private static final String simpleConnectionFilePath = "src/resource/ConnectionString.txt";     // Används endast för öppen kanal till db source/-host
    private final String connectionString = "src/resource/ConnectionString.txt";
    private Connection dbConnection = null;

    private final String databaseUse = "auricula_export_TIO_100";

    // given
    HemorrhagesBuilderTest() throws ClassNotFoundException, SQLException, JSchException, IOException {
            MyConnection myConnection = new MyConnection(databaseUse);
            myConnection.createSshTunnel();                                     // todo: borde testas innan nästa steg, har skapat testcase för class: MyConnection
            dbConnection = myConnection.getSSHDbConnection();
    }

    // when
    HemorrhagesBuilder underTest = new HemorrhagesBuilder(dbConnection);

    @Test
    @DisplayName("HemorrhagesBuilderTest::Valid Connection")
    void itShouldReturnConnection() {

        //then
        assertThat(dbConnection).isNotNull();
    }

    /*
    @Test
    @DisplayName("it Should return valid connection")
    void itShouldReturnConnection() throws SQLException, ClassNotFoundException {
        // given

        // when
        Connection con = underTest.getMyconnection();

        // then
        assertThat(con).isNotNull();
        //assertThat(underTest.comparePersonListCountWithJsonPersonCount()).isTrue();
    }

    @Test
    @DisplayName("Count of hemorrhages")
    void listHemorrhagesItemsShouldBeEqualToJSONObjectCount() throws ClassNotFoundException, SQLException, PersonInitializationException, IOException, OrdinationsperiodInitializeException, JSchException {
        // given
        String centreId = "11012AK";
        //int regpatId = 0;
        int regpatId = 54241;

        // when
        underTest.buildHemorrhages(centreId, regpatId, false);

        // then
        assertThat(underTest.compareResults()).isTrue();
    }

     */

}