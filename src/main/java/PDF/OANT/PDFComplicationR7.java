package PDF.OANT;

import MV.HemorrhagesR7;
import Mott.JournalcommentBuilder;
import Mott.JournalcommentException;
import OANT.ComplicationR7;
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
import java.sql.SQLException;
import java.util.List;

public class PDFComplicationR7 {

    private List<ComplicationR7> complicationR7List = null;

    private final String pdfPathFileName = "out/R7/PDFComplicationR7.pdf";

    private float x = 0;
    private float y = 750;
    private final float leading = 20;
    private float fontSize = 12;
    private float fontHeight = fontSize;

    private PDFont fontNormal = PDType1Font.COURIER;
    private PDFont fontBold = PDType1Font.COURIER_BOLD;

    private PDDocument document = null;
    private PDPage page = null;
    private PDPageContentStream contentStream = null;
    private int sidaNo = 0;
    public PDFComplicationR7(List<ComplicationR7> complicationR7List) throws IOException {
        this.complicationR7List = complicationR7List;

        /** Initialize document and first page **/
        this.document = new PDDocument();
        this.page = new PDPage();

        /** Initialize content stream, first page **/
        contentStream = new PDPageContentStream(document, page);
    }

    private void writeHeader() throws IOException {
        float yCordinate = page.getCropBox().getUpperRightY() - 30;
        float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;

        /* ORDINATIONPERIOD */
        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER_BOLD, 14f);
        contentStream.newLineAtOffset( startX, yCordinate);
        contentStream.showText("Complication");
        yCordinate -= fontHeight;
        contentStream.endText();

        contentStream.setLineWidth(2f);
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
        contentStream.setFont(PDType1Font.COURIER, 12f);
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Förnamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(complicationR7List.get(0).getPatFirstName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(complicationR7List.get(0).getPatLastName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(complicationR7List.get(0).getSsn());
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30f, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1 , 0);
        contentStream.showText(complicationR7List.get(0).getSsnType().toString());
        contentStream.endText();
        yHold -= leading;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }

    public void createComplicationDetailsR7() throws IOException, GeneralBefattningReadJSONException, JournalcommentException, SQLException {
        final float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;
        final float startX2 = startX + 280f;
        final float xTab1 = startX + 110f;
        final float xTab2 = startX2 + 80f;
        final float x2Offset = 100;
        float yHold = y;

        if(contentStream != null) {
            /** First page **/
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(fontBold, fontSize + 2);
            contentStream.setLeading(leading);
            writeHeader();
            writePatientInfo();     // written only once, on page 1

            int arraySize = complicationR7List.size();

            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {

                /* OID */
                contentStream.beginText();
                contentStream.setFont(fontBold, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("OID:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream, complicationR7List.get(arrayItem).getOid());
                contentStream.endText();
                y -= leading;

                /* BLEEDING */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Bleeding:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, complicationR7List.get(arrayItem).getBleeding());
                contentStream.endText();
                y -= leading;

                /* TROMBOSIS */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Trombosis:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, complicationR7List.get(arrayItem).getTrombosis());
                contentStream.endText();
                y -= leading;

                /* DAYS OF CARE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Days of care:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream, complicationR7List.get(arrayItem).getDaysOfCare());
                contentStream.endText();
                y -= leading;

                /* PKVALUE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("PK value:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showBigdecimal(contentStream, complicationR7List.get(arrayItem).getPKINR());
                contentStream.endText();
                y -= leading;

                /* STATUS */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Status:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, complicationR7List.get(arrayItem).getStatus());
                contentStream.endText();
                y -= leading;

                /* CREATED BY */
                StringBuilder sbcr = new StringBuilder();
                GeneralBefattningReadJSON gbj = new GeneralBefattningReadJSON(complicationR7List.get(arrayItem).getCreatedBy());
                sbcr.append(gbj.getGeneralBefattningFirstName() + " ");
                sbcr.append(gbj.getGeneralBefattningLastName());
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Created by:");
                contentStream.newLineAtOffset(100, 0);
                TextShower.showString(contentStream, sbcr.toString());
                sbcr = null;
                gbj = null;
                contentStream.endText();

                // todo: hur exact nullvärden i hsaId skall hanteras
                /* -> UPDATED BY */
                StringBuilder sbupd = new StringBuilder();
                GeneralBefattningReadJSON gbj2 = new GeneralBefattningReadJSON(complicationR7List.get(arrayItem).getUpdatedBy());
                sbupd.append(gbj2.getGeneralBefattningFirstName() + " ");
                sbupd.append(gbj2.getGeneralBefattningLastName() + " ");
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Updated by:");
                contentStream.newLineAtOffset(100, 0);
                TextShower.showString(contentStream, sbupd.toString());
                sbupd = null;
                gbj2 = null;
                contentStream.endText();
                y -= leading;

                /* TSCREATED */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Created (TS):");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(complicationR7List.get(arrayItem).getTsCreated().toString().substring(0, 19));
                contentStream.endText();
                y -= leading;

                if(y <= 100){
                    contentStream.close();
                    this.page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setLeading(leading);
                    writeHeader();
                }

            }
            contentStream.close();
            writePageNumbers();
        }
        FileOperations fop = new FileOperations(pdfPathFileName);
        String fileWithoutExtension =  fop.getFilenameWithoutExtension(); // kontrollerProvtagningDoseringarList.get(0).getSsn();
        fop = null;
        String filenameWithSSN = fileWithoutExtension + "_" + complicationR7List.get(0).getSsn() + ".pdf";
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

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
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
