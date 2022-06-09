package MV;

import OrdinationMOTT.Ordinationperiod;
import PDF.MV.PDFMatvardeL;
import PDF.Ordination.PDFOrdinationperiod;
import auxilliary.GeneralBefattningReadJSONException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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

public class MatvardeLBuilder {
    private Connection connection;

    private final String sqlScriptFileMatvardeL = "src/resource/sql/matvarde/MatvardeL.sql";
    private final String JSONFileName = "temp/matvarde/matvardeL.json";

    List<MatvardeL> matvardeLList = null;

    public MatvardeLBuilder(final Connection con) throws IOException {
        this.connection = con;
        this.matvardeLList = new ArrayList<>();
    }

    public void buildMatvardeL(String centreId, String regpatSSN, Boolean writeToFile) throws IOException, SQLException, MatvardeLBuilderException, GeneralBefattningReadJSONException {
        Path file = Path.of(sqlScriptFileMatvardeL);

        String sqlStatement = Files.readString(file);

        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, centreId);
        preparedStatement.setString(2, regpatSSN);

        ResultSet rs = preparedStatement.executeQuery();

        List<MatvardeL> matvardeLlist = new ArrayList<>();

        while (rs.next()) {
            MatvardeL matvardeL = new MatvardeL(
                rs.getString("CENTREID"),
                rs.getString("SSN"),
                rs.getShort("SSN_TYPE"),
                rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"),

                    rs.getString("HMR_LEVERNJURSJUKDOM"),
                    rs.getString("HMR_ETANOLMISSBRUK"),
                    rs.getString("HMR_MALIGNINITET"),
                    rs.getString("HMR_REDUCERAT_TROMBOCYTANTAL_FUNK"),
                    rs.getString("HMR_TIDIGARE_BLÖDNING"),

                    rs.getString("HMR_HYPERTONI"),
                    rs.getString("HMR_ANEMI"),
                    rs.getString("HMR_GENETISKAFAKTORER"),
                    rs.getString("HMR_STOR_RISK_FÖR_FALL"),
                    rs.getString("HMR_STROKE"),

                    rs.getString("HMR_CREATEDBY"),
                    rs.getString("HMR_UPDATEDBY"),
                    rs.getTimestamp("HMR_TSCREATED"),
                    rs.getString("CHA2_HJÄRTSVIKT"),
                    rs.getString("CHA2_HYPERTONI"),

                    rs.getString("CHA2_DIABETES"),
                    rs.getString("CHA2_STROKE"),
                    rs.getString("CHA2_CREATEDBY"),
                    rs.getString("CHA2_UPDATEDBY"),
                    rs.getTimestamp("CHA2_TSCREATED")
            );
            matvardeLlist.add(matvardeL);
        }

        /***  Här skapas PDF dokumentet ***/
        if(matvardeLlist.size() > 0) {
            PDFMatvardeL pdfMatvardeL = new PDFMatvardeL(matvardeLlist);
            pdfMatvardeL.createMatvardeLDetails();
        }

        System.out.println("Antal matvardeL: " + matvardeLlist.size());

        System.out.println(matvardeLlist);

        if(writeToFile) {
            POJOListToJSONToFile(matvardeLlist);
        }
    }

    private void POJOListToJSONToFile(List<MatvardeL> matvarde) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(matvarde);
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
