package Person;

import DBSource.DBConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PersonPatientBuilder {

    private String host;
    private String uName;
    private String uPass;
    private final String sqlScriptFilePath = "src/resource/PersonPatient.sql";
    private final String POJOFileName = "personPatients.txt";
    private final String JSONFileName = "personPatients.json";

    public PersonPatientBuilder(String host, String uName, String uPass) {
        this.host = host;
        this.uName = uName;
        this.uPass = uPass;
    }

    public void buildPersonPatient(Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException {

        Path file = Path.of(sqlScriptFilePath);

        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        Connection myConnection = dbConnection.createConnection();

        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();

        ResultSet rs = statement.executeQuery(sqlStatement);

        //PreparedStatement preparedStatement = null;
        //preparedStatement = myConnection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        List<PersonPatient> personPatients = new ArrayList<>();

        while (rs.next()) {
            PersonPatient person = new PersonPatient(
                    rs.getString("pid"),
                    rs.getString("firstName"),
                    rs.getString("lastName"));
            personPatients.add(person);
        }

        personPatients.stream()
                .forEach(System.out::println);

        if(writeToFile){
            writePOJOToFile(personPatients);
            POJOListToJSONToFile(personPatients);
        }
    }

    private void writePOJOToFile(List<PersonPatient> personPatients) throws IOException {
        FileWriter pojoWriter = new FileWriter(POJOFileName);
        for (PersonPatient person : personPatients) {
            pojoWriter.write(person + System.lineSeparator());
        }
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<PersonPatient> personPatients) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(personPatients);
        // 1. Convert List of person objects to JSON :");
        System.out.println(arrayToJson);

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(arrayToJson);
        jsonWriter.close();
    }
}
