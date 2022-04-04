package ordination.Waran;

import Person.PersonInitializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdinationsperiodIndikationerBuilder {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/ordinationsperiodWaran.txt";
    private String JSONFileName = "temp/ordination/ordinationsperiodWaran.json";
    private Connection myConnection = null;
    private int totalOfOrdinationer = 0;

    public OrdinationsperiodIndikationerBuilder(final Connection con) {
        this.myConnection = con;
    }

    public void buildOrdinationPeriodIndikation(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException {
        ResultSet rsOrdinationer = null;

        List<OrdinationsperiodIndikationer> ordinationsperiodIndikationerList = new ArrayList<>();

        if(regpatSSN.length() > 0) {
            // ONE regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/OrdinationsperiodWaranOne.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            selectOrdinationWaran.setString(2, regpatSSN);
            rsOrdinationer = selectOrdinationWaran.executeQuery();
        }
        else if(regpatSSN.length() == 0){
            // ALL regpatId
            sqlScriptFilePath = "src/resource/sql/ordination/OrdinationsperiodWaranAll.sql";
            Path file = Path.of(sqlScriptFilePath);
            String sqlStatement = Files.readString(file);

            PreparedStatement selectOrdinationWaran = myConnection.prepareStatement(sqlStatement);
            selectOrdinationWaran.setString(1, centreId);
            rsOrdinationer = selectOrdinationWaran.executeQuery();
        }
        else {
            System.out.println("Verification of SSN: Wrong format. Program abort.");
            System.exit(0);
        }

        while (rsOrdinationer.next()) {
            OrdinationsperiodIndikationer op = new OrdinationsperiodIndikationer(
                    rsOrdinationer.getString("c.id"),               // varchar(30)
                    rsOrdinationer.getInt("p.pid"),                 // int unsigned
                    rsOrdinationer.getString("p.SSN"),              // varchar(30)
                    rsOrdinationer.getShort("p.SSN_TYPE"),         // tinyiny
                    rsOrdinationer.getString("rp.FIRSTNAME"),       // varchar(60)
                    rsOrdinationer.getString("rp.LASTNAME"),        // varchar(60)
                    rsOrdinationer.getShort("pal.PAL_TITLE"),      // tinyint
                    rsOrdinationer.getString("pal.PAL_FIRSTNAME"),  // varchar(40)
                    rsOrdinationer.getString("pal.PAL_LASTNAME"),   // varchar(40)
                    rsOrdinationer.getDate("op.STARTDATE"),         // date
                    rsOrdinationer.getString("opat.COMMENT_ORDPATIENT"),    // varchar(12000)
                    rsOrdinationer.getString(11),                             // MEDICIN_TXT       // tinyint
                    rsOrdinationer.getString(12),                             // ATRIAL_FIB_TXT    // tinyint
                    rsOrdinationer.getString(13),                     // VALVE_MALFUNCTION_TXT     // tinyint
                    rsOrdinationer.getString(14),                  // VENOUS_TROMB                 // tinyint
                    rsOrdinationer.getString(15),                 // INDICATION_OTHER_TXT          // tinyint
                    rsOrdinationer.getString(16),                 // OTHERCHILDINDICATION_TXT       // tinyint
                    rsOrdinationer.getString(17),                 // DCCONVERSION_TXT              // tinyint
                    rsOrdinationer.getString(18),                 // tinyint
                    rsOrdinationer.getString("null.LMH_KLAFFEL")                    // lmh_KLAFFEL
            );
            ordinationsperiodIndikationerList.add(op);
        }
        int listSize = ordinationsperiodIndikationerList.size();
        myConnection.close();

        ordinationsperiodIndikationerList.stream()
                .forEach(System.out::println);
        System.out.println("Antal poster: " + ordinationsperiodIndikationerList.size());

        if(writeToFile){
            POJOToFile(ordinationsperiodIndikationerList, regpatSSN);
            POJOListToJSONToFile(ordinationsperiodIndikationerList, regpatSSN);
        }
    }

    private void POJOToFile(List<OrdinationsperiodIndikationer> ordp, String regpatSSN) throws IOException {

        if(regpatSSN.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        }
        else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Ordinationstillfälle för patient:\n");
        for (OrdinationsperiodIndikationer op : ordp) {
            pojoWriter.write(op + System.lineSeparator());
        }
        pojoWriter.write("Total antal ordinationer: " + ordp.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<OrdinationsperiodIndikationer> ordp, String regpatSSN) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(ordp);
        JSONArray jArr = new JSONArray(arrayToJson);

        System.out.println(arrayToJson);
        System.out.println("Antal poster: " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(arrayToJson);
        jsonWriter.write("\nAntal poster: " + jArr.length());
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
