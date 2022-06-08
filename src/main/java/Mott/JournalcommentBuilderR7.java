package Mott;

import PDF.Mott.PDFJournalcomment;
import PDF.Mott.PDFJournalcommentR7;
import auxilliary.GeneralBefattningReadJSONException;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: Ivo Kristensjö Vukmanovic
 * company: Regionstockholm
 * purpose: to ensure that the journalcomments will be wrapped if neccessary
 * date: 2022-06-08
 */

public class JournalcommentBuilderR7 {
    private final String sqlScriptFilePathJournalcomment = "src/resource/sql/mott/JournalcommentR7.sql";
    private Connection myConnection = null;
    private String centreId;
    private int oid;
    private String patSSN;
    private Short ssnType;
    private String patFirstName;
    private String patLastName;

    List<Journalcomment> journalcomments;

    public JournalcommentBuilderR7(Connection myConnection, String centreId, int oid, String patSSN, Short ssnType, String patFirstName, String patLastName) throws IOException {
        this.myConnection = myConnection;
        this.centreId = centreId;
        this.oid = oid;
        this.patSSN = patSSN;
        this.patFirstName = patFirstName;
        this.patLastName = patLastName;
        this.ssnType = ssnType;

        journalcomments = new ArrayList<>();
    }

    public void buildJournalcomment() throws IOException, SQLException, JournalcommentException, GeneralBefattningReadJSONException {
        Path file = Path.of(sqlScriptFilePathJournalcomment);
        String sqlStatement = Files.readString(file);

        PreparedStatement selOrdperiod = myConnection.prepareStatement(sqlStatement);
        selOrdperiod.setString(1, centreId);
        selOrdperiod.setString(2, patSSN);
        selOrdperiod.setInt(3, oid);
        ResultSet rs = selOrdperiod.executeQuery();

        while (rs.next()) {
            Journalcomment journalcomment = new Journalcomment(
                    rs.getInt("OID"),
                    rs.getString("JOURNALCOMMENT"),
                    rs.getString("CREATEDBY"),
                    rs.getTimestamp("TSCREATED")
            );
            journalcomments.add(journalcomment);
        }

        PDFJournalcommentR7 jourPDFR7 = new PDFJournalcommentR7(journalcomments, patFirstName, patLastName, patSSN, ssnType);
        jourPDFR7.createJournalCommentDetails();
    }
}
