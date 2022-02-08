package Person;

import DBSource.DBConnection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonPatientBuilder {

    private String host;
    private String uName;
    private String uPass;
    private final String sqlScriptFilePath = "src/resource/PersonPatient.sql";

    public PersonPatientBuilder(String host, String uName, String uPass) {
        this.host = host;
        this.uName = uName;
        this.uPass = uPass;
    }

    public void buildPersonPatient() throws SQLException, ClassNotFoundException, IOException, PersonInitializationException {

        Path file = Path.of(sqlScriptFilePath);
        //BufferedReader reader = null;

        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        Connection myConnection = dbConnection.createConnection();

        /*
        try {
            reader = new BufferedReader(new FileReader(sqlScriptFilePath));
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
         */

        //String sqlStatement = reader.read();
        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();

        ResultSet rs = statement.executeQuery(sqlStatement);

        //PreparedStatement preparedStatement = null;
        //preparedStatement = myConnection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        List<PersonPatient> personPatients = new ArrayList<>();

        while (rs.next()) {
            PersonPatient person = new PersonPatient(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3));
            personPatients.add(person);
        }

        personPatients.stream()
                .forEach(System.out::println);

        FileWriter writer = new FileWriter("personPatients.txt");
        for (PersonPatient person : personPatients) {
            writer.write(person + System.lineSeparator());
        }
        writer.close();
    }
}
