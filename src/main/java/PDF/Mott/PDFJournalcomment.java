package PDF.Mott;

import Mott.Journalcomment;
import auxilliary.GeneralBefattningReadJSON;
import auxilliary.GeneralBefattningReadJSONException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

public class PDFJournalcomment {
    private Map<Integer, Journalcomment> journalcommentList;

    private final String pdfPathFileName = "out\\PDFJournalcomment.pdf";
    private float x = 0;
    private float y = 750;
    private final float leading = 20;
    private final float fontSize = 12;
    private final float fontHeight = fontSize;
    private  float yHoldOID = 0;

    private PDFont fontNormal = PDType1Font.COURIER;
    private PDFont fontBold = PDType1Font.COURIER_BOLD;

    private PDDocument document = null;
    private PDPage page = null;
    private PDPageContentStream contentStream = null;
    private int oid = 0;
    private int sidaNo = 0;

    public PDFJournalcomment(Map<Integer, Journalcomment> journalcomments, int oid, PDPageContentStream contentStream ) throws IOException {
        this.journalcommentList = journalcomments;
        this.oid = oid;
        this.contentStream = contentStream;

        /** Initialize document and first page **/
        this.document = new PDDocument();
        this.page = new PDPage();
    }

    private void writeHeader() throws IOException {
        float yCordinate = page.getCropBox().getUpperRightY() - 500;
        float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;

        /* JOURNALCOMMENT */
        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER_BOLD, 14f);
        contentStream.newLineAtOffset( startX, yCordinate);
        contentStream.showText("JOURNALCOMMENT");
        yHoldOID = yCordinate;
        yCordinate -= fontHeight;
        contentStream.endText();

        contentStream.setLineWidth(1f);
        contentStream.moveTo(startX, yCordinate);
        contentStream.lineTo(endX, yCordinate);

        contentStream.stroke();
        yCordinate -= leading;
        y = yCordinate;
    }

    public void createJournalCommentDetails() throws IOException, GeneralBefattningReadJSONException {
        final float startX = 30;
        final float startX2 = startX + 280f;

        if (contentStream != null) {
            /** First page (layout only for one, it is sufficient for this info type) **/
            contentStream.setFont(fontNormal, fontSize);
            contentStream.setLeading(leading);
            writeHeader();

            for (Map.Entry<Integer, Journalcomment> entry : journalcommentList.entrySet()) {
                var resOid = entry.getKey();

                if(resOid != oid){
                    continue;
                }

                contentStream.beginText();
                contentStream.setFont(fontNormal, fontSize);
                contentStream.newLineAtOffset(startX, y);
                Timestamp ts = null;

                for (var wrappedText : entry.getValue().getWrappedJournalComment()) {
                    contentStream.showText(wrappedText);
                    contentStream.newLine();
                    y -= leading;
                    ts = entry.getValue().getTsCreated();
                }

                var createdBy = entry.getValue().getCreatedBy();
                StringBuilder responsible = new StringBuilder();
                GeneralBefattningReadJSON genBef = new GeneralBefattningReadJSON(createdBy);
                responsible.append(genBef.getGeneralBefattningFirstName() + " ");
                responsible.append(genBef.getGeneralBefattningLastName());

                contentStream.showText("Ansvarig: ");
                contentStream.newLineAtOffset(startX + 75f, 0);
                contentStream.showText(responsible.toString());
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2 +30f, y);
                contentStream.showText("Titel:");
                contentStream.newLineAtOffset(50, 0);
                contentStream.showText(genBef.getGeneralBefattningTitel());
                contentStream.endText();
                y -= leading;
                responsible = null;

                /* DATUM */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Dat. created:");
                contentStream.newLineAtOffset(startX + 75f, 0);
                contentStream.showText(ts.toString().substring(0, 19));
                contentStream.endText();
                ts = null;
            }
            contentStream.close();
        }
    }
}
