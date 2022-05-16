package auxilliary;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringWriter1 {
    private String defaultRaw;
    private String defaultEncoded;
    private String rawString;
    private String polishedString;
    private List<String> textArray;
    private int sliceLength;

    private String firstRow, secondRow, thirdRow;

    public StringWriter1(String rawString, int sliceLength) {
        //this.defaultRaw = "Ditt%20PK%28INR%29-v%E4rde%20ligger%20f%F6r%20h%F6gt%2C%20tag%20d%E4rf%F6r%201%20tablett%20mindre%20endast%20den%20dagen%20du%20f%E5r%20det%20h%E4r%20brevet%20och%20f%F6lj%20d%E4refter%20schemat.%20V%E4nligen%20ring%20medicinmottagningen%20m%E5n-fre%20kl%209-11%20p%E5%20tele%3A%200176-32%2060%2092%20f%F6r%20att%20meddela%20oss%20ditt%20telefonnummer%20om%20vi%20beh%F6ver%20n%E5%20dig%20f%F6r%20br%E5dskande%20besked%20ang%E5ende%20din%20ordination.";
        this.rawString = rawString;
        this.sliceLength = sliceLength;
        this.textArray = new ArrayList<>();

        if(rawString.length() > 0){
            polishedString = ensureOneWhiteSpaceOnly (encodeRawString(rawString));
            buildTextRowArray(polishedString);
        }
    }

    private String encodeRawString(String raw){
        defaultEncoded = URLDecoder.decode(raw, StandardCharsets.ISO_8859_1);
        return defaultEncoded;
    }

    private void buildTextRowArray(String procText){
        int startX = 0;
        int lastIdx = 0;
        int textLength = procText.length();

        while(lastIdx != -1) {
            String stringSlice;
            try {
                stringSlice = procText.substring(startX, startX + sliceLength);
            }
            catch (StringIndexOutOfBoundsException e){
                stringSlice = procText.substring(startX);
            }

            lastIdx = stringSlice.lastIndexOf(" ");

            System.out.println("Test lastIdx: " + lastIdx);
            if(lastIdx > 0) {
                firstRow = stringSlice.substring(0, lastIdx);
            }
            else {
                firstRow = stringSlice.substring(0);
            }
            startX += lastIdx;
            textArray.add(firstRow);
            startX++;
        }
/*
        //startX = ++lastIdx;
        startX++;
        stringSlice = procText.substring(startX, startX + sliceLength);
        lastIdx = stringSlice.lastIndexOf(" ");

        System.out.println("Test lastIdx: " + lastIdx);
        secondRow = stringSlice.substring(0, lastIdx);
        startX += lastIdx;
        textArray.add(secondRow);

        //startX = ++lastIdx;
        startX++;
        stringSlice = procText.substring(startX, startX + sliceLength);
        lastIdx = stringSlice.lastIndexOf(" ");
        thirdRow = stringSlice.substring(0, lastIdx);
        textArray.add(thirdRow);

 */
    }

    private String ensureOneWhiteSpaceOnly(String stringToProcess){
        //String toTest = "Ivo    Kristensjö  Vukmanovic";
        Pattern replace = Pattern.compile("\\s+");
        Matcher matcher = replace.matcher(stringToProcess);
        return matcher.replaceAll(" ");

        /*
        while(matcher.find()){
            System.out.print("Start index: " + matcher.start());
            System.out.print(" End index: " + matcher.end());
            System.out.println(matcher.group());
        }
         */
    }

    public List<String> getPolishedStringArray(){
        //return polishedString;
//        return firstRow;
        return textArray;
    }
}
