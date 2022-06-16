package BOI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrevpathBuilderR7 {

    List<BrevpathR7> brevpathR7List = null;
    private Connection connection;
    private String patSSN;
    private final String sqlScriptFilePathBrevpath = "src/resource/sql/BOI/BrevpathR7.sql";

    private final String brevDirectory = "d:\\Auricula\\TIO_100_brevbilagor_expanded\\";

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

    public void preparePdfPath(int quantity) throws BrevpathExceptionR7 {
        for(int i = 0; i < quantity; i++){
            String itemPath = brevpathR7List.get(i).getBrevPath();
            Path path = Paths.get(brevDirectory, itemPath);

            getPdfDocument(path);
        }
    }

    private void getPdfDocument(Path path) throws BrevpathExceptionR7 {
        String pdfFilePath = String.valueOf(path);
        try{
            if(new File(pdfFilePath).exists()){
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + pdfFilePath);
            }
            else {
                System.out.println("File does not exist.");
            }
        }
        catch (Exception e){
            throw new BrevpathExceptionR7(e.getMessage());
        }
    }


}
