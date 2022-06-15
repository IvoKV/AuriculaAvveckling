package OANT;

import MV.HemorrhagesR7Exception;
import Mott.JournalcommentException;
import OrdinationperiodLKM.Waran.OrdinationsperiodInitializeException;
import PDF.OANT.PDFObservandaR7;
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


public class OrdpatientObservandaBuilderR7 {
    private String sqlScriptFilePath = null;
    private String POJOFileName = "temp/OANT/ObservandaR7.txt";
    private String JSONFileName = "temp/OANT/ObservandaR7.json";
    private int totalHemorrhagesPoster = 0;

    private Connection myConnection = null;

    public OrdpatientObservandaBuilderR7(final Connection con) {
        this.myConnection = con;
    }

    public void buildObservandaR7(String centreId, String regpatSSN, Boolean writeToFile) throws SQLException, ClassNotFoundException, IOException, PersonInitializationException, OrdinationsperiodInitializeException, JSchException, PatientGeneralDataException, HemorrhagesR7Exception, GeneralBefattningReadJSONException, JournalcommentException, ComplicationR7Exception {
        sqlScriptFilePath = "src/resource/sql/OANT/ObservandaR7.sql";
        Path file = Path.of(sqlScriptFilePath);
        String sqlStatement = Files.readString(file);

        PreparedStatement selComplication = myConnection.prepareStatement(sqlStatement);
        selComplication.setString(1, centreId);
        selComplication.setString(2, regpatSSN);

        ResultSet rs = selComplication.executeQuery();

        List<OrdpatientObservandaR7> ordpatientObservandaR7List = new ArrayList<>();

        while (rs.next()) {
            OrdpatientObservandaR7 ordpatientObservandaR7 = new OrdpatientObservandaR7(
                    rs.getInt("PATIENT_PID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("SSN"),
                    rs.getShort("SSN_TYPE"),

                    rs.getInt("ORDP_RPID"),
                    rs.getString("ORDP_OBSERVANDA_COMMENT"),
                    rs.getString("ORDP_SEVERITY_OBSERVANDA"),
                    rs.getString("CREATEDBY"),
                    rs.getString("UPDATEDBY"),
                    rs.getTimestamp("TSCREATED")
            );
            ordpatientObservandaR7List.add(ordpatientObservandaR7);
        }

        int listSize = ordpatientObservandaR7List.size();
        myConnection.close();

        ordpatientObservandaR7List.stream()
                .forEach(System.out::println);
        System.out.println("Total antal popster: " + listSize);

        if(ordpatientObservandaR7List.size() > 0){
            PDFObservandaR7 pdfObservandaR7 = new PDFObservandaR7(ordpatientObservandaR7List);
            pdfObservandaR7.createComplicationDetailsR7();
        }

        if (writeToFile) {
            writePOJOToFile(ordpatientObservandaR7List, regpatSSN);
            POJOListToJSONToFile(ordpatientObservandaR7List);
        }
    }

    private void writePOJOToFile(List<OrdpatientObservandaR7> ordpatientObservandaR7s, String regpat) throws IOException {
        if (regpat.length() > 0) {
            POJOFileName = insertString(POJOFileName, "One");
            JSONFileName = insertString(JSONFileName, "One");
        } else {
            POJOFileName = insertString(POJOFileName, "All");
            JSONFileName = insertString(JSONFileName, "All");
        }

        FileWriter pojoWriter = new FileWriter(POJOFileName);
        pojoWriter.write("Hemorrhages för patient:\n");
        for (OrdpatientObservandaR7 comp : ordpatientObservandaR7s) {
            pojoWriter.write(comp + System.lineSeparator());
        }
        pojoWriter.write("Total antal poster: " + ordpatientObservandaR7s.size() + "\n");
        pojoWriter.close();
    }

    private void POJOListToJSONToFile(List<OrdpatientObservandaR7> hemorrhagesR7List) throws IOException {
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
}
