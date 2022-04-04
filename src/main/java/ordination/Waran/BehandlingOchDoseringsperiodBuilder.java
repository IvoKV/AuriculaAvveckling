package ordination.Waran;

import DBSource.DBConnection;
import Person.PersonInitializationException;
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

public class BehandlingOchDoseringsperiodBuilder {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/behandlingOchDoseringsperiod.txt";
    private String JSONFileName = "temp/ordination/behandlingOchDoseringsperiod.json";
    private Connection myConnection = null;

    private long countObjChars;
    private long behandlingDoseringsperiodCount;

    public BehandlingOchDoseringsperiodBuilder(final Connection con) {
        this.myConnection = con;
    }

    public void buildBehandlingOchDosering(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, IOException {
        ResultSet rsDoseringsperiod = null;

        List<BehandlingOchDoseringsperiod> behandlingOchDoseringsperiods = new ArrayList<>();

        if(regpatSSN.length() > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/BehandlingOchDoseringsperiodOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectDosBeh = myConnection.prepareStatement(sqlStatement);
            selectDosBeh.setString(1, centreId);
            selectDosBeh.setString(2, regpatSSN);
            rsDoseringsperiod = selectDosBeh.executeQuery();
        }
        else if(regpatSSN.length() == 0){
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/BehandlingOchDoseringsperiodAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectDosBeh = myConnection.prepareStatement(sqlStatement);
            selectDosBeh.setString(1, centreId);
            rsDoseringsperiod = selectDosBeh.executeQuery();
        }
        else{
            System.out.println("Verification of SSN: Wrong format. Program abort.");
            System.exit(0);
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
                    rsDoseringsperiod.getString(9),                             // MEDICINE
                    rsDoseringsperiod.getString(10),                            // DOSE_MODE
                    rsDoseringsperiod.getString(11),                            // PREFERED_INTERVAL_START
                    rsDoseringsperiod.getString(12),                            // PREFERED_INTERVAL_END
                    rsDoseringsperiod.getString(13),                            // INRMETHOD
                    rsDoseringsperiod.getDouble(14),                            // WEIGHT
                    rsDoseringsperiod.getDate(15),                              // WEIGHTDATE
                    rsDoseringsperiod.getString(16),                            // DOSE
                    rsDoseringsperiod.getDate("FROMDATE"),                      // FROMDATE
                    rsDoseringsperiod.getDate(18),                              // TODATE
                    rsDoseringsperiod.getDate(19),                              // STARTDATE
                    rsDoseringsperiod.getString(20),                            // PERIOD_LENGTH
                    rsDoseringsperiod.getString(21),                            // LENGTHCOMMENT
                    rsDoseringsperiod.getDate("op.enddate"),                    // op.enddate       // ENDDATE
                    rsDoseringsperiod.getString("REASON_STOPPED")               // op.reason_stopped // REASON_STOPPED
            );
            behandlingOchDoseringsperiods.add(bdp);
        }
        int listSize = behandlingOchDoseringsperiods.size();
        myConnection.close();

        behandlingOchDoseringsperiods.stream()
                .forEach(System.out::println);
        System.out.println("Total antal doseringsperiodposter: " + listSize);

        if(writeToFile){
            writePOJOToFile(behandlingOchDoseringsperiods, regpatSSN);
            POJOListToJSONToFile(behandlingOchDoseringsperiods);
        }
    }

    /*
    // must be accessible for test class
    public Connection getMyConnection() throws SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection(host, uName, uPass);
        return dbConnection.createConnection();
    }

     */

    private void writePOJOToFile(List<BehandlingOchDoseringsperiod> ordp, String regpat) throws IOException {
        if(regpat.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Behandlings- och doseringsposter för patient:\n");
        for (BehandlingOchDoseringsperiod dosbeh : ordp) {
            pojoWriter.write(dosbeh + System.lineSeparator());
        }
        pojoWriter.write("Total antal ordinationer: " + ordp.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<BehandlingOchDoseringsperiod> behandlingOchDoseringsperiods) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(behandlingOchDoseringsperiods);
        JSONArray jArr = new JSONArray(listToJson);

        // Convert List of person objects to JSON :");
        System.out.println(listToJson);
        System.out.println("Antal poster: "+ jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nTotal antal Behandling och Doseringsposter: " + jArr.length() + System.lineSeparator());
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
