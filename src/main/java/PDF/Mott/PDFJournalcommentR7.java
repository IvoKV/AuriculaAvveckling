package PDF.Mott;

import Mott.Journalcomment;
import Person.GeneralBefattning;
import auxilliary.FileOperations;
import auxilliary.GeneralBefattningReadJSON;
import auxilliary.GeneralBefattningReadJSONException;
import auxilliary.TextShower;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFJournalcommentR7 {
    private List<Journalcomment> journalcommentList;
    private String patFirstName;
    private String patLastName;
    private String patSSN;
    private Short ssnType;

    // this Journalcomment will be printed as stand-alone file
    private final String pdfPathFileName = "out/R7/PDFJournalcommentR7.pdf";
    private float x = 0;
    private float y = 750;
    private final float leading = 20;
    private final float fontSize = 12;
    private final float fontHeight = fontSize;
    //private  float yHoldOID = 0;

    private PDFont fontNormal = PDType1Font.COURIER;
    private PDFont fontBold = PDType1Font.COURIER_BOLD;

    private PDDocument document = null;
    private PDPage page = null;
    private PDPageContentStream contentStream = null;
    private int oid = 0;

    public PDFJournalcommentR7(List<Journalcomment> journalcommentList, String patFirstName, String patLastName, String SSN, short ssnType) throws IOException {
        this.journalcommentList = journalcommentList;

        /** Initialize document and first page **/
        this.document = new PDDocument();
        this.page = new PDPage();

        /** Initialize contenstream, first page  **/
        this.contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, true, true);

        this.patFirstName = patFirstName;
        this.patLastName = patLastName;
        this.patSSN = SSN;
        this.ssnType = ssnType;
    }

    private void writeHeader() throws IOException {
        float yCordinate = page.getCropBox().getUpperRightY() - 30;
        float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;

        /* JOURNALCOMMENT */
        contentStream.beginText();
        contentStream.setFont(fontBold, 14f);
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(startX, yCordinate);
        contentStream.showText("JOURNALCOMMENT");
        StringBuilder sb = new StringBuilder();
        sb.append("oId:" + " ");
        sb.append(journalcommentList.get(0).getOid());
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(sb.toString());
        contentStream.endText();
        yCordinate -= fontHeight;

        contentStream.setLineWidth(1f);
        contentStream.moveTo(startX, yCordinate);
        contentStream.lineTo(endX, yCordinate);
        contentStream.stroke();
        yCordinate -= leading;
        y = yCordinate;
    }

    private void writePatientInfo() throws IOException {
        float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;
        float startX2 = startX + 280f;
        float xTab1 = startX + 65f;
        float yHold = y;

        contentStream.beginText();
        contentStream.setFont(fontNormal, fontSize);
        contentStream.setLeading(leading);
        contentStream.setFont(PDType1Font.COURIER, 12f);
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Förnamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(patFirstName);
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(patLastName);
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(patSSN);
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30f, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1 , 0);
        TextShower.showIntToText(contentStream, ssnType);
        contentStream.endText();
        yHold -= fontHeight;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }

    public void createJournalCommentDetails() throws IOException, GeneralBefattningReadJSONException {
        final float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;
        final float startX2 = startX + 280f;
        final float xTab1 = startX + 110f;
        final float xTab2 = startX2 + 80f;
        final float x2Offset = 100;
        float yHold = y;

        if (contentStream != null) {
            /** First page (layout only for one, it is sufficient for this info type) **/
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, true, true);
            writeHeader();
            writePatientInfo();

            /* SECTION FOR JOUTNALCOMMENT, with wrapped lines */
            for(int i = 0; i < journalcommentList.get(0).getWrappedJournalComment().size(); i++){
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText( journalcommentList.get(0).getWrappedJournalComment().get(i));
                contentStream.endText();
                y -= leading;

                if(y <= 50){
                    contentStream.close();

                    this.page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                    writeHeader();
                    writePatientInfo();
                }
            }

            /* CREATED BY */
            StringBuilder sb = new StringBuilder();
            GeneralBefattningReadJSON genBef = new GeneralBefattningReadJSON(journalcommentList.get(0).getCreatedBy());
            sb.append(genBef.getGeneralBefattningFirstName() + " ");
            sb.append(genBef.getGeneralBefattningLastName());
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, y);
            contentStream.showText("Created by:");
            contentStream.newLineAtOffset(xTab1, 0);
            contentStream.showText(sb.toString());
            contentStream.endText();
            sb = null;
            genBef = null;

            contentStream.close();
            writePageNumbers();
        }

        FileOperations fop = new FileOperations(pdfPathFileName);
        String fileWithoutExtension =  fop.getFilenameWithoutExtension(); // kontrollerProvtagningDoseringarList.get(0).getSsn();
        fop = null;
        oid = journalcommentList.get(0).getOid();
        String filenameWithSSN = fileWithoutExtension + "_" + patSSN + "(" + oid + ")" + ".pdf";

        document.save(filenameWithSSN);
        document.close();
    }

    private void writePageNumbers() throws IOException {
        float yCordinate = page.getCropBox().getUpperRightY() - 30;
        float endX = page.getCropBox().getUpperRightX() - 100;

        int pagecounter = 1;
        int sumPages = document.getPages().getCount();

        for(PDPage page : document.getPages()){
            StringBuilder sb = new StringBuilder();
            sb.append("sid: ");
            sb.append(pagecounter);
            sb.append(" (" + sumPages + ")");

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, false);
            contentStream.setFont(PDType1Font.COURIER, 12);

            contentStream.beginText();
            contentStream.newLineAtOffset(endX, yCordinate);
            contentStream.showText(sb.toString());
            contentStream.endText();
            contentStream.close();
            pagecounter++;
        }
    }
}
