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

public class PersonInChargeBuilder {

    private String host;
    private String uName;
    private String uPass;
    private final String sqlScriptFilePath = "src/resource/PersonInCharge.sql";

    public PersonInChargeBuilder(String host, String uName, String uPass) {
        this.host = host;
        this.uName = uName;
        this.uPass = uPass;
    }

    public void buildPersonInCharge() throws SQLException, ClassNotFoundException, IOException, PersonInChargeException {
        Path file = Path.of(sqlScriptFilePath);

        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        Connection myConnection = dbConnection.createConnection();

        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();

        ResultSet rs = statement.executeQuery(sqlStatement);

        List<PersonPatient> personsInCharge = new ArrayList<>();

        while (rs.next()) {
            PersonInCharge personCharge = new PersonInCharge(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5));
            personsInCharge.add(personCharge);
        }

        personsInCharge.stream()
                .forEach(System.out::println);

        FileWriter writer = new FileWriter("personInCharge.txt");
        for (PersonInCharge person : personsInCharge) {
            writer.write(person + System.lineSeparator());
        }
        writer.close();
    }
}
