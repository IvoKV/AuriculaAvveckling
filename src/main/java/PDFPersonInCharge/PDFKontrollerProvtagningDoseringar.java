package PDFPersonInCharge;

import auxilliary.TextShower;
import ordination.KontrollerProvtagningDoseringar.KontrollerProvtagningDoseringar;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFKontrollerProvtagningDoseringar {
    List<KontrollerProvtagningDoseringar> kontrollerProvtagningDoseringarList;
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
        contentStream.newLineAtOffset(startX2, yHold);
        contentStream.showText("SSN:");
        contentStream.newLineAtOffset(xTab1 - 20, 0);
        contentStream.showText(kontrollerProvtagningDoseringarList.get(0).getSsn());
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1 - 20, 0);
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
        float endX = page.getCropBox().getUpperRightX() - 30;
        final float startX2 = startX + 280f;
        final float xTab1 = startX + 140f;
        final float xTab2 = startX2 + 160f;
        float yHold = y;
        //int arrayItem = 0;

        if(contentStream != null) {

            /** First page **/
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(fontBold, fontSize + 2);
            contentStream.setLeading(leading);
            writeHeader();
            writePatientInfo();     // written only once, on page 1

            int arraySize = kontrollerProvtagningDoseringarList.size();

            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {
                /* ut Pal Text */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Pal Text:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getPalText());
                contentStream.endText();
                yHold = y;
                y -= leading;

                /* inr IntervalID */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("inrIntervalId:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showIntToText(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getInrIntervalId());
                contentStream.endText();
                y -= leading;

                /* date next visit */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("date next visit:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getDateNextVisit());
                contentStream.endText();
                yHold -= leading;

                /* ordination date */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("ordination date:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getOrdinationDate());
                contentStream.endText();
                yHold -= leading;
                y = Math.min(y, yHold);

                /* Waran Dose */
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
                //xTab1 += 25f;
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

                /* Waran Dose Reduced */
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

                // todo: search if there are "long" comments. They must be handled with line separation processing
                /* Comment Waran */
                contentStream.beginText();
                y -= leading;
                yHold = y;
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Comment dose:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getCommentDose());
                contentStream.endText();
                y -= leading;

                /* Comment Waran reduced comment */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Comment Reduced dose:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showComment(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getReducedComment());
                contentStream.endText();
                y -= leading;

                /* INR */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("PK(INR) value:");
                contentStream.endText();
                y -= leading;

                contentStream.moveTo(startX, y + fontHeight );
                contentStream.lineTo(startX + 100, y + fontHeight );
                contentStream.stroke();

                /* INR labels and values */
                contentStream.beginText();
                yHold = y;
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
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("INR method:");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream,  kontrollerProvtagningDoseringarList.get(arrayItem).getInrMethod());
                contentStream.endText();
                y -= leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("INR Date:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                TextShower.showDate(contentStream, kontrollerProvtagningDoseringarList.get(arrayItem).getInrDate());
                contentStream.endText();
                yHold -= leading;
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("medicine Type:");
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xTab2, yHold);
                contentStream.showText(kontrollerProvtagningDoseringarList.get(arrayItem).getMedicineTypeText());
                contentStream.endText();
                yHold -= leading;
                y = Math.min(y, yHold);

                /* Creatinin */
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 12f);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Creatinin:");
                contentStream.endText();
                contentStream.moveTo(startX, y - fontHeight / 2);
                contentStream.lineTo(startX + 71, y - fontHeight / 2);
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

                /*egfr */
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

                break;
//                        /* EFTERNAMN */
//                        contentStream.beginText();
//                        contentStream.newLineAtOffset(startX, y);
//                        contentStream.showText("Efternamn:");
//                        contentStream.newLineAtOffset(tabSpaceX, 0);
//                        contentStream.showText(item.getLastName());
//                        contentStream.endText();
//                        y -= leading;
//
//                        /* BEFATTNING */
//                        contentStream.beginText();
//                        contentStream.newLineAtOffset(startX, y);
//                        contentStream.showText("Befattning:");
//                        contentStream.newLineAtOffset(tabSpaceX, 0);
//                        //contentStream.showText(item.getBefattningskod());
//                        contentStream.endText();
//                        y -= leading * 2;

//                        if (y <= leading * 3) {
//                            contentStream.close();
//                            this.page = new PDPage();
//                            document.addPage(page);
//                            contentStream = new PDPageContentStream(document, page);
//                            writeHeader();
//                        }



            }
            contentStream.close();
            writePageNumbers();
        }
        document.save(pdfPathFileName);
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
