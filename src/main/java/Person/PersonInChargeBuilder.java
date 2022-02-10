package Person;

import DBSource.DBConnection;
import auxilliary.MappingPositions;
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

public class PersonInChargeBuilder {

    private String host;
    private String uName;
    private String uPass;
    private final String sqlScriptFilePathAll = "src/resource/PersonInChargeAll.sql";      // All titles, creating a total of employees in charge
    private final String sqlScriptFilePathGroup = "src/resource/PersonInChargeGroupBy.sql";    // Set of Titles that exist in DB

    private String POJOFileName = "";
    private String JSONFileName = "";

    public PersonInChargeBuilder(String host, String uName, String uPass) {
        this.host = host;
        this.uName = uName;
        this.uPass = uPass;
    }

    public void buildPersonInCharge(Boolean writeToFile, String mode) throws SQLException, ClassNotFoundException, IOException, PersonInChargeException {
        Path file = null;
        switch (mode){
            case "Group":
                file = Path.of(sqlScriptFilePathGroup);
                POJOFileName = "temp/personInChargeGroupBy.txt";
                JSONFileName = "temp/personInChargeGroupBy.json";
                break;
            case "All":
                file = Path.of(sqlScriptFilePathAll);
                POJOFileName = "temp/personInChargeAll.txt";
                JSONFileName = "temp/personInChargeAll.json";
                break;
        }

        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        Connection myConnection = dbConnection.createConnection();

        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();

        ResultSet rs = statement.executeQuery(sqlStatement);

        List<PersonInCharge> personsInCharge = new ArrayList<>();
        MappingPositions map = new MappingPositions();
        while (rs.next()) {
            if(rs.getString("titel") == null)
                continue;
            String befattningskod = map.getBefattning(rs.getString("titel"));
            PersonInCharge personCharge = new PersonInCharge(
                    rs.getString("id"),
                    rs.getString("titel"),
                    rs.getString("groupId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
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



    private void extractToFile(List<PersonInCharge> personsInCharge) throws IOException {
        FileWriter writer = new FileWriter(POJOFileName);
        for (PersonInCharge person : personsInCharge) {
            writer.write(person + System.lineSeparator());
        }
        writer.close();
    }

    private void POJOListToJSONToFile(List<PersonInCharge> personsInCharge) throws IOException {
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
