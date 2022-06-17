package BOI;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class BrevpathBuilderR7 {

    List<BrevpathR7> brevpathR7List = null;
    private Connection connection;
    private String patSSN;
    private final String sqlScriptFilePathBrevpath = "src/resource/sql/BOI/BrevpathR7.sql";

    private final String brevDirectory = "d:\\Auricula\\TIO_100_brevbilagor_expanded";
    private final String PdfFileDestination = "D:\\Auricula\\patientBrevTioHundra";
    int antalHittadeBrev = 0;
    int antalSaknadeBrev = 0;

    public BrevpathBuilderR7(Connection myConnection) throws IOException {
        this.connection = myConnection;
        this.patSSN = patSSN;
    }

    public void createBrevPathR7(String patSSN, boolean writeToFile) throws IOException, SQLException, BrevpathExceptionR7 {
        Path file = Path.of(sqlScriptFilePathBrevpath);
        String sqlStatement = Files.readString(file);

        PreparedStatement selOrdperiod = connection.prepareStatement(sqlStatement);
        selOrdperiod.setString(1, patSSN);

        ResultSet rs = selOrdperiod.executeQuery();
        brevpathR7List = new ArrayList<>();
        while (rs.next()) {
            BrevpathR7 brevpathR7 = new BrevpathR7(
                    rs.getString("PATSSN"),
                    rs.getString("PATH")
            );
            brevpathR7List.add(brevpathR7);
        }
        System.out.println(brevpathR7List);
    }

    public void preparePdfPath(int quantity, boolean showOnDesctop) throws BrevpathExceptionR7 {
        int counter = 0;
        for(int i = 0; i < quantity; i++){
            String itemPath;
            try {
                itemPath = brevpathR7List.get(i).getBrevPath();  // 2011/03/07/11012AK/SE_76021_1_1864723.pdf
            }
            catch(IndexOutOfBoundsException e){
                break;
            }
            String yyyymmdd = itemPath.substring(0, 10);
            String itemPathWithMonth = getDateWithMonthLiteral(yyyymmdd);

            int beforeLastIndex = itemPath.lastIndexOf("/");
            beforeLastIndex = itemPath.lastIndexOf("/", beforeLastIndex -1);String pdfDocumentName = itemPath.substring(beforeLastIndex  + 1);

            Path pathPdfDocument = Paths.get(brevDirectory, itemPathWithMonth, pdfDocumentName);
            String patSSN = brevpathR7List.get(i).getPatSSN();
            getPdfDocument(patSSN, pathPdfDocument, showOnDesctop);
            counter++;
        }
        System.out.println("Antal hittade brev: " + antalHittadeBrev);
        System.out.println("Antal saknade brev: " + antalSaknadeBrev);

        if(counter < brevpathR7List.size()){
            System.out.println("Endast ett urval av breven för patienten har behandlats!!");
        }
        else {
            System.out.println("Alla förekommande brevindex för patienten har blivit behandlade.");
        }
    }

    private String getDateWithMonthLiteral(String yyyymmdd) {
        int year = Integer.valueOf(yyyymmdd.substring(0,4));
        int month = Integer.parseInt(yyyymmdd.substring(5,7));
        int day = Integer.valueOf(yyyymmdd.substring(8,10));
        String dayString = String.format("%02d", day);

        LocalDate date = LocalDate.of(year, month, day);
        Month monthLiteral = date.getMonth();

        String monthLiteralLowerCase = monthLiteral.toString().toLowerCase(Locale.getDefault());
        String monthUCFL = monthLiteralLowerCase.substring(0,1).toUpperCase();
        String monthCapitalized = monthUCFL + monthLiteralLowerCase.substring(1);

        StringBuilder dateRebuild = new StringBuilder();
        dateRebuild.append(year);
        dateRebuild.append("/");
        dateRebuild.append(monthCapitalized);
        dateRebuild.append("/");
        dateRebuild.append(dayString);

        return dateRebuild.toString();
    }

    private void getPdfDocument(String patSSN, Path path, boolean showOnDesktop) throws BrevpathExceptionR7 {
        String pdfFilePath = String.valueOf(path);
        try{
            if(new File(pdfFilePath).exists()){
                System.out.println("Hittade brev: " + pdfFilePath);
                antalHittadeBrev++;
                if(showOnDesktop) {
                    Process p = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler " + pdfFilePath);
                }
                savePdfToDrive(patSSN, pdfFilePath);
            }
            else {
                System.out.println("File [" + pdfFilePath + "] does not exist!!");
                antalSaknadeBrev++;
            }
        }
        catch (Exception e){
            throw new BrevpathExceptionR7(e.getMessage());
        }
    }

    private void savePdfToDrive(String patSSN, String pdfFileAbsolutePath) throws IOException {
        File f = new File(pdfFileAbsolutePath);
        String pdfFileName = f.getName();
        String copyFileName = patSSN + "_" + pdfFileName;

        Path copyPathFilenameToDestination = Paths.get(PdfFileDestination, copyFileName);
        String destination = copyPathFilenameToDestination.toString();

        int method = 1;         // using Apache Commons-IO
        //int method = 2;       // using java.nio.file.Files
        //int method = 3;       // using FileInput/ -OutputStream (långsamaste metoden)
        switch (method){
            case 1:
                saveFileUsingApacheCommonsIO(pdfFileAbsolutePath, destination);
                break;
            case 2:
                File source = new File(pdfFileAbsolutePath);
                File destination1 = new File(destination);
                saveFiles_nio_file(source, destination1);
                break;
            case 3:
                savePdfToDrive_Stream(pdfFileAbsolutePath, destination);
        }
    }

    private void saveFiles_nio_file(File source, File destination) throws IOException {
        FileUtils.copyFile(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    private void saveFileUsingApacheCommonsIO(String pdfFileAbsolutePath, String destination){
        try {
            Files.copy(Paths.get(pdfFileAbsolutePath), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void savePdfToDrive_Stream(String pdfFileAbsolutePath, String destination) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(pdfFileAbsolutePath);
            FileOutputStream fos = new FileOutputStream(destination);
            int count = 0;

            while ((count = fis.read()) > -1) {
                fos.write(count);
            }
            fis.close();
            fos.close();
        } catch(FileNotFoundException e) {
            System.err.println("FileStremsReadnWrite" + e);
        } catch(IOException e){
            System.err.println("FileStreamReadnWrite:" + e);
        }
    }
}
