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

        List<OrdinationsperiodIndikationer> ordinationsperiodIndikationerList = new ArrayList<>();

        while (rs.next()) {
            OrdinationsperiodIndikationer op = new OrdinationsperiodIndikationer(
                    rs.getString("c.id"),               // varchar(30)
                    rs.getInt("p.pid"),                 // int unsigned
                    rs.getString("p.SSN"),              // varchar(30)
                    rs.getShort("p.SSN_TYPE"),         // tinyiny
                    rs.getString("rp.FIRSTNAME"),       // varchar(60)
                    rs.getString("rp.LASTNAME"),        // varchar(60)
                    rs.getShort("pal.PAL_TITLE"),      // tinyint
                    rs.getString("pal.PAL_FIRSTNAME"),  // varchar(40)
                    rs.getString("pal.PAL_LASTNAME"),   // varchar(40)
                    rs.getDate("op.STARTDATE"),         // date
                    rs.getString("opat.COMMENT_ORDPATIENT"),    // varchar(12000)
                    rs.getString(11),                             // MEDICIN_TXT       // tinyint
                    rs.getString(12),                             // ATRIAL_FIB_TXT    // tinyint
                    rs.getString(13),                     // VALVE_MALFUNCTION_TXT     // tinyint
                    rs.getString(14),                  // VENOUS_TROMB                 // tinyint
                    rs.getString(15),                 // INDICATION_OTHER_TXT          // tinyint
                    rs.getString(16),                 // OTHERCHILDINDICATION_TXT       // tinyint
                    rs.getString(17),                 // DCCONVERSION_TXT              // tinyint
                    rs.getString(18)                 // tinyint
            );
            ordinationsperiodIndikationerList.add(op);
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
        pojoWriter.write("Ordinationstillfälle för patient:\n");
        for (OrdinationsperiodIndikationer op : ordp) {
            pojoWriter.write(op + System.lineSeparator());
        }
        //pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
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
