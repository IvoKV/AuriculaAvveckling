package PDFPersonInCharge;

import Person.PersonInCharge;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFPersonInChargeTioHundra {
    List<PersonInCharge> personsInCharge;
    private final String pdfPathFileName = "PDFPersonInChargeTioHundra.pdf";

    private float x = 0;
    private float y = 750;
    private final float leading = 20;
    private float fontSize = 12;
    private float fontHeight = fontSize;

    private float startX = 0;
    private float endX = 0;

    private PDFont fontNormal = PDType1Font.COURIER;
    private PDFont fontBold = PDType1Font.COURIER_BOLD;

    private PDDocument document = null;
    private PDPage page = null;
    private PDPageContentStream contentStream = null;
    private int sidaNo = 0;

    public PDFPersonInChargeTioHundra(List<PersonInCharge> personsInCharge) throws IOException {
        this.personsInCharge = personsInCharge;

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
        contentStream.showText("Befattningar hos vårdgivaren TIO 100");
        yCordinate -= fontHeight;
        contentStream.endText();

        contentStream.moveTo(startX, yCordinate);
        contentStream.lineTo(endX, yCordinate);
        contentStream.stroke();
        yCordinate -= leading;
        y = yCordinate;
    }

    public void createPersonInChargePDFDetails() throws IOException {
        if(contentStream != null) {
            float startX = page.getCropBox().getLowerLeftX() + 30;
            float tabSpaceX = 100;

            /** First page **/
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(fontBold, fontSize + 2);
            writeHeader();

            /** SKRIV UT BEFATTNINGAR (LISTA) **/
            personsInCharge.stream().forEach(item -> {
                try {
                    /* FÖRNAMN */
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER, 12);
                    contentStream.newLineAtOffset( startX, y);
                    contentStream.showText("Förnamn:");
                    contentStream.newLineAtOffset( tabSpaceX, 0);
                    contentStream.showText(item.getFirstName());
                    contentStream.endText();
                    y -= leading;

                    /* EFTERNAMN */
                    contentStream.beginText();
                    contentStream.newLineAtOffset( startX, y);
                    contentStream.showText("Efternamn:");
                    contentStream.newLineAtOffset( tabSpaceX, 0);
                    contentStream.showText(item.getLastName());
                    contentStream.endText();
                    y -= leading;

                    /* BEFATTNING */
                    contentStream.beginText();
                    contentStream.newLineAtOffset( startX, y);
                    contentStream.showText("Befattning:");
                    contentStream.newLineAtOffset( tabSpaceX, 0);
                    contentStream.showText(item.getBefattningskod());
                    contentStream.endText();
                    y -= leading * 2;

                    if(y <= leading * 3){
                        contentStream.close();
                        this.page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        writeHeader();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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
