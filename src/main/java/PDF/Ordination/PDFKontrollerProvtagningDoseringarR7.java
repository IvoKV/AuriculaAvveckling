package PDF.Ordination;

import OrdinationperiodLKM.KontrollerProvtagningDoseringarR7;
import auxilliary.*;
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

public class PDFKontrollerProvtagningDoseringarR7 {
    List<KontrollerProvtagningDoseringarR7> kontrollerProvtagningDoseringarR7List;

    private ListGenerics listGenerics;
    private final String pdfPathFileName = "out/R7/PDFKontrollerProvtagningDoseringarR7.pdf";

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

    public PDFKontrollerProvtagningDoseringarR7(List<KontrollerProvtagningDoseringarR7> kontrollerProvtagningDoseringarR7List) throws IOException {
        this.kontrollerProvtagningDoseringarR7List = kontrollerProvtagningDoseringarR7List;
        this.listGenerics = new ListGenerics(Collections.unmodifiableList(kontrollerProvtagningDoseringarR7List));

        /** Initialize document and first page **/
        this.document = new PDDocument();
        this.page = new PDPage();

        /** Initialize content stream, first page **/
        this.contentStream = new PDPageContentStream(document, page);
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
        float xTab1 = startX + 165f;
        float yHold = y;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER, 12f);
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Förnamn:");
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(xTab1, y);
        contentStream.showText(kontrollerProvtagningDoseringarR7List.get(0).getPatFirstName());
        contentStream.endText();
        yHold = y;
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(xTab1, y);
        contentStream.showText(kontrollerProvtagningDoseringarR7List.get(0).getPatLastName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN:");
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 150, yHold);
        contentStream.showText(kontrollerProvtagningDoseringarR7List.get(0).getSsn());
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN Type:");
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 150, yHold);
        contentStream.showText(kontrollerProvtagningDoseringarR7List.get(0).getSsnType().toString());
        contentStream.endText();
        yHold -= fontSize / 2;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }

    public void createKontrollerProvtagningDoseringarR7PDFDetails() throws IOException, GeneralBefattningReadJSONException {
        final float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;
        final float startX2 = startX + 280f;
        final float xTab1 = startX + 140f;
        final float xTab2 = startX2 + 100f;
        float yHold = y;

        if(contentStream != null) {
            /** First page **/
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.setLeading(leading);
            writeHeader();
            writePatientInfo();     // written only once, on page 1

            int arraySize = kontrollerProvtagningDoseringarR7List.size();
            int currentOID = 0;
            int oidCounter = 0;

            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {
                /* ANSVARIG */
                GeneralBefattningReadJSON genJSON1 = new GeneralBefattningReadJSON(kontrollerProvtagningDoseringarR7List.get(arrayItem).getOpCreatedBy());
                StringBuilder sb1 = new StringBuilder();
                sb1.append(genJSON1.getGeneralBefattningFirstName() + " ");
                sb1.append(genJSON1.getGeneralBefattningLastName());

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Ansvarig (Ordp):");
                contentStream.newLineAtOffset(xTab1 - 40, 0);
                contentStream.showText(sb1.toString());
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2 + 30, y);
                contentStream.showText("Title:");
                contentStream.newLineAtOffset(80, 0);
                TextShower.showString(contentStream, genJSON1.getGeneralBefattningTitel());
                contentStream.endText();
                sb1 = null;
                genJSON1 = null;
                yHold = y;
                yHold -= fontSize / 2;

                /*  --- line ---   */
                contentStream.setLineWidth(0.5f);
                contentStream.moveTo(startX, yHold);
                contentStream.lineTo(endX, yHold);
                contentStream.stroke();
                y = yHold - leading;

                /** NÄSTA KONTROLL **/
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Nästa kontroll");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 100, y - fontHeight / 2);
                contentStream.stroke();
                y -= leading;

                /* ORDINATION DATE */
                contentStream.beginText();
                contentStream.setFont(fontNormal, fontSize);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("ordination date:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getOrdinationDate());
                contentStream.endText();

                /* -> DATE NEXT VISIT */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("date next visit:");
//                contentStream.endText();
//                contentStream.beginText();
                contentStream.newLineAtOffset(165, 0);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getDateNextVisit());
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
                int oid = kontrollerProvtagningDoseringarR7List.get(arrayItem).getOid();
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

                /* MEDICIN TEXT */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("medicin text:");
                contentStream.newLineAtOffset(xTab1 - 40, 0);
                contentStream.showText(kontrollerProvtagningDoseringarR7List.get(arrayItem).getMedicinText());
                contentStream.endText();

                /* DOSE MODE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("dose mode:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream,  kontrollerProvtagningDoseringarR7List.get(arrayItem).getDoseMode());
                contentStream.endText();
                y -= leading;

                /* MEDICIN TYP */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Medicin typ:");
                contentStream.newLineAtOffset(xTab1 - 40, 0);
                TextShower.showString(contentStream,  kontrollerProvtagningDoseringarR7List.get(arrayItem).getMedicineTypeText());
                contentStream.endText();
                y -= leading;

                /* START DATE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Start date:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream,  kontrollerProvtagningDoseringarR7List.get(arrayItem).getStartDate());
                contentStream.endText();

                /* -> END DATE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("End date:");
                contentStream.newLineAtOffset(xTab1 - 40, 0);
                TextShower.showDate(contentStream,  kontrollerProvtagningDoseringarR7List.get(arrayItem).getEndDate());
                contentStream.endText();
                y -= leading;

                /** WARAN **/
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
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getMondayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getTuesdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getWednesdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getThursdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getFridayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getSaturdayDose());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getSundayDose());
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
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getMondayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getTuesdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getWednesdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getThursdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getFridayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getSaturdayDoseReduced());
                contentStream.newLine();
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getSundayDoseReduced());
                contentStream.endText();
                y -= leading;

                /* waran reduced type text */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("reduced type text:");

                /* waran reduced type txt value */
                contentStream.newLineAtOffset(xTab1 , 0);
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getReducedTypeTXT());
                contentStream.endText();
                y -= leading;

                /* Comment Waran */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Comment dose:");
                contentStream.endText();
                String commentText = kontrollerProvtagningDoseringarR7List.get(arrayItem).getCommentDose();
                if(commentText != null) {
                    commentText = URLDecoder.decode(commentText, StandardCharsets.ISO_8859_1);

                    TextWrapper stringWriter = new TextWrapper(commentText, 55, false);
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
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getReducedComment());
                contentStream.endText();
                y -= leading;

                /* PK(INR) */
//                contentStream.beginText();
//                contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
//                contentStream.newLineAtOffset(startX, y);
//                contentStream.showText("PK(INR)");
//                contentStream.endText();
//                y -= leading;
//                yHold = y;
//
//                contentStream.moveTo(startX, y + fontHeight );
//                contentStream.lineTo(startX + 50, y + fontHeight );
//                contentStream.stroke();
//
//                /* INR labels and values */
//                contentStream.beginText();
//                contentStream.setFont(PDType1Font.COURIER, 12f);
//                contentStream.newLineAtOffset(startX, y);
//                contentStream.showText("INR value:");
//                contentStream.newLineAtOffset(xTab1, 0);
//                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getInrValue());
//                contentStream.endText();
//                y -= leading;
//                contentStream.beginText();
//                contentStream.newLineAtOffset(startX, y);
//                contentStream.showText("laboratoryId:");
//                contentStream.newLineAtOffset(xTab1, 0);
//                TextShower.showString(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getLaboratoryId());
//                contentStream.endText();
//                y -= leading;
//
//                /* inr method */
//                contentStream.beginText();
//                contentStream.newLineAtOffset(startX, y);
//                contentStream.showText("INR method:");
//                contentStream.newLineAtOffset(xTab1, 0);
//                TextShower.showString(contentStream,  kontrollerProvtagningDoseringarR7List.get(arrayItem).getInrMethod());
//                contentStream.endText();
//                y -= leading;
//
//                /* inr date */
//                contentStream.beginText();
//                contentStream.newLineAtOffset(startX2, yHold);
//                contentStream.showText("INR Date:");
//                contentStream.endText();
//                contentStream.beginText();
//                contentStream.newLineAtOffset(xTab2, yHold);
//                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getInrDate());
//                contentStream.endText();
//                yHold -= leading;
//
//                /* medicine tyhpe */
//                contentStream.beginText();
//                contentStream.newLineAtOffset(startX2, yHold);
//                contentStream.showText("medicine Type:");
//                contentStream.endText();
//                contentStream.beginText();
//                contentStream.newLineAtOffset(xTab2, yHold);
//                contentStream.showText(kontrollerProvtagningDoseringarR7List.get(arrayItem).getMedicineTypeText());
//                contentStream.endText();
//                yHold -= leading;
//
//                /* analysis pathol */
//                contentStream.beginText();
//                contentStream.newLineAtOffset(startX2, yHold);
//                contentStream.showText("analysis pathol:");
//                contentStream.endText();
//                contentStream.beginText();
//                contentStream.newLineAtOffset(xTab2, yHold);
//                TextShower.showString(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getAnalysisPathol());
//                contentStream.endText();
//
//                yHold -= leading;
//                y = Math.min(y, yHold);

                /* Mätvärde (Creatinin) */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Mätvärde (Creatinin)");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 140, y - fontHeight / 2);
                contentStream.stroke();
                y -= leading;
                yHold = y;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Creatinin:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab1, y);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getCreatinin());
                contentStream.endText();

                /* -> test Date Creatinin */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Test date Creatinin:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2 + xTab1, y);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getTestDateCreatinin());
                contentStream.endText();
                y -= leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Comment Creatinin:");
                contentStream.newLineAtOffset(xTab1,0);
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getCommentCreatinin());
                contentStream.endText();
                y -= leading;

                /** LABRESULT **/
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

                /* PK(INR) INTVALUE */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("PK(INR):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showFloatToText(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getInrValue());
                contentStream.endText();

                /* -> INR DATE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("INR date:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, y);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getInrDate());
                contentStream.endText();
                y -= leading;

                /* PATOLOG VÄRDE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Pathol analys:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getAnalysisPathol());
                contentStream.endText();
                y -= leading;

                /* LABREMISS COMMENT */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Labremiss comment:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getLabremComment());
                contentStream.endText();
                y -= leading;

                /* SPECIMEN COMMENT */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Specimen comment:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getSpecimenComment());
                contentStream.endText();
                y -= leading;

                /* ANALYSIS COMMENT */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Prov comment:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarR7List.get(arrayItem).getAnalysisCommentLab());
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
        String filenameWithSSN = fileWithoutExtension + "_" + kontrollerProvtagningDoseringarR7List.get(0).getSsn() + ".pdf";
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
            contentStream.newLineAtOffset(endX - 15, yCordinate);
            contentStream.showText(sb.toString());
            contentStream.endText();
            contentStream.close();
            pagecounter++;
        }
    }
}
