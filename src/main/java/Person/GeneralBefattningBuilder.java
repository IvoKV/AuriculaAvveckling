package Person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GeneralBefattningBuilder {

    private List<GeneralBefattning> generalBefattningList;
    private String sqlScriptFilePathGeneralBefattning = "src/resource/sql/person/GeneralBefattning.sql";
    private final String JSONFileName = "temp/generalBefattningar.json";

    private Connection myConnection = null;

    public GeneralBefattningBuilder(final Connection con) {
        this.generalBefattningList = new ArrayList<>();
        this.myConnection = con;
    }

    public void buildGeneralBefattning() throws SQLException, IOException, GeneralBefattningException {
        Path file = Path.of(sqlScriptFilePathGeneralBefattning);

        String sqlStatement = Files.readString(file);
        Statement statement = myConnection.createStatement();

        ResultSet rs = statement.executeQuery(sqlStatement);

        while (rs.next()) {
            if (rs.getString("titel") == null)
                continue;

            GeneralBefattning generalBefattning = new GeneralBefattning(
                    rs.getString("HSAID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("TITEL")
            );
            generalBefattningList.add(generalBefattning);
        }
        POJOListToJSONToFile(generalBefattningList);
    }

    public String getTitle(String hsaid){
        String title = null;
        for(var item : generalBefattningList){
            if(item.getHsaId().equals(hsaid)){
                title = item.getTitel();
            }
        }
        return title;
    }

    private void POJOListToJSONToFile(List<GeneralBefattning> gb) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = objectMapper.writeValueAsString(gb);
        JSONArray jArr = new JSONArray(arrayToJson);

        // 1. Convert List of person objects to JSON :");
        System.out.println(arrayToJson);
        System.out.println("Total antal poster (json objekt): " + jArr.length());

        FileWriter jsonWriter = new FileWriter(JSONFileName);
        jsonWriter.write(arrayToJson);
        jsonWriter.close();
    }
}
