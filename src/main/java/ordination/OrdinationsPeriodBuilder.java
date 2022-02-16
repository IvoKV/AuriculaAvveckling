package ordination;

import DBSource.DBConnection;
import Person.PersonInitializationException;
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

public class OrdinationsPeriodBuilder {

    private String host = null;
    private String uName = null;
    private String uPass = null;

    private final String sqlScriptFilePath = "src/resource/sql/ordination/Patient_Ordinationsperiod.sql";
    private final String POJOFileName = "temp/ordination/ordinationsperiod.txt";
    private final String JSONFileName = "temp/ordination/ordinationsperiod.json";
    private Connection myConnection = null;

    public OrdinationsPeriodBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException, IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildPersonPatient(Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsInitializeException {

        Path file = Path.of(sqlScriptFilePath);
        myConnection = getConnection();

        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();
        ResultSet rs = statement.executeQuery(sqlStatement);

        //PreparedStatement preparedStatement = null;
        //preparedStatement = myConnection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        List<Ordinationsperiod> ordinationsperiodList = new ArrayList<>();

        while (rs.next()) {
            Ordinationsperiod op = new Ordinationsperiod(
                    rs.getString("pid"),
                    rs.getInt(2));

            ordinationsperiodList.add(op);
        }
        myConnection.close();
        //listCount = personPatients.size();

        ordinationsperiodList.stream()
                .forEach(System.out::println);

        if(writeToFile){
            writePOJOToFile(ordinationsperiodList);
            POJOListToJSONToFile(ordinationsperiodList);
        }
        //System.out.println("Antal poster: " + listCount);
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<Ordinationsperiod> ordp) throws IOException {
        FileWriter pojoWriter = new FileWriter(POJOFileName);
        for (Ordinationsperiod op : ordp) {
            pojoWriter.write(op + System.lineSeparator());
        }
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<Ordinationsperiod> ordp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(ordp);
        // 1. Convert List of person objects to JSON :");
        System.out.println(listToJson);

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.close();
    }


}
