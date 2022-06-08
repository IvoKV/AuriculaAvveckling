package PDF.Ordination;

import Mott.JournalcommentBuilder;
import Mott.JournalcommentException;
import auxilliary.*;
import OrdinationMOTT.Ordinationperiod;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PDFOrdinationperiod {
    private List<Ordinationperiod> ordinationperiodList;
    private Connection connection;

    private final String pdfPathFileName = "out\\PDFOrdinationperiod.pdf";
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
    private int sidaNo = 0;

    public PDFOrdinationperiod(List<Ordinationperiod> ordinationplist, Connection connection) throws IOException {
        this.ordinationperiodList = ordinationplist;
        this.connection = connection;

        /** Initialize document and first page **/
        this.document = new PDDocument();
        this.page = new PDPage();

        /** Initialize content stream, first page **/
        contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
    }

    private void writeHeader() throws IOException {
        float yCordinate = page.getCropBox().getUpperRightY() - 30;
        float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;

        /* ORDINATIONPERIOD */
        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER_BOLD, 14f);
        contentStream.newLineAtOffset( startX, yCordinate);
        contentStream.showText("Ordinationperiod");
        yHoldOID = yCordinate;
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
        contentStream.showText(ordinationperiodList.get(0).getPatFirstName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(ordinationperiodList.get(0).getPatLastName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(ordinationperiodList.get(0).getSsn());
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30f, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1 , 0);
        contentStream.showText(ordinationperiodList.get(0).getSsnType().toString());
        contentStream.endText();
        yHold -= leading;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }

    public void createOrdinationperiodDetails() throws IOException, GeneralBefattningReadJSONException, JournalcommentException, SQLException {
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

            int arraySize = ordinationperiodList.size();

            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {
                int currentOID = ordinationperiodList.get(arrayItem).getOid();

                /* ADDITION: OID */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX + 160f, yHoldOID);

                /* OID documentation  */
                StringBuilder sb = new StringBuilder();
                sb.append(ordinationperiodList.get(arrayItem).getOid());             // GET OID
                sb.append(" (");
                sb.append(arrayItem + 1);
                sb.append( " av ");
                int ordListSize = ordinationperiodList.size();
                sb.append(ordListSize);
                sb.append(")");
                contentStream.showText(sb.toString());
                contentStream.endText();
                sb = null;

                /* (PAL) Ansvarig - Resposible TEXT */
                StringBuilder responsible = new StringBuilder();
                GeneralBefattningReadJSON genBef = new GeneralBefattningReadJSON(ordinationperiodList.get(arrayItem).getCreatedBy());
                responsible.append(genBef.getGeneralBefattningFirstName() + " ");
                responsible.append(genBef.getGeneralBefattningLastName());

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Ansvarig:");
                contentStream.newLineAtOffset(startX + 65f, 0);
                contentStream.showText(responsible.toString());
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2 +30f, y);
                contentStream.showText("Titel:");
                contentStream.newLineAtOffset(50, 0);
                contentStream.showText(genBef.getGeneralBefattningTitel());
                contentStream.endText();
                genBef = null;
                yHold = y;
                yHold -= fontHeight / 2;

                /*  --- line ---   */
                contentStream.setLineWidth(0.5f);
                contentStream.moveTo(startX, yHold);
                contentStream.lineTo(endX, yHold);
                contentStream.stroke();
                y = yHold - leading;

                /* MEDICINE TYPE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Medicine Type:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getMedicinetype());
                contentStream.endText();

                /* -> Atrial Fib */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("AtrialFib:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getAtrialFib());
                contentStream.endText();
                y -= leading;

                /* VALVE MALFUNCTION */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Valve malfunction:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getValveMalfunction());
                contentStream.endText();

                /* -> VENOUS TROMB */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Venous tromb:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getVenousTromb());
                contentStream.endText();
                y -= leading;

                /* OTHER */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Other:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getOther());
                contentStream.endText();

                /* -> OTHER CHILDINDICATION */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Otherchildind:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getOtherChildIndication());
                contentStream.endText();
                y -= leading;

                /* DCCONVERSION */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Dcconversion:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getDcconversion());
                contentStream.endText();

                /* -> DCTHERAPYDROPOUT */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Dctherapydrop:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showIntToText(contentStream, ordinationperiodList.get(arrayItem).getDctherapydropout());
                contentStream.endText();
                y -= leading;

                /* PERIODLENGTH */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Period length:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getPeriodLength());
                contentStream.endText();

                /* -> MEDICIN */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Medicin:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getMedicin());
                contentStream.endText();
                y -= leading;

                /* DOSE MODE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Dose mode:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getDoseMode());
                contentStream.endText();
                y -= leading;

                /*  CREAINTERVAL FIRSTYEAR */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Cr.int.first Y.:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getCreaintervalFirstyear());
                contentStream.endText();

                /* -> STARTDATE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Startdate:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showDate(contentStream, ordinationperiodList.get(arrayItem).getStartDate());
                contentStream.endText();
                y -= leading;

                /* CREAINTERVAL */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Crea interval:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getCreainterval());
                contentStream.endText();

                /* -> ENDDATE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Enddate:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showDate(contentStream, ordinationperiodList.get(arrayItem).getEndDate());
                contentStream.endText();
                y -= leading;

                /* CREA COMPLATEST CREATED */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Cr. comp. testcr.:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream, ordinationperiodList.get(arrayItem).getCreaComplaTestcreated());
                contentStream.endText();

                /* -> CREA COMPLETED FIRST YEAR */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Cr.complFirstY:");
                contentStream.newLineAtOffset(x2Offset + 10f, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getCreaComplFirstYear());
                contentStream.endText();
                y -= leading;

                /* CREA COMPL FOLL YEAR */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Cr.comp.folYear.:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream, ordinationperiodList.get(arrayItem).getCreaComplFolYear());
                contentStream.endText();

                /* -> INR METHOD*/
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Inr method:");
                contentStream.newLineAtOffset(x2Offset + 10f, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getInrmethod());
                contentStream.endText();
                y -= leading;

                /* CONTINUE LATE CHECK */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Cont.latecheck:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream, ordinationperiodList.get(arrayItem).getContinueLateCheck());
                contentStream.endText();
                y -= leading;

                /* REASON STOPPED */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Reason stopped:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getReasonStopped());
                contentStream.endText();
                y -= leading;

                /* LENGTHCOMMENT */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Lengthcomment:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getLengthcomment());
                contentStream.endText();

                /* -> COMPLFOLYEAR */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Compl Folyear:");
                contentStream.newLineAtOffset(x2Offset + 10f, 0);
                TextShower.showIntToText(contentStream, ordinationperiodList.get(arrayItem).getComplfolYear());
                contentStream.endText();
                y -= leading;

                /* CREATED BY */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Createdby:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, responsible.toString());
                contentStream.endText();
                responsible = null;

                /* -> UPDATEDBY */
                StringBuilder updatedBy = new StringBuilder();
                GeneralBefattningReadJSON genBef1 = new GeneralBefattningReadJSON(ordinationperiodList.get(arrayItem).getUpdatedBy());
                updatedBy.append(genBef1.getGeneralBefattningFirstName() + " ");
                updatedBy.append(genBef1.getGeneralBefattningLastName());
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Updatedby:");
                contentStream.newLineAtOffset(x2Offset + 10f, 0);
                TextShower.showString(contentStream, updatedBy.toString());
                contentStream.endText();
                genBef1 = null;
                y -= leading;
                /** end of Ordinationperiod page **/

                /* adding JOURNALCOMMENT */
                JournalcommentBuilder  journalcommentBuilder = new JournalcommentBuilder(connection, currentOID);
                String centreId = ordinationperiodList.get(arrayItem).getCentreId();
                String ssn = ordinationperiodList.get(arrayItem).getSsn();
                journalcommentBuilder.buildJournalcomment(centreId, ssn, contentStream);
                /** end of Journalcomment addition **/

                contentStream.close();

               if (arrayItem < arraySize - 1) {
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
        String filenameWithSSN = fileWithoutExtension + "_" + ordinationperiodList.get(0).getSsn() + ".pdf";
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
