package PDF.Ordination;

import auxilliary.FileOperations;
import auxilliary.ListGenerics;
import auxilliary.StringWriter1;
import auxilliary.TextShower;
import ordination.KontrollerProvtagningDoseringar.KontrollerProvtagningDoseringar;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class PDFKontrollerProvtagningDoseringar{
    List<KontrollerProvtagningDoseringar> kontrollerProvtagningDoseringarList;

    private ListGenerics listGenerics;
    private final String pdfPathFileName = "PDFKontrollerProvtagningDoseringar.pdf";


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

    public PDFKontrollerProvtagningDoseringar(List<KontrollerProvtagningDoseringar> kontrollerProvtagningDoseringars) throws IOException {
        this.kontrollerProvtagningDoseringarList = kontrollerProvtagningDoseringars;
        this.listGenerics = new ListGenerics(Collections.unmodifiableList(kontrollerProvtagningDoseringars));

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

        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER_BOLD, 14f);
        contentStream.newLineAtOffset( startX, yCordinate);
        contentStream.showText("Kontroller, Provtagning, Doseringar");
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
        contentStream.showText(kontrollerProvtagningDoseringarList.get(0).getFirstName());
        contentStream.endText();
        yHold = y;
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(kontrollerProvtagningDoseringarList.get(0).getLastName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(kontrollerProvtagningDoseringarList.get(0).getSsn());
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(kontrollerProvtagningDoseringarList.get(0).getSsnType().toString());
        contentStream.endText();
        yHold -= leading;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }

    public void createDosePDFDetails() throws IOException {
        final float startX = page.getCropBox().getLowerLeftX() + 30;
        //float endX = page.getCropBox().getUpperRightX() - 30;
        final float startX2 = startX + 280f;
        final float xTab1 = startX + 140f;
        final float xTab2 = startX2 + 160f;
        float yHold = y;

        if(contentStream != null) {
            /** First page **/
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.setLeading(leading);
            writeHeader();
            writePatientInfo();     // written only once, on page 1

            int arraySize = kontrollerProvtagningDoseringarList.size();
            int currentOID = 0;
            int oidCounter = 0;

            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {
                /* PAL TEXT */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Pal Text:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getPalText());
                contentStream.endText();
                yHold = y;
                y -= leading;

                /* NÄSTA KONTROLL */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Nästa kontroll");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 100, y - fontHeight / 2);
                contentStream.stroke();
                y -= leading;

                /* inr IntervalID */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("inrIntervalId:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getInrIntervalId());
                contentStream.endText();
                yHold = y;

                /* ordination date */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2 + 15, yHold);
                contentStream.showText("ordination date:");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getOrdinationDate());
                contentStream.endText();
                y -= leading;

                /* date next visit */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("date next visit:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getDateNextVisit());
                contentStream.endText();
                y -= leading;

                /* ORDINATIONPERIOD */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Ordinationperiod");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 120, y - fontHeight / 2);
                contentStream.stroke();

                /* sätter OID */
                int oid = kontrollerProvtagningDoseringarList.get(arrayItem).getOid();
                if(currentOID != oid){
                    currentOID = oid;
                    String oidtext = "oId: " + currentOID + " (" + ++oidCounter + " av " + listGenerics.getTotOidItems() + ")";
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                    contentStream.newLineAtOffset(xTab1, y);
                    contentStream.showText(oidtext);
                    contentStream.endText();
                }
                else{
                    String oidtext = "oId: " + currentOID + " (" + oidCounter + " av " + listGenerics.getTotOidItems() + ")";
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER, 12f);
                    contentStream.newLineAtOffset(xTab1, y);
                    contentStream.showText(oidtext);
                    contentStream.endText();
                }
                y -= leading;

                /* medicin text */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("medicin text:");
                contentStream.newLineAtOffset(xTab1 - 40, 0);
                contentStream.showText(kontrollerProvtagningDoseringarList.get(arrayItem).getMedicinText());
                contentStream.endText();

                /* dose mode */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2 + 60, y);
                contentStream.showText("dose mode:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream,  kontrollerProvtagningDoseringarList.get(arrayItem).getDoseMode());
                contentStream.endText();
                y -= leading;

                /* WARAN DOSE */
                contentStream.beginText();
                yHold = y;
                contentStream.newLineAtOffset(startX, y);
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.showText("Waran Dose");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 73 , y - fontHeight / 2);
                contentStream.stroke();

                /* Monday - Sunday labels */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y - leading);
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.showText("Monday Dose:");
                contentStream.newLine();
                y -= leading;
                contentStream.showText("Tuesday Dose:");
                contentStream.newLine();
                y -= leading;
                contentStream.showText("Wednesday Dose:");
                contentStream.newLine();
                y -= leading;
                contentStream.showText("Thursday Dose:");
                contentStream.newLine();
                y -= leading;
                contentStream.showText("Friday Dose:");
                contentStream.newLine();
                y -= leading;
                contentStream.showText("Saturday Dose:");
                contentStream.newLine();
                y -= leading;
                contentStream.showText("Sunday Dose:");
                y -= leading;
                contentStream.endText();

                /* Monday - Sunday Dose VALUES */
                yHold -= leading;
                contentStream.beginText();
                contentStream.newLineAtOffset(startX + xTab1, yHold);
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getMondayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getTuesdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getWednesdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getThursdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getFridayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSaturdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSundayDose());
                contentStream.endText();

                /* WARAN DSOSE REDUCED */
                contentStream.beginText();
                yHold += leading;
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.showText("Waran Dose Reduced");
                contentStream.endText();
                contentStream.moveTo(startX2, yHold - fontHeight / 2);
                contentStream.lineTo(startX2 + 130f , yHold - fontHeight / 2);
                contentStream.stroke();
                yHold -= leading;

                /* Monday - Sunday Waran Dose Reduced labels */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Monday Dose:");
                contentStream.newLine();
                contentStream.showText("Tuesday Dose:");
                contentStream.newLine();
                contentStream.showText("Wednesday Dose:");
                contentStream.newLine();
                contentStream.showText("Thursday Dose:");
                contentStream.newLine();
                contentStream.showText("Friday Dose:");
                contentStream.newLine();
                contentStream.showText("Saturday Dose:");
                contentStream.newLine();
                contentStream.showText("Sunday Dose:");
                contentStream.endText();

                /* Monday - Sunday Waran Dose Reduced VALUES */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2 + xTab1, yHold);
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getMondayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getTuesdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getWednesdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getThursdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getFridayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSaturdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSundayDoseReduced());
                contentStream.endText();
                y -= leading;

                /* waran reduced type text */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("reduced type text:");

                /* waran reduced type txt value */
                contentStream.newLineAtOffset(xTab1 , 0);
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getReducedTypeTXT());
                contentStream.endText();
                y -= leading;

                /* Comment Waran */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Comment dose:");
                contentStream.endText();
                String commentText = kontrollerProvtagningDoseringarList.get(arrayItem).getCommentDose();
                if(commentText != null) {
                    commentText = URLDecoder.decode(commentText, StandardCharsets.ISO_8859_1);

                    StringWriter1 stringWriter = new StringWriter1(commentText, 55, false);
                    List<String> polishedStringArray = stringWriter.getPolishedStringArray();
                    int commentLeading = 15;
                    boolean isFirstText = true;
                    boolean firstTextSliceInRow = true;

                    for (var item : polishedStringArray) {
                        if(isFirstText) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(xTab1, y);
                            contentStream.setFont(PDType1Font.COURIER_BOLD, 9f);
                            contentStream.setLeading(15f);

                            contentStream.showText(item);
                            contentStream.endText();
                            isFirstText = false;
                        }
                        else {
                            if(firstTextSliceInRow){
                                y -= commentLeading;
                                contentStream.beginText();
                                contentStream.newLineAtOffset(startX, y);
                                contentStream.showText(item);
                                contentStream.endText();
                                firstTextSliceInRow = false;
                            }
                            else {
                                contentStream.beginText();
                                contentStream.newLineAtOffset(startX2 + 10, y);
                                contentStream.showText(item);
                                contentStream.endText();
                                firstTextSliceInRow = true;

                            }
                        }
                    }
                }
                y -= leading;

                /* Comment Waran reduced */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.setLeading(leading);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Comment Reduced dose:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getReducedComment());
                contentStream.endText();
                y -= leading;

                /* PK(INR) */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("PK(INR)");
                contentStream.endText();
                y -= leading;
                yHold = y;

                contentStream.moveTo(startX, y + fontHeight );
                contentStream.lineTo(startX + 50, y + fontHeight );
                contentStream.stroke();

                /* INR labels and values */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("INR value:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getInrValue());
                contentStream.endText();
                y -= leading;
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("laboratoryId:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getLaboratoryId());
                contentStream.endText();
                y -= leading;

                /* inr method */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("INR method:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream,  kontrollerProvtagningDoseringarList.get(arrayItem).getInrMethod());
                contentStream.endText();
                y -= leading;

                /* inr date */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("INR Date:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getInrDate());
                contentStream.endText();
                yHold -= leading;

                /* medicine tyhpe */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("medicine Type:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                contentStream.showText(kontrollerProvtagningDoseringarList.get(arrayItem).getMedicineTypeText());
                contentStream.endText();
                yHold -= leading;

                /* analysis pathol */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("analysis pathol:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getAnalysisPathol());
                contentStream.endText();

                yHold -= leading;
                y = Math.min(y, yHold);

                /* Creatinin */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Creatinin");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 67, y - fontHeight / 2);
                contentStream.stroke();
                y -= leading;
                yHold = y;

                float xTab11 = xTab1 + 30f;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Labresult ID:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab11, y);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getLabresultId());
                contentStream.endText();
                y -= leading;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Planed Date Creatinin:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab11, y);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getPlanedDateCreatinin());
                contentStream.endText();
                y -= leading;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Creatinin:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab11, y);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getCreatinin());
                contentStream.endText();
                y -= leading;

                /* test Date Creatinin */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Test date Creatinin:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getTestDateCreatinin());
                contentStream.endText();
                yHold -= leading;

                /* egfr */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("egfr:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getEgfr());
                contentStream.endText();
                yHold -= leading;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Follow up date:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getFollowupDate());
                contentStream.endText();
                yHold -= leading;

                y = Math.min(y, yHold);
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Comment Creatinin:");
                contentStream.newLineAtOffset(xTab1,0);
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getCommentCreatinin());
                contentStream.endText();
                y -= leading;

                /* LABRESULT */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Labresult");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 65, y - fontHeight / 2);
                contentStream.stroke();
                y -= leading;
                yHold = y;

                /* analysisCodeLab */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("analysis Code:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getAnalysisCodeLab());
                contentStream.endText();
                y -= leading;

                /* sample value */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("sample value:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSampleValueLab());
                contentStream.endText();

                /* sample value text */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("sample value text:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, y);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSampleValueText());
                contentStream.endText();
                y -= leading;

                /* analysis Comment */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Analysis comment:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getAnalysisCommentLab());
                contentStream.endText();
                y -= leading;

                /* CHANGED INR INTERVAL */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Changed Inr interval");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 145, y - fontHeight / 2);
                contentStream.stroke();
                y -= leading;
                yHold = y;

                /* system ordination sugg. */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("sys. ordination sugg.:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSystemsOrdinationSugg());
                contentStream.endText();

                /* system interval sugg. */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("sys. interval sugg.:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, y);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getSystemsIntervalSugg());
                contentStream.endText();
                y -= leading;

                /* user ordination */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("user ordination:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getUserOrdination());
                contentStream.endText();

                /* user interval */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("user interval:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, y);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getUserInterval());
                contentStream.endText();

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
        String filenameWithSSN = fileWithoutExtension + "_" + kontrollerProvtagningDoseringarList.get(0).getSsn() + ".pdf";
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
