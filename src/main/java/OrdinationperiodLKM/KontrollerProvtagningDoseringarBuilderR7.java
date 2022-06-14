package OrdinationperiodLKM;

import PDF.Ordination.PDFKontrollerProvtagningDoseringarR7;
import Person.PatientGeneralDataException;
import Person.PersonInChargeException;
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
import java.util.List;

public class KontrollerProvtagningDoseringarBuilderR7 {
    private final String sqlScriptFilePathKontrollerProvtagningDoseringar = "src/resource/sql/MV/KontrollerProvtagningDoseringar.sql";      // All titles, creating a total of employees in charge
    private Connection myConnection = null;

    private final String POJOFileName = "temp/matvarde/kontrollerProvtagningDoseringar.txt";
    private final String JSONFileName = "temp/matvarde/kontrollerProvtagningDoseringar.json";

    List<KontrollerProvtagningDoseringarR7> kontrollerProvtagningDoseringarR7List;

    public KontrollerProvtagningDoseringarBuilderR7(final Connection con) {
        this.kontrollerProvtagningDoseringarR7List = new ArrayList<>();
        this.myConnection = con;
    }

    public void buildKontrollerProvtagningDoseringarR7(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, IOException, PersonInChargeException, KontrollerProvtagningDoseringarException, PatientGeneralDataException, GeneralBefattningReadJSONException {
        Path file = Path.of(sqlScriptFilePathKontrollerProvtagningDoseringar);

        String sqlStatement = Files.readString(file);

        PreparedStatement selKontrProvtDos = myConnection.prepareStatement(sqlStatement);
        selKontrProvtDos.setString(1, centreId);
        selKontrProvtDos.setString(2, regpatSSN);

        ResultSet rs = selKontrProvtDos.executeQuery();

        while (rs.next()) {
            KontrollerProvtagningDoseringarR7 kontrollerProvtagningDoseringarR7 = new KontrollerProvtagningDoseringarR7(
                    rs.getInt("OID"),
                    rs.getInt("PID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("SSN"),

                    rs.getShort("SSN_TYPE"),
                    rs.getDate("DATE_NEXT_VISIT"),
                    rs.getInt("INR_INTERVAL_ID"),
                    rs.getDate("ORDINATIONDATE"),
                    rs.getDate("STARTDATE"),

                    rs.getDate("ENDDATE"),
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
                    rs.getString("LABREM_COMMENT"),
                    rs.getString("SPECIMEN_COMMENT"),
                    rs.getString("ANALYSIS_COMMENT"),

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
                    rs.getInt("USER_INTERVAL"),
                    rs.getString("OP_CREATEDBY"),

                    rs.getString("OP_UPDATEDBY"),
                    rs.getTimestamp("OP_TSCREATED"),
                    rs.getString("INR_CREATEDBY"),
                    rs.getString("INR_UPDATEDBY"),
                    rs.getTimestamp("INR_TSCREATED"),

                    rs.getString("CREA_CREATEDBY"),
                    rs.getString("CREA_UPDATEDBY"),
                    rs.getTimestamp("CREA_TSCREATED"),
                    rs.getString("LAB_CREATEDBY"),
                    rs.getString("W_CREATEDBY"),

                    rs.getString("W_UPDATEDBY"),
                    rs.getTimestamp("W_TSCREATED")
            );
            kontrollerProvtagningDoseringarR7List.add(kontrollerProvtagningDoseringarR7);
        }

        /***  Här skapas PDF dokumentet ***/
        if(kontrollerProvtagningDoseringarR7List.size() > 0) {
            PDFKontrollerProvtagningDoseringarR7 pdfKontrollerProvtagningDoseringarR7 = new PDFKontrollerProvtagningDoseringarR7(kontrollerProvtagningDoseringarR7List);
            pdfKontrollerProvtagningDoseringarR7.createKontrollerProvtagningDoseringarR7PDFDetails();
        }

        kontrollerProvtagningDoseringarR7List.stream()
                .forEach(System.out::println);
        System.out.println("Total antal poster: " + kontrollerProvtagningDoseringarR7List.size());

        if(writeToFile){
            POJOToFile(kontrollerProvtagningDoseringarR7List);
            POJOListToJSONToFile(kontrollerProvtagningDoseringarR7List);
        }
    }

    private void POJOToFile(List<KontrollerProvtagningDoseringarR7> kpd) throws IOException {
        FileWriter writer = new FileWriter(POJOFileName);
        long count = kpd.stream().count();
        for (KontrollerProvtagningDoseringarR7 kontProvDos : kpd) {
            writer.write(kontProvDos + System.lineSeparator());
        }
        writer.write("Total antal poster: " + count + System.lineSeparator());
        writer.close();
    }

    private void POJOListToJSONToFile(List<KontrollerProvtagningDoseringarR7> kpd) throws IOException {
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
