package ordination.Waran;

import DBSource.DBConnection;
import Person.PersonInitializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import ordination.Waran.OrdinationsperiodInitializeException;
import ordination.Waran.OrdinationsperiodIndikationer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdinationsperiodIndikationerBuilder {

    private String host = null;
    private String uName = null;
    private String uPass = null;

    private final String sqlScriptFilePath = "src/resource/sql/ordination/OrdinationsperiodWaran.sql";
    private final String POJOFileName = "temp/ordination/ordinationsperiodWaran.txt";
    private final String JSONFileName = "temp/ordination/ordinationsperiodWaran.json";
    private Connection myConnection = null;
    private int totalOfOrdinationer = 0;

    public OrdinationsperiodIndikationerBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException, IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildPersonPatient(Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException {

        Path file = Path.of(sqlScriptFilePath);
        myConnection = getConnection();

        String sqlStatement = Files.readString(file);
        PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
        selectOrdinationWaran.setString(1,"11012AK");
        selectOrdinationWaran.setInt(2, 54241);
        var rs = selectOrdinationWaran.executeQuery();

//        Statement statement = myConnection.createStatement();
//        ResultSet rs = statement.executeQuery(sqlStatement);

        //PreparedStatement preparedStatement = null;
        //preparedStatement = myConnection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        List<OrdinationsperiodIndikationer> ordinationsperiodIndikationerList = new ArrayList<>();

        while (rs.next()) {
            OrdinationsperiodIndikationer op = new OrdinationsperiodIndikationer(
                    rs.getString("c.id"),
                    rs.getInt("p.pid"),
                    rs.getString("p.SSN"),
                    rs.getString("p.SSN_TYPE"),
                    rs.getString("rp.FIRSTNAME"),
                    rs.getString("rp.LASTNAME"),
                    rs.getString("pal.PAL_TITLE"),
                    rs.getString("pal.PAL_FIRSTNAME"),
                    rs.getString("pal.PAL_LASTNAME"),
                    rs.getDate("op.STARTDATE"),
                    rs.getString("opat.COMMENT_ORDPATIENT"),
                    rs.getShort(11),       // MEDICIN_TXT
                    rs.getString(12),              // ATRIAL_FIB_TXT
                    rs.getShort(13),       // VALVE_MALFUNCTION_TXT
                    rs.getShort(14),    // VENOUS_TROMB
                    rs.getShort(15),   // INDICATION_OTHER_TXT
                    rs.getShort(16),  // OTHERCHILDINDICATION_TXT
                    rs.getShort(17),   // DCCONVERSION_TXT
                    rs.getShort("op.PERIOD_LENGTH_TXT")
            );

            ordinationsperiodIndikationerList.add(op);
            totalOfOrdinationer += rs.getInt(2);
        }
        myConnection.close();

        ordinationsperiodIndikationerList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal ordinationer: " + totalOfOrdinationer);

        if(writeToFile){
            writePOJOToFile(ordinationsperiodIndikationerList);
            POJOListToJSONToFile(ordinationsperiodIndikationerList);
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<OrdinationsperiodIndikationer> ordp) throws IOException {
        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Listar patienternas antal av ordinationer:\n");
        for (OrdinationsperiodIndikationer op : ordp) {
            pojoWriter.write(op + System.lineSeparator());
        }
        pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<OrdinationsperiodIndikationer> ordp) throws IOException {
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
