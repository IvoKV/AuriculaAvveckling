package OrdinationperiodLKM.Matvarde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;

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
    private String POJOFileName = "temp/matvarde/lab_INR.txt";
    private String JSONFileName = "temp/matvarde/lab_INR.json";

    private Connection myConnection = null;
    private int totalOfLabInr = 0;

    public LabInrBuilder(final Connection con) {
        this.myConnection = con;
    }

    public void buildLabINR(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, IOException {
        ResultSet rsLabInr = null;

        List<LabInr> labinrList = new ArrayList<>();

        if(regpatSSN.length() > 0) {
            // ONE regpatId
            String sqlScriptFilePath = "src/resource/sql/matvarde/Lab_INROne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectLabInr = myConnection.prepareStatement(sqlStatement);
            selectLabInr.setString(1, centreId);
            selectLabInr.setString(2, regpatSSN);
            rsLabInr = selectLabInr.executeQuery();
        }
        else if(regpatSSN.length() == 0){
            // ALL regpatId
            String sqlScriptFilePath = "src/resource/sql/matvarde/Lab_INRAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsLabInr = selectOrdinationWaran.executeQuery();
        }
        else {
            System.out.println("Verification of SSN: Wrong format. Program abort.");
            System.exit(0);
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
                    // todo: ANALYSIS COMMENT HAR HTML tecken, bör tas bort
                    rsLabInr.getString("ANALYSIS_COMMENT")                     // VALVE_MALFUNCTION_TXT     // tinyint
            );
            labinrList.add(labInr);
        }
        int listSize = labinrList.size();
        myConnection.close();

        labinrList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal lab-INR poster: " + listSize);

        if(writeToFile){
            POJOToFile(labinrList, regpatSSN);
            POJOListToJSONToFile(labinrList);
        }
    }
/*
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

 */

    private void POJOToFile(List<LabInr> labInrs, String regpatSSN) throws IOException {
        if(regpatSSN.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Lab INR för patient:\n");
        for (LabInr labInr : labInrs) {
            pojoWriter.write(labInr + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + labInrs.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<LabInr> labInrs) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(labInrs);
        JSONArray jArr = new JSONArray(listToJson);

        System.out.println(listToJson);
        System.out.println("Antal poster: " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nAntal poster: " + jArr.length() + System.lineSeparator());
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
