package Person;

import auxilliary.MappingPositions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

    private String host;
    private String uName;
    private String uPass;
    private final String sqlScriptFilePathAllTitles = "src/resource/sql/person/PersonInChargeAllEmployees.sql";      // All titles, creating a total of employees in charge
    private Connection myConnection = null;

    private String POJOFileName = "";
    private String JSONFileName = "";

    public PersonInChargeAllEmployeesBuilder(final Connection con) throws IOException, PersonInChargeException, SQLException {
        this.myConnection = con;
    }

    public void buildPersonInCharge(Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInChargeException {
        Path file = null;

        file = Path.of(sqlScriptFilePathAllTitles);
        POJOFileName = "temp/personInChargeGroupBy.txt";
        JSONFileName = "temp/personInChargeGroupBy.json";

        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();

        ResultSet rs = statement.executeQuery(sqlStatement);

        List<PersonInChargeAllTitles> personsInCharge = new ArrayList<>();
        MappingPositions map = new MappingPositions();
        while (rs.next()) {
            if(rs.getString("titel") == null)
                continue;
            String befattningskod = map.getBefattning(rs.getString("titel"));
            PersonInChargeAllTitles personCharge = new PersonInChargeAllTitles(
                    rs.getString("titel"),
                    befattningskod
            );
            personsInCharge.add(personCharge);
        }

        personsInCharge.stream()
                .forEach(System.out::println);

        if(writeToFile){
            extractToFile(personsInCharge);
            POJOListToJSONToFile(personsInCharge);
        }
    }


    private void extractToFile(List<PersonInChargeAllTitles> personsInCharge) throws IOException {
        FileWriter writer = new FileWriter(POJOFileName);
        for (PersonInChargeAllTitles person : personsInCharge) {
            writer.write(person + System.lineSeparator());
        }
        writer.close();
    }

    private void POJOListToJSONToFile(List<PersonInChargeAllTitles> personsInCharge) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(personsInCharge);
        // 1. Convert List of person objects to JSON :");
        System.out.println(arrayToJson);

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(arrayToJson);
        jsonWriter.close();
    }
}
