package Person;

import DBSource.DBConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonPatientBuilder {

    private String host = null;
    private String uName = null;
    private String uPass = null;

    private final String sqlScriptFilePath = "src/resource/PersonPatient.sql";
    private final String POJOFileName = "temp/personPatients.txt";
    private final String JSONFileName = "temp/personPatients.json";
    private final String JSONFilePersonsId = "temp/personPatientsID.json";
    private Connection myConnection = null;
    private int listCount = -99999;
    private int jsonCount = 0;

    public PersonPatientBuilder(String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildPersonPatient(Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException {

        Path file = Path.of(sqlScriptFilePath);
        myConnection = getConnection();

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
        myConnection.close();
        listCount = personPatients.size();

        personPatients.stream()
                .forEach(System.out::println);

        if(writeToFile){
            writePOJOToFile(personPatients);
            POJOListToJSONToFile(personPatients);

            // write JSON
            extractPersonID(personPatients);
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
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

        String listToJson = objectMapper.writeValueAsString(personPatients);
        // 1. Convert List of person objects to JSON :");
        System.out.println(listToJson);

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.close();
    }

    private void extractPersonID(List<PersonPatient> personPatients) throws IOException {
        JSONArray jsonArray = new JSONArray(personPatients);
        int len = jsonArray.length();

        var countPersonObjects = jsonArray.get(len - 1);
        //JSONObject jsonObject = new JSONObject(personObject.toString());
        //String id = jsonObject.getString("id");

        FileWriter persIdWriter = new FileWriter(JSONFilePersonsId);
        jsonArray.forEach(extractArray ->
        {
            try {
                persIdWriter.write(
                new JSONObject(extractArray.toString()).getString("id") + "\n");
                jsonCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        persIdWriter.close();
    }

    public boolean comparePersonListCountWithJsonPersonCount(){
        return listCount == jsonCount;
    }
}
