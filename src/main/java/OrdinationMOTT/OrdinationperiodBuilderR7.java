package OrdinationMOTT;

import Mott.JournalcommentBuilderR7;
import Mott.JournalcommentException;
import PDF.Mott.PDFJournalcommentR7;
import PDF.Ordination.PDFOrdinationperiod;
import PDF.Ordination.PDFOrdinationperiodR7;
import Person.PatientGeneralDataException;
import auxilliary.GeneralBefattningReadJSONException;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrdinationperiodBuilderR7 {
    private final String sqlScriptFilePathOrdinationperiod = "src/resource/sql/ordination/Ordinationperiod.sql";      // All titles, creating a total of employees in charge
    private Connection myConnection = null;

    private final String POJOFileName = "temp/ordination/Ordination.txt";
    private final String JSONFileName = "temp/ordination/Ordination.json";

    List<OrdinationperiodR7> ordinationperiodListR7;

    public OrdinationperiodBuilderR7(final Connection con) {
        this.myConnection = con;
        this.ordinationperiodListR7 = new ArrayList<>();
    }

    public void buildOrdinationperiodR7(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, IOException, OrdinationperiodException, PatientGeneralDataException, GeneralBefattningReadJSONException, JournalcommentException {
        Path file = Path.of(sqlScriptFilePathOrdinationperiod);

        String sqlStatement = Files.readString(file);

        PreparedStatement selOrdperiod = myConnection.prepareStatement(sqlStatement);
        selOrdperiod.setString(1, centreId);
        selOrdperiod.setString(2, regpatSSN);

        ResultSet rs = selOrdperiod.executeQuery();

        while (rs.next()) {
            OrdinationperiodR7 ordinationperiod = new OrdinationperiodR7(
                    rs.getString("CENTREID"),
                    rs.getInt("OID"),
                    rs.getInt("PID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),

                    rs.getString("SSN"),
                    rs.getShort("SSN_TYPE"),
                    rs.getString("MEDICINETYPE_TXT"),
                    rs.getString("ATRIALFIB_TXT"),
                    rs.getString("VALVEMALFUNCTION_TXT"),

                    rs.getString("VENOUSTROMB_TXT"),
                    rs.getString("OTHER_TXT"),
                    rs.getString("OTHERCHILDINDICATION_TXT"),
                    rs.getString("DCCONVERSION_TXT"),
                    rs.getShort("DCTHERAPYDROPOUT"),

                    rs.getString("PERIODLENGTH_TXT"),
                    rs.getString("MEDICIN_TXT"),
                    rs.getString("DOSEMODE_TXT"),
                    rs.getString("CREAINTERVALFIRSTYEAR_TXT"),
                    rs.getString("CREAINTERVAL_TXT"),

                    rs.getDate("STARTDATE"),
                    rs.getDate("ENDDATE"),
                    rs.getDate("CREACOMPLATESTCREATED"),
                    rs.getInt("CREACOMPLFOLYEAR"),
                    rs.getString("CREACOMPLFIRSTYEAR_TXT"),

                    rs.getString("REASONSTOPPED_TXT"),
                    rs.getDate("CONTINUELATECHECK"),
                    rs.getString("CREATEDBY"),
                    rs.getString("UPDATEDBY"),
                    rs.getString("LENGTHCOMMENT"),

                    rs.getString("INRMETHOD_TXT"),
                    rs.getShort("COMPLFOLYEAR"),
                    rs.getFloat("WEIGHT"),
                    rs.getDate("WEIGHTDATE"),
                    rs.getShort("ORDINATION_LMH"),
                    rs.getInt("LMH_DOSE"),
                    rs.getFloat("PREFERED_INTERVAL_START"),
                    rs.getFloat("PREFERED_INTERVAL_END")
            );
            ordinationperiodListR7.add(ordinationperiod);
        }

        Set<Integer> oidList = new HashSet<>();
        for(var item : ordinationperiodListR7){
             oidList.add( item.getOid());
        }

        /***  Här skapas PDF dokumentet ***/
        String patFirstName = ordinationperiodListR7.get(0).getPatFirstName();
        String patLastName = ordinationperiodListR7.get(0).getPatLastName();
        Short ssnType = ordinationperiodListR7.get(0).getSsnType();
        if(ordinationperiodListR7.size() > 0) {
            PDFOrdinationperiodR7 pdfOrdinationperiodR7 = new PDFOrdinationperiodR7(ordinationperiodListR7);
            pdfOrdinationperiodR7.createOrdinationperiodDetails(centreId, regpatSSN);
        }
        /*** Här skapas den tillhörande Journalcomment, 1 per Ordinationperiod ***/
        for(var oidItem : oidList){
            JournalcommentBuilderR7 journalcommentR7Builder = new JournalcommentBuilderR7(myConnection, centreId, oidItem, regpatSSN, ssnType, patFirstName, patLastName);
            journalcommentR7Builder.buildJournalcomment();
        }

        ordinationperiodListR7.stream()
                .forEach(System.out::println);
        System.out.println("Total antal poster: " + ordinationperiodListR7.size());

        if(writeToFile){
            POJOToFile(ordinationperiodListR7);
            POJOListToJSONToFile(ordinationperiodListR7);
        }
    }

    private void POJOToFile(List<OrdinationperiodR7> kpd) throws IOException {
        FileWriter writer = new FileWriter(POJOFileName);
        long count = kpd.stream().count();
        for (OrdinationperiodR7 ordProv : kpd) {
            writer.write(ordProv + System.lineSeparator());
        }
        writer.write("Total antal poster: " + count + System.lineSeparator());
        writer.close();
    }

    private void POJOListToJSONToFile(List<OrdinationperiodR7> kpd) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(kpd);
        JSONArray jArr = new JSONArray(arrayToJson);

        // 1. Convert List of person objects to JSON :");
        System.out.println(arrayToJson);
        System.out.println("Total antal poster (json objekt): " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(arrayToJson);
        jsonWriter.write("\nTotal antal poster (json objekt): " + jArr.length() + System.lineSeparator());
        jsonWriter.close();
    }
}



