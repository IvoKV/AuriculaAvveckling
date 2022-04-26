package Person;

import auxilliary.MappingPositions;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonInChargeAllEmployeesBuilder {
    private final String sqlScriptFilePathAllTitles = "src/resource/sql/person/PersonInChargeAllEmployees.sql";      // All titles, creating a total of employees in charge
    private final String sqlScriptFilePathAllTitlesTioHundra = "src/resource/sql/person/PersonInChargeAllEmployeesTioHundra.sql";
    private Connection myConnection = null;

    private final String POJOFileName = "temp/personInChargeAllEmps.txt";
    private final String JSONFileName = "temp/personInChargeAllEmps.json";

    public PersonInChargeAllEmployeesBuilder(final Connection con) {
        this.myConnection = con;
    }

    public void buildPersonInChargeEmployees(Boolean writeToFile) throws SQLException, IOException, PersonInChargeException {
        //Path file = Path.of(sqlScriptFilePathAllTitles);
        Path file = Path.of(sqlScriptFilePathAllTitlesTioHundra);

        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();

        ResultSet rs = statement.executeQuery(sqlStatement);

        List<PersonInChargeAllEmployees> personsInChargeEmployees = new ArrayList<>();
        MappingPositions map = new MappingPositions();
        while (rs.next()) {
            if(rs.getString("titel") == null)
                continue;
            String befattningskod = map.getBefattning(rs.getString("titel"));
            PersonInChargeAllEmployees pEmployee = new PersonInChargeAllEmployees(
                    rs.getString("id"),
                    rs.getString("titel"),
                    rs.getString("groupid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    befattningskod
            );
            personsInChargeEmployees.add(pEmployee);
        }

        personsInChargeEmployees.stream()
                .forEach(System.out::println);
        System.out.println("Total antal poster: " + personsInChargeEmployees.size());

        if(writeToFile){
            POJOToFile(personsInChargeEmployees);
            POJOListToJSONToFile(personsInChargeEmployees);
        }
    }

    private void POJOToFile(List<PersonInChargeAllEmployees> pEmps) throws IOException {
        FileWriter writer = new FileWriter(POJOFileName);
        long count = pEmps.stream().count();
        for (PersonInChargeAllEmployees person : pEmps) {
            writer.write(person + System.lineSeparator());
        }
        writer.write("Total antal poster: " + count + System.lineSeparator());
        writer.close();
    }

    private void POJOListToJSONToFile(List<PersonInChargeAllEmployees> pEmps) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(pEmps);
        JSONArray jArr = new JSONArray(arrayToJson);

        // 1. Convert List of person objects to JSON :");
        System.out.println(arrayToJson);
        System.out.println("Total antal poster (json objekt): " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(arrayToJson);
        jsonWriter.write("\nTotal antal poster (json objekt): " + jArr.length() + System.lineSeparator());
        jsonWriter.close();
    }
}
