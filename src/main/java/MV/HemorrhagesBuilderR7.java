package MV;

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


public class HemorrhagesBuilderR7 {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/ordination/hemorrhagesWaran.txt";
    private String JSONFileName = "temp/ordination/hemorrhagesWaran.json";
    private int totalHemorrhagesPoster = 0;

    private long countObjChars;
    private long hemorrhagesListCount;

    private Connection myConnection = null;

    public HemorrhagesBuilderR7(final Connection con) {
        this.myConnection = con;

    }

    public void buildHemorrhages(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException, JSchException, PatientGeneralDataException, HemorrhagesR7Exception, GeneralBefattningReadJSONException {
        sqlScriptFilePath = "src/resource/sql/MV/HemorrhagesR7.sql";
        Path file = Path.of(sqlScriptFilePath);
        String sqlStatement = Files.readString(file);

        PreparedStatement selectHemorrhages = myConnection.prepareStatement(sqlStatement);
        selectHemorrhages.setString(1, centreId);
        selectHemorrhages.setString(2, regpatSSN);

        ResultSet rsHemorrhages = selectHemorrhages.executeQuery();

        List<HemorrhagesR7> hemorrhagesR7List = new ArrayList<>();
        while (rsHemorrhages.next()) {
            HemorrhagesR7 hemorrhages = new HemorrhagesR7(
                    rsHemorrhages.getString("CID"),
                    rsHemorrhages.getInt("PID"),
                    rsHemorrhages.getString("SSN"),
                    rsHemorrhages.getShort("SSN_TYPE"),
                    rsHemorrhages.getString("FIRSTNAME"),
                    rsHemorrhages.getString("LASTNAME"),

                    rsHemorrhages.getString("LEVER_ELLER_NJURSJUKDOM"),
                    rsHemorrhages.getString("ETANOLMISSBRUK"),
                    rsHemorrhages.getString("MALIGNITET"),
                    rsHemorrhages.getString("REDUCERAT_TROMBOCYTANTALFUNKTION"),
                    rsHemorrhages.getString("TIDIGARE_BLODNING"),
                    rsHemorrhages.getString("HYPERTONI"),
                    rsHemorrhages.getString("ANEMI"),
                    rsHemorrhages.getString("GENETISKA_FAKTORER"),
                    rsHemorrhages.getString("STOR_RISK_FOR_FALL"),
                    rsHemorrhages.getString("STROKE"),
                    rsHemorrhages.getString("CREATEDBY"),
                    rsHemorrhages.getString("UPDATEDBY"),
                    rsHemorrhages.getTimestamp("TSCREATED")
            );
            hemorrhagesR7List.add(hemorrhages);
        }
        int listSize = hemorrhagesR7List.size();
        //myConnection.close();

        hemorrhagesR7List.stream()
                .forEach(System.out::println);
        System.out.println("Total antal popster: " + listSize);

        if(hemorrhagesR7List.size() > 0){
            PDFHemorrhagesR7 pdfHemorrhagesR7 = new PDFHemorrhagesR7(hemorrhagesR7List);
            pdfHemorrhagesR7.createHemorrhagesR7();
        }

        if (writeToFile) {
            writePOJOToFile(hemorrhagesR7List, regpatSSN);
            POJOListToJSONToFile(hemorrhagesR7List);
        }
    }

    private void writePOJOToFile(List<HemorrhagesR7> ordp, String regpat) throws IOException {
        if (regpat.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        } else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Hemorrhages för patient:\n");
        for (HemorrhagesR7 hmhg : ordp) {
            pojoWriter.write(hmhg + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + ordp.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<HemorrhagesR7> hemorrhagesR7List) throws IOException {
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
