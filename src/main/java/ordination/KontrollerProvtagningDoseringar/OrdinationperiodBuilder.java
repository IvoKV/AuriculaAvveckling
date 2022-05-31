package ordination.KontrollerProvtagningDoseringar;

import PDF.Ordination.PDFOrdinationperiod;
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

public class OrdinationperiodBuilder {
    private final String sqlScriptFilePathOrdinationperiod = "src/resource/sql/ordination/Ordinationperiod.sql";      // All titles, creating a total of employees in charge
    private Connection myConnection = null;

    private final String POJOFileName = "temp/ordination/Ordination.txt";
    private final String JSONFileName = "temp/ordination/Ordination.json";

    List<Ordinationperiod> ordinationperiodList;

    public OrdinationperiodBuilder(final Connection con) {
        this.myConnection = con;
        this.ordinationperiodList = new ArrayList<>();
    }

    public void buildOrdinationperiod(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, IOException, PersonInChargeException, KontrollerProvtagningDoseringarException, OrdinationperiodException {
        Path file = Path.of(sqlScriptFilePathOrdinationperiod);

        String sqlStatement = Files.readString(file);

        PreparedStatement selOrdperiod = myConnection.prepareStatement(sqlStatement);
        selOrdperiod.setString(1, centreId);
        selOrdperiod.setString(2, regpatSSN);

        ResultSet rs = selOrdperiod.executeQuery();

        while (rs.next()) {
            Ordinationperiod ordinationperiod = new Ordinationperiod(
                    rs.getInt("OID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("SSN"),
                    rs.getShort("SSN_TYPE"),
                    rs.getString("CPPAL_TXT"),
                    rs.getString("PAL_FIRSTNAME"),
                    rs.getString("PAL_LASTNAME"),
                    rs.getShort("PAL_TITLE"),
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
                    rs.getShort("COMPLFOLYEAR")
            );
            ordinationperiodList.add(ordinationperiod);
        }

        /***  Här skapas PDF dokumentet ***/
        if(ordinationperiodList.size() > 0) {
            PDFOrdinationperiod pdfOrdinationperiod = new PDFOrdinationperiod(ordinationperiodList);
            pdfOrdinationperiod.createOrdinationperiodDetails();
        }

        ordinationperiodList.stream()
                .forEach(System.out::println);
        System.out.println("Total antal poster: " + ordinationperiodList.size());

        if(writeToFile){
            POJOToFile(ordinationperiodList);
            POJOListToJSONToFile(ordinationperiodList);
        }
    }

    private void POJOToFile(List<Ordinationperiod> kpd) throws IOException {
        FileWriter writer = new FileWriter(POJOFileName);
        long count = kpd.stream().count();
        for (Ordinationperiod ordProv : kpd) {
            writer.write(ordProv + System.lineSeparator());
        }
        writer.write("Total antal poster: " + count + System.lineSeparator());
        writer.close();
    }

    private void POJOListToJSONToFile(List<Ordinationperiod> kpd) throws IOException {
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



