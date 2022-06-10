package PDF.MV;

import MV.HemorrhagesR7;
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

public class PDFHemorrhagesR7 {
    private List<HemorrhagesR7> hemorrhagesR7List = null;

    private final String pdfPathFileName = "out/R7/PDFMatvardeL.pdf";

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

    public PDFHemorrhagesR7(List<HemorrhagesR7> hemorrhagesR7List) throws IOException {
        this.hemorrhagesR7List = hemorrhagesR7List;

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
        contentStream.showText("Hemorrhages");
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
        contentStream.showText(hemorrhagesR7List.get(0).getPatFirstName());
        contentStream.endText();
        yHold = y;
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX, y);
        contentStream.showText("Efternamn:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(hemorrhagesR7List.get(0).getPatLastName());
        contentStream.endText();
        y -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(hemorrhagesR7List.get(0).getSsn());
        contentStream.endText();
        yHold -= leading;

        contentStream.beginText();
        contentStream.newLineAtOffset(startX2 + 30, yHold);
        contentStream.showText("SSN Type:");
        contentStream.newLineAtOffset(xTab1, 0);
        contentStream.showText(hemorrhagesR7List.get(0).getSsnType().toString());
        contentStream.endText();
        yHold -= fontSize / 2;

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(startX, yHold);
        contentStream.lineTo(endX, yHold);
        contentStream.stroke();
        yHold -= leading;

        y = Math.min(y, yHold);
    }


    public void createHemorrhagesR7() throws IOException, GeneralBefattningReadJSONException {
        final float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;
        final float startX2 = startX + 300f;
        final float xTab1 = startX + 185f;
        final float xTab2 = 200f;
        float yHold = y;

        if (contentStream != null) {
            /** First page **/
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.setLeading(leading);
            writeHeader();
            writePatientInfo();     // written only once, on page 1

            int arraySize = hemorrhagesR7List.size();

            for(int arrayItem = 0; arrayItem < arraySize; arrayItem++ ) {
//                /* -- HEMORRHAGES -- */
//                contentStream.beginText();
//                contentStream.setFont(fontBold, fontSize);
//                contentStream.newLineAtOffset(startX, y);
//                contentStream.showText("Lever njursjukdom");
//                contentStream.endText();
//
//                contentStream.moveTo(startX, y - leading / 2);
//                contentStream.lineTo(110, y - leading / 2);
//                contentStream.stroke();
//                y -= leading * 1.5;


                /* -- VÄNSTER KOLUMN --*/
                /* Levernjursjukdom (H1) */
                contentStream.beginText();
                contentStream.setLeading(leading);
                contentStream.newLineAtOffset(startX, y);
                contentStream.setFont(fontNormal, fontSize);
                contentStream.showText("Levernjursjukdom (H1):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getLeverNjursjukdom());
                contentStream.endText();
                yHold = y;
                y -= leading;

                /* ETANOLMISSBRUK */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Etanolmissbruk (E1):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getEtanolmissbruk());
                contentStream.endText();
                y -= leading;

                /* MALIGNINITET */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Malignitet (M):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getMalignitet());
                contentStream.endText();
                y -= leading;

                /* REDUCERAT_TROMBOCYTANTAL_FUNK */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Red. Trombocytantal (R1):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getReduceratTrombocytantalFunktion());
                contentStream.endText();
                y -= leading;

                /* TIDIGARE_BLÖDNING */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Red. Tidigare blödning (R2):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getTidigareBlodning());
                contentStream.endText();
                y -= leading;

                /* -- HÖGER KOLUMN --*/
                /* HYPERTONI */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Hypertoni (H2):");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getHypertoni());
                contentStream.endText();
                yHold -= leading;

                /* ANEMI */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Anemi (A):");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getAnemi());
                contentStream.endText();
                yHold -= leading;

                /* GENETISKAFAKTORER */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Genetiska fakt. (G):");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getGenetiskaFaktorer());
                contentStream.endText();
                yHold -= leading;

                /* STOR_RISK_FÖR_FALL */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Stor risk för fall (E2):");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getStorRiskForFall());
                contentStream.endText();
                yHold -= leading;

                /* STROKE */
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Stroke (S):");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getStroke());
                contentStream.endText();

                /* CREATED BY */
                StringBuilder sbcr = new StringBuilder();
                GeneralBefattningReadJSON gbj = new GeneralBefattningReadJSON(hemorrhagesR7List.get(arrayItem).getCreatedBy());
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

                /* UPDATED BY */
                StringBuilder sbupd = new StringBuilder();
                GeneralBefattningReadJSON gbj2 = new GeneralBefattningReadJSON(hemorrhagesR7List.get(arrayItem).getUpdatedBy());
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
                contentStream.showText("Created:");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(hemorrhagesR7List.get(arrayItem).getTsCreated().toString().substring(0, 19));
                contentStream.endText();
                y -= leading * 2;
/*
                /* ---- CHADS2 ---- * /
                contentStream.beginText();
                contentStream.setFont(fontBold, fontSize);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("CHADS2");
                contentStream.endText();
                contentStream.moveTo(startX, y - leading / 2);
                contentStream.lineTo(73, y - leading / 2);
                contentStream.stroke();
                y -= leading * 1.5;
                yHold = y;

                /* -- VÄNSTER KOLUMN --* /
                /* HJÄRTSVIKT * /
                contentStream.beginText();
                contentStream.setFont(fontNormal, fontSize);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Hjärtsvikt (C):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getChaC());
                contentStream.endText();
                y -= leading;

                /* HYPERTONI * /
                contentStream.beginText();
                contentStream.setFont(fontNormal, fontSize);
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Hypertoni (H):");
                contentStream.newLineAtOffset(xTab1, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getChaH());
                contentStream.endText();
                y -= leading;

                /* -- HÖGER KOLUMN --* /
                /* DIABETES * /
                contentStream.beginText();
                contentStream.setFont(fontNormal, fontSize);
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Diabetes (D):");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getChaD());
                contentStream.endText();
                yHold -= leading;

                /* STROKE * /
                contentStream.beginText();
                contentStream.setFont(fontNormal, fontSize);
                contentStream.newLineAtOffset(startX2, yHold);
                contentStream.showText("Stroke (S):");
                contentStream.newLineAtOffset(xTab2, 0);
                TextShower.showString(contentStream, hemorrhagesR7List.get(arrayItem).getChaS());
                contentStream.endText();
                yHold -= leading;

                /* CREATED BY * /
                StringBuilder sbCHAcr = new StringBuilder();
                GeneralBefattningReadJSON gbjCHAcr = new GeneralBefattningReadJSON(hemorrhagesR7List.get(arrayItem).getChaCreatedBy());
                sbCHAcr.append(gbjCHAcr.getGeneralBefattningFirstName() + " ");
                sbCHAcr.append(gbjCHAcr.getGeneralBefattningLastName());
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Created by:");
                contentStream.newLineAtOffset(100, 0);
                TextShower.showString(contentStream, sbCHAcr.toString());
                sbCHAcr = null;
                gbjCHAcr = null;
                contentStream.endText();

                /* UPDATED BY * /
                StringBuilder sbCHAupd = new StringBuilder();
                GeneralBefattningReadJSON gbjCHAupd = new GeneralBefattningReadJSON(hemorrhagesR7List.get(arrayItem).getChaUpdatedBy());
                sbCHAupd.append(gbjCHAupd.getGeneralBefattningFirstName() + " ");
                sbCHAupd.append(gbjCHAupd.getGeneralBefattningLastName() + " ");
                contentStream.beginText();
                contentStream.newLineAtOffset(startX2, y);
                contentStream.showText("Updated by:");
                contentStream.newLineAtOffset(100, 0);
                TextShower.showString(contentStream, sbCHAupd.toString());
                sbCHAupd = null;
                gbjCHAupd = null;
                contentStream.endText();
                y -= leading;

                /* TSCREATED * /
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText("Created:");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(hemorrhagesR7List.get(arrayItem).getChaTsCreated().toString().substring(0, 19));
                contentStream.endText();
                y -= leading * 2;

                 */
            }

            contentStream.close();
        }


        FileOperations fop = new FileOperations(pdfPathFileName);
        String fileWithoutExtension =  fop.getFilenameWithoutExtension(); // kontrollerProvtagningDoseringarList.get(0).getSsn();
        fop = null;
        String filenameWithSSN = fileWithoutExtension + "_" + hemorrhagesR7List.get(0).getSsn() + ".pdf";

        writePageNumbers();

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
