package OANT;

import MV.HemorrhagesR7;
import MV.HemorrhagesR7Exception;
import Mott.JournalcommentException;
import OrdinationperiodLKM.Waran.OrdinationsperiodInitializeException;
import PDF.MV.PDFHemorrhagesR7;
import Person.PatientGeneralDataException;
import Person.PersonInitializationException;
import auxilliary.GeneralBefattningReadJSONException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jcraft.jsch.JSchException;
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


public class ComplicationBuilderR7 {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/hemorrhagesWaran.txt";
    private String JSONFileName = "temp/ordination/hemorrhagesWaran.json";
    private int totalHemorrhagesPoster = 0;

    private long countObjChars;
    private long hemorrhagesListCount;

    private Connection myConnection = null;

    public ComplicationBuilderR7(final Connection con) {
        this.myConnection = con;
    }

    public void buildComplicationR7(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException, JSchException, PatientGeneralDataException, HemorrhagesR7Exception, GeneralBefattningReadJSONException, JournalcommentException, ComplicationR7Exception {
        sqlScriptFilePath = "src/resource/sql/OANT/ComplicationR7.sql";
        Path file = Path.of(sqlScriptFilePath);
        String sqlStatement = Files.readString(file);

        PreparedStatement selComplication = myConnection.prepareStatement(sqlStatement);
        selComplication.setString(1, centreId);
        selComplication.setString(2, regpatSSN);

        ResultSet rs = selComplication.executeQuery();

        List<ComplicationR7> complicationR7List = new ArrayList<>();
        while (rs.next()) {
            ComplicationR7 complicationR7 = new ComplicationR7(
                    rs.getString("CENTRE_ID"),
                    rs.getInt("PATIENT_PID"),
                    rs.getString("SSN"),
                    rs.getShort("SSN_TYPE"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),

                    rs.getInt("ORDINATIONPERIOD_ID"),
                    rs.getString("COMPLEXISTS"),
                    rs.getString("BLEEDING"),
                    rs.getString("TROMBOSIS"),
                    rs.getInt("DAYSOFCARE"),
                    rs.getBigDecimal("PKVALUE"),
                    rs.getString("STATUS"),

                    rs.getString("CREATEDBY"),
                    rs.getString("UPDATEDBY"),
                    rs.getTimestamp("TSCREATED")
            );
            complicationR7List.add(complicationR7);
        }
        int listSize = complicationR7List.size();
        myConnection.close();

        complicationR7List.stream()
                .forEach(System.out::println);
        System.out.println("Total antal popster: " + listSize);

        if(complicationR7List.size() > 0){
            PDFComplicationR7 pdfComplicationR7 = new PDFComplicationR7(complicationR7List);
            pdfComplicationR7.createComplicationDetailsR7();
        }

        if (writeToFile) {
            writePOJOToFile(complicationR7List, regpatSSN);
            POJOListToJSONToFile(complicationR7List);
        }
    }

    private void writePOJOToFile(List<ComplicationR7> complicationR7s, String regpat) throws IOException {
        if (regpat.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        } else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Hemorrhages för patient:\n");
        for (ComplicationR7 comp : complicationR7s) {
            pojoWriter.write(comp + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + complicationR7s.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<ComplicationR7> hemorrhagesR7List) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String listToJson = objectMapper.writeValueAsString(hemorrhagesR7List);
        JSONArray jArr = new JSONArray(listToJson);

        // Convert List of person objects to JSON :");
        System.out.println(listToJson);
        System.out.println("Antal poster: " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(listToJson);
        jsonWriter.write("\nTotal antal hemorrhages poster: " + jArr.length() + System.lineSeparator());
        jsonWriter.close();
    }

    private String insertString(String original, String mode) {
        StringBuffer outfile = new StringBuffer(original);
        int indexPos = outfile.lastIndexOf(".");
        if (mode == "One")
            return outfile.insert(indexPos, "One-Patient").toString();
        else
            return outfile.insert(indexPos, "All-Patients").toString();
    }

    public boolean compareResults() {
        return countObjChars == hemorrhagesListCount;
    }
}
