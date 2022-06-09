package Mott;

import PDF.Mott.PDFJournalcomment;
import auxilliary.GeneralBefattningReadJSONException;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * author: Ivo Kristensjö Vukmanovic
 * company: Regionstockholm
 * purpose: to ensure that the journalcomments will be wrapped if neccessary
 * date: 2022-06-01
 */

public class JournalcommentBuilder {
    private final String sqlScriptFilePathJournalcomment = "src/resource/sql/mott/Journalcomment.sql";
    private Connection myConnection = null;
    private int oid;

    Map<Integer, Journalcomment> journalcomments;

    public JournalcommentBuilder(Connection myConnection, int oid) throws IOException {
        this.myConnection = myConnection;
        this.oid = oid;

        this.journalcomments = new HashMap<>();
    }

    public void buildJournalcomment(String centreId, String regpatSSN, PDPageContentStream contentStream) throws IOException, SQLException, JournalcommentException, GeneralBefattningReadJSONException {
        Path file = Path.of(sqlScriptFilePathJournalcomment);
        String sqlStatement = Files.readString(file);

        PreparedStatement selOrdperiod = myConnection.prepareStatement(sqlStatement);
        selOrdperiod.setString(1, centreId);
        selOrdperiod.setString(2, regpatSSN);
        ResultSet rs = selOrdperiod.executeQuery();

        while (rs.next()) {
            Journalcomment journalcomment = new Journalcomment(
                    rs.getInt("OID"),
                    rs.getString("JOURNALCOMMENT"),
                    rs.getString("CREATEDBY"),
                    rs.getTimestamp("TSCREATED")
            );
            journalcomments.put(rs.getInt("OID"), journalcomment);
        }

        PDFJournalcomment jourPDF = new PDFJournalcomment(journalcomments, oid, contentStream);
        jourPDF.createJournalCommentDetails();
    }
}
