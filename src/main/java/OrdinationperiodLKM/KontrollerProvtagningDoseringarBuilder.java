package OrdinationperiodLKM;

import OrdinationperiodLKM.KontrollerProvtagningDoseringar;
import PDF.Ordination.PDFKontrollerProvtagningDoseringar;
import Person.PersonInChargeException;
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

public class KontrollerProvtagningDoseringarBuilder {
    private final String sqlScriptFilePathKontrollerProvtagningDoseringar = "src/resource/sql/MV/KontrollerProvtagningDoseringar.sql";      // All titles, creating a total of employees in charge
    private Connection myConnection = null;

    private final String POJOFileName = "temp/matvarde/kontrollerProvtagningDoseringar.txt";
    private final String JSONFileName = "temp/matvarde/kontrollerProvtagningDoseringar.json";

    List<KontrollerProvtagningDoseringar> kontrollerProvtagningDoseringarList;

    public KontrollerProvtagningDoseringarBuilder(final Connection con) {
        this.kontrollerProvtagningDoseringarList = new ArrayList<>();
        this.myConnection = con;
    }

    public void buildKontrollerProvtagningDoseringar(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, IOException, PersonInChargeException, KontrollerProvtagningDoseringarException {
        Path file = Path.of(sqlScriptFilePathKontrollerProvtagningDoseringar);

        String sqlStatement = Files.readString(file);

        PreparedStatement selKontrProvtDos = myConnection.prepareStatement(sqlStatement);
        selKontrProvtDos.setString(1, centreId);
        selKontrProvtDos.setString(2, regpatSSN);

        ResultSet rs = selKontrProvtDos.executeQuery();

        while (rs.next()) {
            KontrollerProvtagningDoseringar kontrollerProvtagningDoseringar = new KontrollerProvtagningDoseringar(
                    rs.getInt("OID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("SSN"),
                    rs.getShort("SSN_TYPE"),
                    rs.getString("CPPAL_TXT"),
                    rs.getString("PAL_FIRSTNAME"),
                    rs.getString("PAL_LASTNAME"),
                    rs.getShort("PAL_TITLE"),
                    rs.getDate("DATE_NEXT_VISIT"),

                    rs.getInt("INR_INTERVAL_ID"),
                    rs.getDate("ORDINATIONDATE"),
                    rs.getFloat("MONDAY_DOSE"),
                    rs.getFloat("TUESDAY_DOSE"),
                    rs.getFloat("WEDNSDAY_DOSE"),
                    rs.getFloat("THURSDAY_DOSE"),
                    rs.getFloat("FRIDAY_DOSE"),
                    rs.getFloat("SATURDAY_DOSE"),
                    rs.getFloat("SUNDAY_DOSE"),
                    rs.getString("COMMENT_DOSE"),

                    rs.getString("MEDICIN_TXT"),
                    rs.getShort("DOSE_MODE"),

                    rs.getString("REDUCED_TYPE_TXT"),
                    rs.getFloat("MONDAY_DOSE_REDUCED"),
                    rs.getFloat("TUESDAY_DOSE_REDUCED"),
                    rs.getFloat("WEDNSDAY_DOSE_REDUCED"),
                    rs.getFloat("THURSDAY_DOSE_REDUCED"),
                    rs.getFloat("FRIDAY_DOSE_REDUCED"),
                    rs.getFloat("SATURDAY_DOSE_REDUCED"),
                    rs.getFloat("SUNDAY_DOSE_REDUCED"),

                    rs.getString("REDUCED_COMMENT"),
                    rs.getFloat("INRVALUE"),
                    rs.getDate("INRDATE"),
                    rs.getString("LABORATORY_ID"),
                    rs.getString("MEDICINETYPE_TXT"),
                    rs.getString("INRMETHOD_TXT"),
                    rs.getString("ANALYSIS_PATHOL"),
                    rs.getInt("LABRESULTID_CREATININ"),
                    rs.getDate("PLANEDDATE_CREATININ"),
                    rs.getDate("TESTDATE_CREATININ"),

                    rs.getShort("CREATININ"),
                    rs.getShort("EGFR"),
                    rs.getDate("FOLLOWUPDATE_CREATININ"),
                    rs.getString("COMMENT_CREATININ"),
                    rs.getString("ANALYSISCODE_LAB"),
                    rs.getFloat("SAMPLEVALUE_LAB"),
                    rs.getString("SAMPLEVALUE_TEXT"),
                    rs.getString("ANALYSISCOMMENT_LAB"),
                    rs.getFloat("SYSTEMS_ORDINATION_SUGG"),
                    rs.getInt("SYSTEMS_INTERVAL_SUGG"),

                    rs.getFloat("USER_ORDINATION"),
                    rs.getInt("USER_INTERVAL")
            );
            kontrollerProvtagningDoseringarList.add(kontrollerProvtagningDoseringar);
        }

        /***  Här skapas PDF dokumentet ***/
        if(kontrollerProvtagningDoseringarList.size() > 0) {
            PDFKontrollerProvtagningDoseringar pdfKontrollerProvtagningDoseringar = new PDFKontrollerProvtagningDoseringar(kontrollerProvtagningDoseringarList);
            pdfKontrollerProvtagningDoseringar.createDosePDFDetails();
        }

        kontrollerProvtagningDoseringarList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal poster: " + kontrollerProvtagningDoseringarList.size());

        if(writeToFile){
            POJOToFile(kontrollerProvtagningDoseringarList);
            POJOListToJSONToFile(kontrollerProvtagningDoseringarList);
        }
    }

    private void POJOToFile(List<KontrollerProvtagningDoseringar> kpd) throws IOException {
        FileWriter writer = new FileWriter(POJOFileName);
        long count = kpd.stream().count();
        for (KontrollerProvtagningDoseringar kontProvDos : kpd) {
            writer.write(kontProvDos + System.lineSeparator());
        }
        writer.write("Total antal poster: " + count + System.lineSeparator());
        writer.close();
    }

    private void POJOListToJSONToFile(List<KontrollerProvtagningDoseringar> kpd) throws IOException {
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
