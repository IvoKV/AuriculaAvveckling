package ordination.Matvarde;

import DBSource.DBConnection;
import Person.PersonInitializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

public class LMHBuilder {
    private String host = null;
    private String uName = null;
    private String uPass = null;

    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/matvarde/LMH.txt";
    private String JSONFileName = "temp/matvarde/LMH.json";

    private Connection myConnection = null;
    private int totalOfLMH = 0;

    private long countObjChars;
    private long lmhListCount;

    public LMHBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildLMH(String centreId, int regpatId, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException {
        ResultSet rsLMH = null;
        myConnection = getConnection();
        List<LMH> lmhList = new ArrayList<>();

        if(regpatId > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/matvarde/LMHOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectLMH = myConnection.prepareStatement(sqlStatement);
            selectLMH.setString(1, centreId);
            selectLMH.setInt(2, regpatId);
            rsLMH = selectLMH.executeQuery();
        }
        else{
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/matvarde/LMHAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsLMH = selectOrdinationWaran.executeQuery();
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
                    rsLMH.getShort("LMHTYPE"),         // date
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
            writePOJOToFile(lmhList, regpatId);
            POJOListToJSONToFile(lmhList, regpatId);
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<LMH> ordp, int regpat) throws IOException {

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
        for (LMH lmh : ordp) {
            pojoWriter.write(lmh + System.lineSeparator());
        }
        //pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<LMH> lmhlist, int regpat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(lmhlist);
        // Convert List of person objects to JSON :");
        System.out.println(listToJson);

        char search = '{';
        countObjChars = listToJson.chars().filter(ch -> ch == search).count();
        lmhListCount = lmhlist.size();

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nTotal antal hemorrhages poster: " + countObjChars + System.lineSeparator());
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
