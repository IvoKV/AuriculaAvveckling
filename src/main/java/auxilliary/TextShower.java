package auxilliary;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

public class TextShower {
    private TextShower() {
    }

    public static void showNL(PDPageContentStream contentStream, String text) throws IOException {
        if (text != null && text.length() != 0) {
            contentStream.showText(text);
            contentStream.newLine();
        }
    }

    public static void showString(PDPageContentStream contentStream, String text) throws IOException {
        if (text != null && text.length() != 0) {
            contentStream.showText(text);
        }
        else {
            contentStream.showText("no value");
        }
    }

    public static void showIntToText(PDPageContentStream contentStream, int value) throws IOException {
        String text = String.valueOf(value);
        if (text != null && text.length() != 0) {
            contentStream.showText(text);
        }
        else {
            contentStream.showText("no value");
        }
    }

    public static void showFloatToText(PDPageContentStream contentStream, float value) throws IOException {
        if(value == 0.0){
            String text = "no value";
            contentStream.showText(text);
        }
        else {
            String text = String.valueOf(value);
            contentStream.showText(text);
        }
    }

    public static void showDate(PDPageContentStream contentStream, Date date) throws IOException {
        if (date != null) {
            String text = String.valueOf(date);
            contentStream.showText(text);
        }
        else {
            contentStream.showText("no value");
        }
    }

    public static void showComment(PDPageContentStream contentStream, String comment) throws IOException {
        if(comment == null || comment.length() == 0){
            contentStream.showText("no commment");
        }
        else {
            contentStream.showText(comment);
        }
    }

    public static void showBigdecimal(PDPageContentStream contentStream, BigDecimal bigDecimal) throws IOException {
        if(bigDecimal == null){
            contentStream.showText("no commment");
        }
        else {
            String bdString = bigDecimal.toString();
            contentStream.showText(bdString);
        }
    }

    public static boolean stringIsNotNull(String text) {
        return (text != null && text.length() > 0);
    }
}
