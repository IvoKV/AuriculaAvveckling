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

public class BehandlingOchDoseringsperiodBuilder {

    private String host = null;
    private String uName = null;
    private String uPass = null;

    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/behandlingOchDoseringsperiod.txt";
    private String JSONFileName = "temp/ordination/behandlingOchDoseringsperiod.json";
    private Connection myConnection = null;

    private long countObjChars;
    private long behandlingDoseringsperiodCount;

    public BehandlingOchDoseringsperiodBuilder(final String connectionFilePath) throws IOException {
        extractConnectionAttributes(connectionFilePath);
    }

    private void extractConnectionAttributes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String connectionString = Files.readString(path);
        this.host = connectionString.split(";")[0];
        this.uName = connectionString.split(";")[1];
        this.uPass = connectionString.split(";")[2];
    }

    public void buildBehandlingOchDosering(String centreId, int regpatId, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException {
        ResultSet rsDoseringsperiod = null;
        myConnection = getConnection();
        List<BehandlingOchDoseringsperiod> behandlingOchDoseringsperiods = new ArrayList<>();

        if(regpatId > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/BehandlingOchDoseringsperiodOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setInt(2, regpatId);
            rsDoseringsperiod = selectOrdinationWaran.executeQuery();
        }
        else{
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/BehandlingOchDoseringsperiodAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsDoseringsperiod = selectOrdinationWaran.executeQuery();
        }

        while (rsDoseringsperiod.next()) {
            BehandlingOchDoseringsperiod bdp = new BehandlingOchDoseringsperiod(
                    rsDoseringsperiod.getString("c.id"),               // varchar(30)
                    rsDoseringsperiod.getInt("p.pid"),                 // int unsigned
                    rsDoseringsperiod.getString("p.SSN"),              // varchar(30)
                    rsDoseringsperiod.getShort("p.SSN_TYPE"),         // tinyiny
                    rsDoseringsperiod.getString("rp.FIRSTNAME"),       // varchar(60)
                    rsDoseringsperiod.getString("rp.LASTNAME"),        // varchar(60)
                    rsDoseringsperiod.getShort("pal.PAL_TITLE"),      // tinyint
                    rsDoseringsperiod.getString("pal.PAL_FIRSTNAME"),  // varchar(40)
                    rsDoseringsperiod.getString("pal.PAL_LASTNAME"),   // varchar(40)
                    rsDoseringsperiod.getString(9),
                    rsDoseringsperiod.getString(10),    // varchar(12000)
                    rsDoseringsperiod.getString(11),                             // MEDICIN_TXT       // tinyint
                    rsDoseringsperiod.getString(12),                             // ATRIAL_FIB_TXT    // tinyint
                    rsDoseringsperiod.getString(13),                     // VALVE_MALFUNCTION_TXT     // tinyint
                    rsDoseringsperiod.getDouble(14),                  // VENOUS_TROMB                 // tinyint
                    rsDoseringsperiod.getDate(15),                 // INDICATION_OTHER_TXT          // tinyint
                    rsDoseringsperiod.getString(16),                 // OTHERCHILDINDICATION_TXT       // tinyint
                    rsDoseringsperiod.getLong(17),                 // DCCONVERSION_TXT              // tinyint
                    rsDoseringsperiod.getDate(18),                 //
                    rsDoseringsperiod.getDate(19),                 //
                    rsDoseringsperiod.getString(20),                 //
                    rsDoseringsperiod.getString(21)                 //
            );
            behandlingOchDoseringsperiods.add(bdp);
        }
        int listSize = behandlingOchDoseringsperiods.size();
        myConnection.close();

        behandlingOchDoseringsperiods.stream()
                .forEach(System.out::println);
        System.out.println("Total antal hemorrhages popster: " + listSize);

        if(writeToFile){
            writePOJOToFile(behandlingOchDoseringsperiods, regpatId);
            POJOListToJSONToFile(behandlingOchDoseringsperiods, regpatId);
        }
    }

    // must be accessible for test class
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

    private void writePOJOToFile(List<BehandlingOchDoseringsperiod> ordp, int regpat) throws IOException {

        if(regpat > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Behandlings- och doseringspost för patient:\n");
        for (BehandlingOchDoseringsperiod hmhg : ordp) {
            pojoWriter.write(hmhg + System.lineSeparator());
        }
        //pojoWriter.write("Total antal ordinationer: " + totalOfOrdinationer + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<BehandlingOchDoseringsperiod> behandlingOchDoseringsperiods, int regpat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(behandlingOchDoseringsperiods);
        // Convert List of person objects to JSON :");
        System.out.println(listToJson);

        char search = '{';
        countObjChars = listToJson.chars().filter(ch -> ch == search).count();
        behandlingDoseringsperiodCount = behandlingOchDoseringsperiods.size();

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nTotal antal Behandling och Doseringsposter: " + behandlingOchDoseringsperiods.size() + System.lineSeparator());
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
