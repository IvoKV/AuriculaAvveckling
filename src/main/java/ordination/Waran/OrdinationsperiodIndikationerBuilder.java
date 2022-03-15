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

    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/ordinationsperiodWaran.txt";
    private String JSONFileName = "temp/ordination/ordinationsperiodWaran.json";
    private Connection myConnection = null;
    private int totalOfOrdinationer = 0;

    public OrdinationsperiodIndikationerBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildOrdinationPeriodIndikation(String centreId, int regpatId, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException {
        ResultSet rsOrdinationer = null;
        myConnection = getConnection();
        List<OrdinationsperiodIndikationer> ordinationsperiodIndikationerList = new ArrayList<>();

        if(regpatId > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/OrdinationsperiodWaranOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setInt(2, regpatId);
            rsOrdinationer = selectOrdinationWaran.executeQuery();
        }
        else{
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/OrdinationsperiodWaranAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsOrdinationer = selectOrdinationWaran.executeQuery();
        }

        while (rsOrdinationer.next()) {
            OrdinationsperiodIndikationer op = new OrdinationsperiodIndikationer(
                    rsOrdinationer.getString("c.id"),               // varchar(30)
                    rsOrdinationer.getInt("p.pid"),                 // int unsigned
                    rsOrdinationer.getString("p.SSN"),              // varchar(30)
                    rsOrdinationer.getShort("p.SSN_TYPE"),         // tinyiny
                    rsOrdinationer.getString("rp.FIRSTNAME"),       // varchar(60)
                    rsOrdinationer.getString("rp.LASTNAME"),        // varchar(60)
                    rsOrdinationer.getShort("pal.PAL_TITLE"),      // tinyint
                    rsOrdinationer.getString("pal.PAL_FIRSTNAME"),  // varchar(40)
                    rsOrdinationer.getString("pal.PAL_LASTNAME"),   // varchar(40)
                    rsOrdinationer.getDate("op.STARTDATE"),         // date
                    rsOrdinationer.getString("opat.COMMENT_ORDPATIENT"),    // varchar(12000)
                    rsOrdinationer.getString(11),                             // MEDICIN_TXT       // tinyint
                    rsOrdinationer.getString(12),                             // ATRIAL_FIB_TXT    // tinyint
                    rsOrdinationer.getString(13),                     // VALVE_MALFUNCTION_TXT     // tinyint
                    rsOrdinationer.getString(14),                  // VENOUS_TROMB                 // tinyint
                    rsOrdinationer.getString(15),                 // INDICATION_OTHER_TXT          // tinyint
                    rsOrdinationer.getString(16),                 // OTHERCHILDINDICATION_TXT       // tinyint
                    rsOrdinationer.getString(17),                 // DCCONVERSION_TXT              // tinyint
                    rsOrdinationer.getString(18),                 // tinyint
                    rsOrdinationer.getString("null.LMH_KLAFFEL")                    // lmh_KLAFFEL
            );
            ordinationsperiodIndikationerList.add(op);
        }
        int listSize = ordinationsperiodIndikationerList.size();
        myConnection.close();

        ordinationsperiodIndikationerList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal ordinationer: " + totalOfOrdinationer);

        if(writeToFile){
            writePOJOToFile(ordinationsperiodIndikationerList, regpatId);
            POJOListToJSONToFile(ordinationsperiodIndikationerList, regpatId);
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<OrdinationsperiodIndikationer> ordp, int regpat) throws IOException {

        if(regpat > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Ordinationstillfälle för patient:\n");
        for (OrdinationsperiodIndikationer op : ordp) {
            pojoWriter.write(op + System.lineSeparator());
        }
        //pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<OrdinationsperiodIndikationer> ordp, int regpat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(ordp);
        // Convert List of person objects to JSON :");
        System.out.println(listToJson);

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.close();
    }

    private String insertString(String original, String mode){
        StringBuffer outfile = new StringBuffer(original);
        int indexPos = outfile.lastIndexOf(".");
        if(mode == "One")
            return outfile.insert(indexPos, "One-Patient").toString();
        else
            return outfile.insert(indexPos, "All-Patients").toString();
    }
}
