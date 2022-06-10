package auxilliary;

import Person.GeneralBefattning;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 * author: Ivo Kristensjö Vukmanovic
 * company: Regionstockholm
 * purpose: unmarshalling (deserialisering) of JSON to POJO
 * date: 2022-05-31
 */
public class GeneralBefattningReadJSON {
    private String hsaId;
    private boolean isNull;

    List<GeneralBefattning> resultGeneralBefattning;

    private final String JSONFilePathBefattning = "temp\\generalBefattningar.json";

    public GeneralBefattningReadJSON(String hsaId) throws GeneralBefattningReadJSONException {
        if(hsaId == null){
            isNull = true;
        }
        else if(hsaId.length() != 4){
            throw new GeneralBefattningReadJSONException("hsaID validation error: length not = 4, or hsaId is null");
        }
        else {
            this.hsaId = hsaId;
        }

        try{
            Objects.requireNonNullElse(hsaId, "hsaId is null");
            Paths.get(JSONFilePathBefattning);
            deserializeObject();
        }catch (Exception exp){
            throw new GeneralBefattningReadJSONException(exp.getMessage());
        }
    }

    private void deserializeObject() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        GeneralBefattning [] genBef = mapper.readValue(new File(JSONFilePathBefattning), GeneralBefattning[].class);

        List<GeneralBefattning> generalBefattningList = Arrays.asList(genBef);
        resultGeneralBefattning = generalBefattningList.stream().filter( item -> item.getHsaId().equals(hsaId)).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getGeneralBefattningFirstName(){
        if(!isNull)
            return resultGeneralBefattning.get(0).getFirstName();
        else
            return "is null";
    }

    public String getGeneralBefattningLastName(){
        if(!isNull)
            return resultGeneralBefattning.get(0).getLastName();
        else
            return "is null";
    }

    public String getGeneralBefattningTitel(){
        if(!isNull)
            return resultGeneralBefattning.get(0).getTitel();
        else
            return "is null";
    }
}
