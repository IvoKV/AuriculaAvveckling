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

public class MatvardeBuilder {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/matvarde/matvarde.txt";
    private String JSONFileName = "temp/matvarde/matvarde.json";

    private Connection myConnection = null;

    private long countObjChars;
    private long matvardeListCount;

    public MatvardeBuilder(final Connection con) {
        this.myConnection = con;
    }

    public void buildMatvarde(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, MatvardeInitializationException {
        ResultSet rsMatvarde = null;
        List<Matvarde> matvardes = new ArrayList<>();

        if(regpatSSN.length() > 0) {
            // ONE regpatSSN
            sqlScriptFilePath = "src/resource/sql/matvarde/MatvardeOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setString(2, regpatSSN);
            rsMatvarde = selectOrdinationWaran.executeQuery();
        }
        else if(regpatSSN.length() == 0){
            // ALL regpatSSN
            sqlScriptFilePath = "src/resource/sql/matvarde/MatvardeAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsMatvarde = selectOrdinationWaran.executeQuery();
        }
        else {
            System.out.println("Verification of SSN: Wrong format. Program abort.");
            System.exit(0);
        }

        while (rsMatvarde.next()) {
            Matvarde comp = new Matvarde(
                    rsMatvarde.getString("c.id"),                   // varchar(30)
                    rsMatvarde.getInt("p.pid"),                     // int unsigned
                    rsMatvarde.getString("p.SSN"),                  // varchar(30)
                    rsMatvarde.getShort("p.SSN_TYPE"),              // tinyiny
                    rsMatvarde.getString("rp.FIRSTNAME"),           // varchar(60)
                    rsMatvarde.getString("rp.LASTNAME"),            // varchar(60)
                    rsMatvarde.getShort("pal.PAL_TITLE"),           // tinyint
                    rsMatvarde.getString("pal.PAL_FIRSTNAME"),      // varchar(40)
                    rsMatvarde.getString("pal.PAL_LASTNAME"),       // varchar(40)
                    rsMatvarde.getShort("CREATININ"),                        // Creatinin
                    rsMatvarde.getDate("TESTDATE"),                         // Testdate
                    rsMatvarde.getString(11),                       // specimenComment
                    rsMatvarde.getString(12),                       // remissComment
                    rsMatvarde.getString(13)                        // analysisComment
            );
            matvardes.add(comp);
        }
        int listSize = matvardes.size();
        //myConnection.close();

        matvardes.stream()
                .forEach(System.out::println);
        System.out.println("Total antal mätvärde popster: " + listSize);

        if(writeToFile){
            POJOToFile(matvardes, regpatSSN);
            POJOListToJSONToFile(matvardes);
        }
    }

    /*
    // must be accessible for test class
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

     */

    private void POJOToFile(List<Matvarde> matvardes, String regpatSSN) throws IOException {
        if(regpatSSN.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Mätvärde för patient:\n");
        for (Matvarde matvarde : matvardes) {
            pojoWriter.write(matvarde + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + matvardes.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<Matvarde> matvardeList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(matvardeList);
        JSONArray jArr = new JSONArray(listToJson);

        System.out.println(listToJson);
        System.out.println("Total antal poster: " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nTotal antal poster: " + jArr.length() + System.lineSeparator());
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
