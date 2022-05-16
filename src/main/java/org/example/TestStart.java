package org.example;

import auxilliary.StringWriter1;

public class TestStart {
    public static void main(String[] args) {
//        String unpolished = "Ivo    Kristensjö   Vukmanovic";
//        StringWriter1 str1 = new StringWriter1(unpolished, 50);
//        System.out.println("Raw string was: " + unpolished);
//        System.out.println( str1.getPolishedString());


        String comment = "Ditt%20PK%28INR%29-v%E4rde%20ligger%20f%F6r%20h%F6gt%2C%20tag%20d%E4rf%F6r%201%20tablett%20mindre%20endast%20den%20dagen%20du%20f%E5r%20det%20h%E4r%20brevet%20och%20f%F6lj%20d%E4refter%20schemat.%20V%E4nligen%20ring%20medicinmottagningen%20m%E5n-fre%20kl%209-11%20p%E5%20tele%3A%200176-32%2060%2092%20f%F6r%20att%20meddela%20oss%20ditt%20telefonnummer%20om%20vi%20beh%F6ver%20n%E5%20dig%20f%F6r%20br%E5dskande%20besked%20ang%E5ende%20din%20ordination.";
        StringWriter1 str = new StringWriter1(comment, 55);
        //System.out.println( str.getTextArray());
        System.out.println("First row: " + str.getPolishedStringArray());
        //System.out.println("Second row: " + str.getPolishedString());
    }
}
