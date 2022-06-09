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

public class LMHBuilder {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/matvarde/LMH.txt";
    private String JSONFileName = "temp/matvarde/LMH.json";

    private Connection myConnection = null;
    private int totalOfLMH = 0;

    private long countObjChars;
    private long lmhListCount;

    public LMHBuilder(final Connection con) {
        this.myConnection = con;
    }

    public void buildLMH(String centreId, String regpatIdSSN, Boolean writeToFile) throws SQLException, IOException {
        ResultSet rsLMH = null;

        List<LMH> lmhList = new ArrayList<>();

        if(regpatIdSSN.length() > 0) {
            // ONE regpatIdSSN
            sqlScriptFilePath = "src/resource/sql/matvarde/LMHOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectLMH = myConnection.prepareStatement(sqlStatement);
            selectLMH.setString(1, centreId);
            selectLMH.setString(2, regpatIdSSN);
            rsLMH = selectLMH.executeQuery();
        }
        else if(regpatIdSSN.length() == 0){
            // ALL regpatIdSSN
            sqlScriptFilePath = "src/resource/sql/matvarde/LMHAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsLMH = selectOrdinationWaran.executeQuery();
        }
        else {
            System.out.println("Verification of SSN: Wrong format. Program abort.");
            System.exit(0);
        }

        while (rsLMH.next()) {
            LMH labInr = new LMH(
                    rsLMH.getString("c.id"),               // varchar(30)
                    rsLMH.getInt("p.pid"),                 // int unsigned
                    rsLMH.getString("p.SSN"),              // varchar(30)
                    rsLMH.getShort("p.SSN_TYPE"),         // tinyiny
                    rsLMH.getString("rp.FIRSTNAME"),       // varchar(60)
                    rsLMH.getString("rp.LASTNAME"),        // varchar(60)
                    rsLMH.getShort("pal.PAL_TITLE"),      // tinyint
                    rsLMH.getString("pal.PAL_FIRSTNAME"),  // varchar(40)
                    rsLMH.getString("pal.PAL_LASTNAME"),                             // varchar(40)
                    rsLMH.getString("LMH_TYPE"),         // date
                    rsLMH.getInt("DOSE"),    // varchar(12000)
                    rsLMH.getDate("FROMDATE"),                             // MEDICIN_TXT       // tinyint
                    rsLMH.getDate("TODATE")                           // ATRIAL_FIB_TXT    // tinyint
            );
            lmhList.add(labInr);
        }
        int listSize = lmhList.size();
        myConnection.close();

        lmhList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal lmh poster: " + listSize);

        if(writeToFile){
            writePOJOToFile(lmhList, regpatIdSSN);
            POJOListToJSONToFile(lmhList);
        }
    }

    /*
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

     */

    private void writePOJOToFile(List<LMH> lmhs, String regpatSSN) throws IOException {

        if(regpatSSN.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Lab LMH för patient:\n");
        for (LMH lmh : lmhs) {
            pojoWriter.write(lmh + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + lmhs.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<LMH> lmhlist) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(lmhlist);
        JSONArray jArr = new JSONArray(listToJson);

        System.out.println(listToJson);
        System.out.println("Total antal poster: " + lmhlist.size());

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
