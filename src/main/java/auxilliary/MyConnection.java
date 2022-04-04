package auxilliary;

import DBSource.DBConnection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {
    private String host = null;
    private String uName = null;
    private String uPass = null;

    private final String jumpserverHost = "sllmltprodapp01.dc.sll.se";
    private final String jumpserverUsername = "cwz7";       //jumpserveUsername
    //private final String strSshPassword  = "";      //jumpserverUsername


    private final String databaseHost = "sllmltprodapp01.dc.sll.se";  // databaseHost
    private final int databasePort = 30306;  // databasePort
    private final String databaseUsername = "cwz7";            //databaseUsername
    private final String databasePassword = "ivo64..";      //databasePassword
    private String databaseUse = null;                      // vilken databas som väljs

    private Session session = null;

    private static final String connectionFilePath = "src/resource/ConnectionStringSshTunnel.txt";

    private Connection connection = null;

    // ssh uppkoppling genom tunnel
    public MyConnection(String dbUse) throws JSchException, SQLException, IOException, ClassNotFoundException {
        this.databaseUse = dbUse;
    }

    // vanlig uppkoppling
    public MyConnection(String host, String uName, String uPass) throws JSchException, SQLException, IOException, ClassNotFoundException {
        this.host = host;
        this.uName = uName;
        this.uPass = uPass;
    }

    public Session createSshTunnel() throws SQLException, ClassNotFoundException, IOException, JSchException {
        JSch jSch = new JSch();
        jSch.addIdentity("c:\\Users\\cwz7\\.ssh\\id_rsa");

        session = jSch.getSession(jumpserverUsername, jumpserverHost);

        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        try{
            session.connect();
            System.out.println("Connection lyckades!");
            return session;
        }
        catch (Exception e){
            System.out.println("Tunnel kunde inte skapas.");
            e.printStackTrace();
            System.exit(0);
        }
        int forwardedPort =  session.setPortForwardingL(0, databaseHost, databasePort);
        // the session is returned only for the need of the test case
        return session;
    }

    public Connection getSSHDbConnection() throws JSchException, IOException {
        int forwardedPort =  session.setPortForwardingL(0, databaseHost, databasePort);

        Path path = Path.of(connectionFilePath);
        String connectionString = Files.readString(path);

        String url = "jdbc:mysql://localhost:" + forwardedPort + "/" + databaseUse;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, databaseUsername, databasePassword);
            if(con == null)
                System.out.println("con is null");
            return con;
        }
        catch (SQLException e1) {
            e1.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    public void disconnectSession(){
        session.disconnect();
    }
}
