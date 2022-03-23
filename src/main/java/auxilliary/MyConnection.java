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
    private final String databasePassword = "ivo64..";     //databasePassword

    private Session session = null;




    //private final String strSshHost = "localhost";
    //private final int nSshPort = 22;

    //private final int nLocalPort = 4321;


    private static final String connectionFilePath = "src/resource/ConnectionStringSshTunnel.txt";

    private Connection connection = null;

    public MyConnection() throws JSchException, SQLException, IOException, ClassNotFoundException {
        //doSshTunnel();
        //connection = getSSHConnection();
        //getSSHConnection();
    }

    public MyConnection(String host, String uName, String uPass) throws JSchException, SQLException, IOException, ClassNotFoundException {
        this.host = host;
        this.uName = uName;
        this.uPass = uPass;

        connection = getConnection();
    }


    // must be accessible for test class
    public void createSshTunnel() throws SQLException, ClassNotFoundException, IOException, JSchException {


        JSch jSch = new JSch();
        jSch.addIdentity("c:\\Users\\cwz7\\.ssh\\id_rsa");

        session = jSch.getSession(jumpserverUsername, jumpserverHost);

        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();

        System.out.println("Connection lyckades!");

        int forwardedPort =  session.setPortForwardingL(0, databaseHost, databasePort);



        /*
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(connectionString, databaseUsername, databasePassword);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
        return connection;
    }

 */
    }


    public  Connection getDbConnection() throws JSchException, IOException {
        int forwardedPort =  session.setPortForwardingL(0, databaseHost, databasePort);

        Path path = Path.of(connectionFilePath);
        String connectionString = Files.readString(path);

        String url = connectionString + ":" + forwardedPort;
        url = "jdbc:mysql://localhost:" + forwardedPort;

        try {
            Connection con = DriverManager.getConnection(url, databaseUsername, databasePassword);
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


}
