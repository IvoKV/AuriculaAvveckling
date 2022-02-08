package DBSource;

import java.sql.*;

public class DBConnection {

    private String host;
    private String uName;
    private String uPass;

    private Connection con = null;

    public DBConnection(String host, String uName, String uPass) {
        this.host = host;
        this.uName = uName;
        this.uPass = uPass;
    }

    public Connection createConnection() throws SQLException, ClassNotFoundException {
        // load driver class for mysql - not needed
        //Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(host, uName, uPass);
    }

    public Connection createMyConnection() throws SQLException, ClassNotFoundException {
        // load driver class for mysql - not needed
        Class.forName("com.mysql.jdbc.Driver");
        return con = DriverManager.getConnection(host, uName, uPass);
        //Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

    }

    public void closeConnection(Connection con) throws SQLException {
        if (!con.isClosed()) {
            con.close();
        }
    }
}
