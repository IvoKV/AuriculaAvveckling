package auxilliary;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyConnectionTest {
    private static final String databaseUse = "auricula_export_TIO_100";

    MyConnection underTest = new MyConnection(databaseUse);
    Session session = null;

    MyConnectionTest() throws ClassNotFoundException, SQLException, JSchException, IOException {
        // given
        //underTest.createSshTunnel();
    }

    @Test
    @DisplayName("MyConnectionTest::shouldCreateSessionForSSHTunnel")
    void shouldCreateValidSessionForSSHTunnel() throws ClassNotFoundException, SQLException, JSchException, IOException {
        // when
        session = underTest.createSshTunnel();

        //then
        assertThat(session.isConnected());
    }

    @Test
    @DisplayName("MyConnectionTest::getValidSSHDbConnection")
    void getValidSSHDbConnection() throws IOException, JSchException {
        // when
        if(session != null){
            Connection con = underTest.getSSHDbConnection();

            // then
            assertThat(con).isNotNull();
        }
        else {
            assertThat(true).isFalse();
        }
    }
}