package MV;

import MV.Hemorrhages;
import OrdinationperiodLKM.Waran.OrdinationsperiodInitializeException;
import Person.PersonInitializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jcraft.jsch.JSchException;
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


public class HemorrhagesBuilder {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/hemorrhagesWaran.txt";
    private String JSONFileName = "temp/ordination/hemorrhagesWaran.json";
    private int totalHemorrhagesPoster = 0;

    private long countObjChars;
    private long hemorrhagesListCount;

    private Connection myConnection = null;

    public HemorrhagesBuilder(final Connection con) {
        this.myConnection = con;
    }

    public void buildHemorrhages(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException, JSchException {
        ResultSet rsHemorrhages = null;

        List<Hemorrhages> hemorrhagesList = new ArrayList<>();

        if(regpatSSN.length() > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/HemorrhagesWaranOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setString(2, regpatSSN);
            rsHemorrhages = selectOrdinationWaran.executeQuery();
        }
        else if (regpatSSN.length() == 0){
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/HemorrhagesWaranAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsHemorrhages = selectOrdinationWaran.executeQuery();
        }
        else{
            System.out.println("Verification of SSN: Wrong format. Program abort.");
            System.exit(0);
        }

        while (rsHemorrhages.next()) {
            Hemorrhages hmg = new Hemorrhages(
                    rsHemorrhages.getString("c.id"),               // varchar(30)
                    rsHemorrhages.getInt("p.pid"),                 // int unsigned
                    rsHemorrhages.getString("p.SSN"),              // varchar(30)
                    rsHemorrhages.getShort("p.SSN_TYPE"),         // tinyiny
                    rsHemorrhages.getString("rp.FIRSTNAME"),       // varchar(60)
                    rsHemorrhages.getString("rp.LASTNAME"),        // varchar(60)
                    rsHemorrhages.getShort("pal.PAL_TITLE"),      // tinyint
                    rsHemorrhages.getString("pal.PAL_FIRSTNAME"),  // varchar(40)
                    rsHemorrhages.getString("pal.PAL_LASTNAME"),   // varchar(40)
                    rsHemorrhages.getString(9),
                    rsHemorrhages.getString(10),    // varchar(12000)
                    rsHemorrhages.getString(11),                             // MEDICIN_TXT       // tinyint
                    rsHemorrhages.getString(12),                             // ATRIAL_FIB_TXT    // tinyint
                    rsHemorrhages.getString(13),                     // VALVE_MALFUNCTION_TXT     // tinyint
                    rsHemorrhages.getString(14),                  // VENOUS_TROMB                 // tinyint
                    rsHemorrhages.getString(15),                 // INDICATION_OTHER_TXT          // tinyint
                    rsHemorrhages.getString(16),                 // OTHERCHILDINDICATION_TXT       // tinyint
                    rsHemorrhages.getString(17),                 // DCCONVERSION_TXT              // tinyint
                    rsHemorrhages.getString(18)                 // tinyint
            );
            hemorrhagesList.add(hmg);
        }
        int listSize = hemorrhagesList.size();
        myConnection.close();

        hemorrhagesList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal popster: " + listSize);

        if(writeToFile){
        writePOJOToFile(hemorrhagesList, regpatSSN);
            POJOListToJSONToFile(hemorrhagesList);
        }
    }

    /*
    // must be accessible for test class
    // todo: modify connection in test for  db in cluster
    public Connection getMyconnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

     */

    private void writePOJOToFile(List<Hemorrhages> ordp, String regpat) throws IOException {
        if(regpat.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Hemorrhages för patient:\n");
        for (Hemorrhages hmhg : ordp) {
            pojoWriter.write(hmhg + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + ordp.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<Hemorrhages> hemorrhagesList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(hemorrhagesList);
        JSONArray jArr = new JSONArray(listToJson);

        // Convert List of person objects to JSON :");
        System.out.println(listToJson);
        System.out.println("Antal poster: " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nTotal antal hemorrhages poster: " + jArr.length() + System.lineSeparator());
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

    public boolean compareResults(){
        return countObjChars == hemorrhagesListCount;
    }
}