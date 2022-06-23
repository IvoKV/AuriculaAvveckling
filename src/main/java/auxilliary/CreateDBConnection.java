package auxilliary;

import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateDBConnection {
    private static final String simpleConnectionFilePath = "src/resource/ConnectionStringTolitds63513845.txt";
    private String datasourceHost;

    private MyConnection myConnection;
    private Connection dbConnection;
    private String databaseUse;

    public CreateDBConnection(String datasourceHost, String databaseUse) {
        this.datasourceHost = datasourceHost;
        this.databaseUse = databaseUse;
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException, JSchException, IOException {
        if(datasourceHost == "cluster") {
            myConnection = new MyConnection(databaseUse);
            myConnection.createSshTunnel();
            return dbConnection = myConnection.getSSHDbConnection();
        }
        else if (datasourceHost == "stationär"){
            Path path = Path.of(simpleConnectionFilePath);

            String connectionString = Files.readString(path);

            String host = connectionString.split(";")[0];
            String uName = connectionString.split(";")[1];
            String uPass = connectionString.split(";")[2];

            myConnection = new MyConnection(host, uName, uPass);
            return dbConnection = myConnection.getConnection();
        }
        else {
            System.out.println("Improper choice of datacourceHost!");
            System.exit(0);
            return null;
        }
    }

    public void closeMyConnection(){
        myConnection.disconnectSession();
        System.out.println("Session is disconnected.");
    }
}
