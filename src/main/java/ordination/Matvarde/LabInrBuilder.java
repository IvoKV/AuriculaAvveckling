package ordination.Matvarde;

import DBSource.DBConnection;
import Person.PersonInitializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ordination.Waran.OrdinationsperiodIndikationer;
import ordination.Waran.OrdinationsperiodInitializeException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabInrBuilder {
    private String host = null;
    private String uName = null;
    private String uPass = null;

    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/matvarde/lab_INR.txt";
    private String JSONFileName = "temp/matvarde/lab_INR.json";

    private Connection myConnection = null;
    private int totalOfLabInr = 0;

    public LabInrBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildLabINR(String centreId, int regpatId, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException {
        ResultSet rsLabInr = null;
        myConnection = getConnection();
        List<LabInr> labinrList = new ArrayList<>();

        if(regpatId > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/matvarde/Lab_INROne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setInt(2, regpatId);
            rsLabInr = selectOrdinationWaran.executeQuery();
        }
        else{
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/matvarde/Lab_INRAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsLabInr = selectOrdinationWaran.executeQuery();
        }

        while (rsLabInr.next()) {
            LabInr labInr = new LabInr(
                    rsLabInr.getString("c.id"),               // varchar(30)
                    rsLabInr.getInt("p.pid"),                 // int unsigned
                    rsLabInr.getString("p.SSN"),              // varchar(30)
                    rsLabInr.getShort("p.SSN_TYPE"),         // tinyiny
                    rsLabInr.getString("rp.FIRSTNAME"),       // varchar(60)
                    rsLabInr.getString("rp.LASTNAME"),        // varchar(60)
                    rsLabInr.getShort("pal.PAL_TITLE"),      // tinyint
                    rsLabInr.getString("pal.PAL_FIRSTNAME"),  // varchar(40)
                    rsLabInr.getString("pal.PAL_LASTNAME"),                             // varchar(40)
                    rsLabInr.getDouble("INRVALUE"),         // date
                    rsLabInr.getDate("INRDATE"),    // varchar(12000)
                    rsLabInr.getString("LABREM_COMMENT"),                             // MEDICIN_TXT       // tinyint
                    rsLabInr.getString("SPECIMEN_COMMENT"),                             // ATRIAL_FIB_TXT    // tinyint
                    rsLabInr.getString("ANALYSIS_COMMENT")                     // VALVE_MALFUNCTION_TXT     // tinyint
            );
            labinrList.add(labInr);
        }
        int listSize = labinrList.size();
        myConnection.close();

        labinrList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal lab poster: " + totalOfLabInr);

        if(writeToFile){
            writePOJOToFile(labinrList, regpatId);
            POJOListToJSONToFile(labinrList, regpatId);
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<LabInr> ordp, int regpat) throws IOException {

        if(regpat > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Lab INR för patient:\n");
        for (LabInr labInr : ordp) {
            pojoWriter.write(labInr + System.lineSeparator());
        }
        //pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<LabInr> ordp, int regpat) throws IOException {
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
