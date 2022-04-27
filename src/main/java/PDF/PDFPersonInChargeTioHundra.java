package PDF;


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
    private float leading = 20;
    private float fontSize = 12;
    private float fontHeight = fontSize;

    private float startX = 0;
    private float endX = 0;

    private PDFont fontNormal = PDType1Font.COURIER;
    private PDFont fontBold = PDType1Font.COURIER_BOLD;

    private PDDocument document = null;
    private PDPage page = null;
    private PDPageContentStream contentStream = null;


    public PDFPersonInChargeTioHundra(List<PersonInCharge> personsInCharge) throws IOException {
        this.personsInCharge = personsInCharge;
        this.document = new PDDocument();
        this.page = new PDPage();
        writeHeader();
    }

    private void writeHeader() throws IOException {
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(fontBold, fontSize + 2);

        float yCordinate = page.getCropBox().getUpperRightY() - 30;
        float startX = page.getCropBox().getLowerLeftX() + 30;
        float endX = page.getCropBox().getUpperRightX() - 30;

        contentStream.beginText();
        contentStream.newLineAtOffset( startX, yCordinate);
        contentStream.showText("Befattningar hos vårdgivaren TIO 100");
        yCordinate -= fontHeight;
        contentStream.endText();

        contentStream.moveTo(startX, yCordinate);
        contentStream.lineTo(endX, yCordinate);
        contentStream.stroke();
        yCordinate -= leading;

        contentStream.close();
        document.save(pdfPathFileName);
        document.close();
    }

    public void createPersonInChargePDFDetails() throws IOException {

        //contentStream = new PDPageContentStream(document, page);
        if(contentStream != null) {
            contentStream.setFont(PDType1Font.COURIER, 12);
            y -= 24;
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);

            // todo: fortsätt härifrån
            personsInCharge.get(0).getBefattningskod();
            contentStream.showText("Hello World!");
            contentStream.endText();
            contentStream.close();
        }
        //document.save(savePDFPathName);
        document.close();
    }
}
