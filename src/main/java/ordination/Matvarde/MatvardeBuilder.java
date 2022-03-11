package ordination.Matvarde;

import DBSource.DBConnection;
import Person.PersonInitializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ordination.Waran.Complication;
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

public class MatvardeBuilder {
    private String host = null;
    private String uName = null;
    private String uPass = null;
    private Connection myConnection = null;

    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/matvarde/matvarde.txt";
    private String JSONFileName = "temp/matvarde/matvarde.json";

    private long countObjChars;
    private long matvardeListCount;

    public MatvardeBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildMatvarde(String centreId, int regpatId, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, MatvardeInitializationException {
        ResultSet rsMatvarde = null;
        myConnection = getConnection();
        List<Matvarde> matvarden = new ArrayList<>();

        if(regpatId > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/matvarde/MatvardeOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setInt(2, regpatId);
            rsMatvarde = selectOrdinationWaran.executeQuery();
        }
        else{
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/matvarde/MatvardeAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsMatvarde = selectOrdinationWaran.executeQuery();
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
            matvarden.add(comp);
        }
        int listSize = matvarden.size();
        myConnection.close();

        matvarden.stream()
                .forEach(System.out::println);
        System.out.println("Total antal hemorrhages popster: " + listSize);

        if(writeToFile){
            writePOJOToFile(matvarden, regpatId);
            POJOListToJSONToFile(matvarden, regpatId);
        }
    }

    // must be accessible for test class
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<Matvarde> ordp, int regpat) throws IOException {
        if(regpat > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Mätvärde för patient:\n");
        for (Matvarde matvarde : ordp) {
            pojoWriter.write(matvarde + System.lineSeparator());
        }
        //pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<Matvarde> matvardeList, int regpat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(matvardeList);
        // Convert List of person objects to JSON :");
        System.out.println(listToJson);

        char search = '{';
        countObjChars = listToJson.chars().filter(ch -> ch == search).count();
        matvardeListCount = matvardeList.size();

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nTotal antal mätvärde poster: " + matvardeListCount + System.lineSeparator());
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
