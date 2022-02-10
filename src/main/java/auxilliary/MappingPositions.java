package auxilliary;

import java.util.HashMap;
import java.util.Map;

public class MappingPositions {

    private static Map<String, String> befattning = new HashMap<>();

    public MappingPositions() {
        initializeBefattning();
    }

    public static void initializeBefattning(){
        befattning.put("Överläkare", "300");
        befattning.put("Leg. Läkare", "300");
        befattning.put("Leg. Sjuksköterska", "101");
        befattning.put("Medicinsk sekreterare", "?");   // ev. 503?
        befattning.put("Administratör", "X98");
        befattning.put("Distriktssjuksköterska", "?");  // ev. 101?
        befattning.put("Läkarsekreterare", "503");
        befattning.put("Specialistläkare", "300");
        befattning.put("Distriktsläkare", "300");
        befattning.put("Leg. Biomedicinsk analytiker", "102");
        befattning.put("Undersköterska", "H12");
        befattning.put("Övriga användare", "X98");
    }

    public String getBefattning(String key) {
        return befattning.get(key);
    }
}
