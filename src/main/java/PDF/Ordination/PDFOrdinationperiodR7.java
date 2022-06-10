package PDF.Ordination;

import Mott.JournalcommentException;
import OrdinationMOTT.OrdinationperiodR7;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PDFOrdinationperiodR7 {
    private List<OrdinationperiodR7> ordinationperiodListR7;

    private final String pdfPathFileName = "out/R7/PDFOrdinationperiodR7.pdf";
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
    private int oidCountTot = 0;
    private int oidCounter = 0;
    private int currentOid = 0;

    public PDFOrdinationperiodR7(List<OrdinationperiodR7> ordinationplist) throws IOException {
        this.ordinationperiodListR7 = ordinationplist;
        //this.connection = connection;

        /** Initialize document and first page **/
        this.document = new PDDocument();
        this.page = new PDPage();

        /** Initialize content stream, first page **/
        contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, true, true);

        this.oidCountTot = getOidCount();
    }

    private void writeHeader() throws IOException {
        float yCordinate = page.getCropBox().getUpperRightY() - 30;
        float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;

        /* ORDINATIONPERIOD */
        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER_BOLD, 14f);
        contentStream.newLineAtOffset( startX, yCordinate);
        contentStream.showText("Ordinationperiod enl. R7");
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
        contentStream.showText(ordinationperiodListR7.get(0).getPatFirstName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(ordinationperiodListR7.get(0).getPatLastName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(ordinationperiodListR7.get(0).getSsn());
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30f, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1 , 0);
        contentStream.showText(ordinationperiodListR7.get(0).getSsnType().toString());
        contentStream.endText();
        yHold -= leading;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }

    public void createOrdinationperiodDetails(String cid, String ssn) throws IOException, GeneralBefattningReadJSONException, JournalcommentException, SQLException {
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

            int arraySize = ordinationperiodListR7.size();
            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {
                int extractedOid = ordinationperiodListR7.get(arrayItem).getOid();

                /* ADDITION: OID */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX + 230f, yHoldOID);

                /* OID documentation  */
                StringBuilder sb = new StringBuilder();
                sb.append("oid: ");
                sb.append(ordinationperiodListR7.get(arrayItem).getOid());             // GET OID
                sb.append(" (");

                if(currentOid != extractedOid){
                    currentOid = extractedOid;
                    oidCounter++;
                }
                sb.append(oidCounter);
                sb.append( " av ");
                sb.append(oidCountTot);
                sb.append(")");
                contentStream.showText(sb.toString());
                contentStream.endText();
                sb = null;

                /* (PAL) Ansvarig - Resposible TEXT */
                StringBuilder responsible = new StringBuilder();
                GeneralBefattningReadJSON genBef = new GeneralBefattningReadJSON(ordinationperiodListR7.get(arrayItem).getCreatedBy());
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

                /* ATRIAL_FIB */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Atrial Fib:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getAtrialFib());
                contentStream.endText();
                y -= leading;

                /* VALVE MALFUNCTION */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Valve Malfunction:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getValveMalfunction());
                contentStream.endText();
                y -= leading;

                /* VENOUS TROMB */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Venous tromb:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getVenousTromb());
                contentStream.endText();
                y -= leading;

                /* OTHER */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Other:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getOther());
                contentStream.endText();
                y -= leading;

                /* DCCONVERSION */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Dcconversion:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getDcconversion());
                contentStream.endText();

                /* -> MEDICIN */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Medicin:");
                contentStream.newLineAtOffset(x2Offset, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getMedicin());
                contentStream.endText();
                y -= leading;

                /* DOSE MODE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Dose mode:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getDoseMode());
                contentStream.endText();
                y -= leading;

                /* INR METHOD*/
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Inr method:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getInrmethod());
                contentStream.endText();
                y -= leading;

                /* WEIGHT*/
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, fontSize);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Weight:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showFloatToText(contentStream, ordinationperiodListR7.get(arrayItem).getWeight());
                contentStream.endText();

                /* -> WEIGHT DATE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, fontSize);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Weight date:");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showDate(contentStream, ordinationperiodListR7.get(arrayItem).getWeigthDate());
                contentStream.endText();
                y -= leading;

                /* LMH DOSERING */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("LMH dosering:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream, ordinationperiodListR7.get(arrayItem).getLmhDose());
                contentStream.endText();
                y -= leading;

                /* PREFERED INTERVAL START */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Pref.Int.Start:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showFloatToText(contentStream, ordinationperiodListR7.get(arrayItem).getPreferedIntStart());
                contentStream.endText();

                /* -> PREFERED INTERVAL END */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Pref.Int.END:");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showFloatToText(contentStream, ordinationperiodListR7.get(arrayItem).getPreferedIntEnd());
                contentStream.endText();
                y -= leading;

                /* ORDINATION STARTDATE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Ordp.Startdate:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream, ordinationperiodListR7.get(arrayItem).getStartDate());
                contentStream.endText();

                /* -> ORDINATION ENDDATE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Ordp.Enddate:");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showDate(contentStream, ordinationperiodListR7.get(arrayItem).getEndDate());
                contentStream.endText();
                y -= leading;

                /* LENGTHCOMMENT */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Lengthcomment:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getLengthcomment());
                contentStream.endText();
                y -= leading;

                /* REASON STOPPED */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Reason stopped:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, ordinationperiodListR7.get(arrayItem).getReasonStopped());
                contentStream.endText();
                y -= leading;
                /** end of Ordinationperiod page **/

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
        String fileWithoutExtension =  fop.getFilenameWithoutExtension();
        fop = null;
        String filenameWithSSN = fileWithoutExtension + "_" + ordinationperiodListR7.get(0).getSsn() + ".pdf";
        document.save(filenameWithSSN);
        document.close();
        document = null;
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
            contentStream.newLineAtOffset(endX - 15, yCordinate);
            contentStream.showText(sb.toString());
            contentStream.endText();
            contentStream.close();
            pagecounter++;
        }
    }

    private int getOidCount(){
        Set<Integer> oidSet = new HashSet<>();
        for(var item : ordinationperiodListR7){
            oidSet.add(item.getOid());
        }
        return oidSet.size();
    }
}
