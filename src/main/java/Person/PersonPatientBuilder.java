package Person;

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
    private final String sqlScriptFilePath = "src/resource/sql/person/PersonPatient.sql";
    private final String POJOFileName = "temp/personPatients.txt";
    private final String JSONFileName = "temp/personPatients.json";
    private final String JSONFilePersonsId = "temp/personPatientSSN.json";

    private Connection myConnection = null;
    private int listCount = -99999;
    private int jsonCount = 0;

    public PersonPatientBuilder(final Connection con) throws IOException {
        this.myConnection = con;
    }

    public void buildPersonPatient(String centreId, Boolean writeToFile) throws SQLException, IOException, PersonInitializationException {
        ResultSet rsPerson = null;

        //preparedStatement = myConnection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        List<PersonPatient> personPatients = new ArrayList<>();

        Path file = Path.of(sqlScriptFilePath);
        String sqlStatement = Files.readString(file);

        PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
        selectOrdinationWaran.setString(1, centreId);

        rsPerson = selectOrdinationWaran.executeQuery();

        while (rsPerson.next()) {
//            try{
                PersonPatient person = new PersonPatient(
                    rsPerson.getInt("PID"),
                    rsPerson.getString("SSN"),
                    rsPerson.getString("FIRSTNAME"),
                    rsPerson.getString("LASTNAME"));
                    personPatients.add(person);
//            }
//            catch (Exception e){
//                e.printStackTrace();
//                continue;
//            }
        }
        myConnection.close();
        listCount = personPatients.size();

        personPatients.stream()
                .forEach(System.out::println);
        System.out.println("Total antal poster: " + personPatients.size());

        if(writeToFile){
            POJOToFile(personPatients);
            POJOListToJSONToFile(personPatients);

            // write JSON
            extractPersonIDSSN(personPatients);
        }
        System.out.println("Antal poster: " + listCount);
    }

    private void POJOToFile(List<PersonPatient> personPatients) throws IOException {
        FileWriter pojoWriter = new FileWriter(POJOFileName);
        long count = personPatients.stream().count();
        for (PersonPatient person : personPatients) {
            pojoWriter.write(person + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + count + System.lineSeparator());
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<PersonPatient> personPatients) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(personPatients);
        JSONArray jArr = new JSONArray(arrayToJson);
        // 1. Convert List of person objects to JSON :");
        System.out.println(arrayToJson);
        System.out.println("Total antal poster (json objekt): " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(arrayToJson);
        jsonWriter.write("\nTotal antal poster (json objekt): " + jArr.length() + System.lineSeparator());
        jsonWriter.close();
    }

    private void extractPersonIDSSN(List<PersonPatient> personPatients) throws IOException {
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
                new JSONObject(extractArray.toString()).getString("SSN") + "\n"
                );
                jsonCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        persIdWriter.write("Antal poster: " +  jsonArray.length() + System.lineSeparator());
        persIdWriter.close();
    }

    public boolean comparePersonListCountWithJsonPersonCount(){
        return listCount == jsonCount;
    }
}
