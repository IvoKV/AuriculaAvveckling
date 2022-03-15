package ordination.Waran;

import DBSource.DBConnection;
import Person.PersonInitializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

public class ComplicationBuilder {
    private String host = null;
    private String uName = null;
    private String uPass = null;
    private Connection myConnection = null;

    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/complicationWaran.txt";
    private String JSONFileName = "temp/ordination/complicationWaran.json";

    private long countObjChars;
    private long complicationsListCount;

    public ComplicationBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildComplication(String centreId, int regpatId, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException {
        ResultSet rsComplication = null;
        myConnection = getConnection();
        List<Complication> complications = new ArrayList<>();

        if(regpatId > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/ComplicationWaranOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setInt(2, regpatId);
            rsComplication = selectOrdinationWaran.executeQuery();
        }
        else{
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/ComplicationWaranAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsComplication = selectOrdinationWaran.executeQuery();
        }

        while (rsComplication.next()) {
            Complication comp = new Complication(
                    rsComplication.getString("c.id"),                   // varchar(30)
                    rsComplication.getInt("p.pid"),                     // int unsigned
                    rsComplication.getString("p.SSN"),                  // varchar(30)
                    rsComplication.getShort("p.SSN_TYPE"),              // tinyiny
                    rsComplication.getString("rp.FIRSTNAME"),           // varchar(60)
                    rsComplication.getString("rp.LASTNAME"),            // varchar(60)
                    rsComplication.getShort("pal.PAL_TITLE"),           // tinyint
                    rsComplication.getString("pal.PAL_FIRSTNAME"),      // varchar(40)
                    rsComplication.getString("pal.PAL_LASTNAME"),       // varchar(40)
                    rsComplication.getString(9),                        // complex exists
                    rsComplication.getString(10),                        // bleeding
                    rsComplication.getString(11),                       // trombosis
                    rsComplication.getInt(12),                          // daysOfCare
                    rsComplication.getBigDecimal(13),                   // PKINR
                    rsComplication.getString(14)                       // status

            );
            complications.add(comp);
        }
        int listSize = complications.size();
        myConnection.close();

        complications.stream()
                .forEach(System.out::println);
        System.out.println("Total antal complication popster: " + listSize);

        if(writeToFile){
            writePOJOToFile(complications, regpatId);
            POJOListToJSONToFile(complications, regpatId);
        }
    }

    // must be accessible for test class
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<Complication> ordp, int regpat) throws IOException {

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
        for (Complication cmp : ordp) {
            pojoWriter.write(cmp + System.lineSeparator());
        }
        //pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<Complication> hemorrhagesList, int regpat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(hemorrhagesList);
        // Convert List of person objects to JSON :");
        System.out.println(listToJson);

        char search = '{';
        countObjChars = listToJson.chars().filter(ch -> ch == search).count();
        complicationsListCount = hemorrhagesList.size();

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
