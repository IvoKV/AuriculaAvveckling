package PDF.Ordination;

import auxilliary.FileOperations;
import auxilliary.ListGenerics;
import auxilliary.TextShower;
import ordination.KontrollerProvtagningDoseringar.Ordinationperiod;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFOrdinationperiod {
    private List<Ordinationperiod> ordinationperiodList;

    private final String pdfPathFileName = "PDFOrdinationperiod.pdf";
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

    public PDFOrdinationperiod(List<Ordinationperiod> ordinationplist) throws IOException {
        this.ordinationperiodList = ordinationplist;

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
        contentStream.showText(ordinationperiodList.get(0).getFirstName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(ordinationperiodList.get(0).getLastName());
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
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1 , 0);
        contentStream.showText(ordinationperiodList.get(0).getSsntype().toString());
        contentStream.endText();
        yHold -= leading;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }

    public void createOrdinationperiodDetails() throws IOException {
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
            int currentOID = 0;
            int oidCounter = 0;

            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {
                /* ADDITION: OID */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX + 160f, yHoldOID);

                StringBuilder sb = new StringBuilder();
                sb.append(ordinationperiodList.get(arrayItem).getOid());
                sb.append(" (");
                sb.append(arrayItem + 1);
                sb.append( " av ");
                int ordListSize = ordinationperiodList.size();
                sb.append(ordListSize);
                sb.append(")");
                contentStream.showText(sb.toString());
                contentStream.endText();
                sb = null;

                /* PAL TEXT */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Pal Text:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getPaltext());
                contentStream.endText();
                y -= leading;
                yHold = y;
                yHold += fontHeight / 2;

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
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getCreatedBy());
                contentStream.endText();

                /* -> UPDATEDBY */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Updatedby:");
                contentStream.newLineAtOffset(x2Offset + 10f, 0);
                TextShower.showString(contentStream, ordinationperiodList.get(arrayItem).getUpdatedBy());
                contentStream.endText();
                y -= leading;

                /** end of page **/
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
