package auxilliary;

import leverans.patientjournal.elektroniskjournalpost.Befattning;

import java.util.Map;

import static java.util.Map.entry;

public class MappingPositions {

    private static Map<String, Befattning> befattning = Map.ofEntries(
            entry("Överläkare", Befattning.lakare),
            entry("Leg. Läkare", Befattning.lakare),
            entry("Leg. Sjuksköterska", Befattning.sjukskoterska),
            entry("Medicinsk sekreterare", Befattning.adminpersonal),
            entry("Administratör", Befattning.adminpersonal),
            entry("Distriktssjuksköterska", Befattning.sjukskoterska),
            entry("Läkarsekreterare", Befattning.lakarsekreterare),
            entry("Specialistläkare", Befattning.lakare),
            entry("Distriktsläkare", Befattning.lakare),
            entry("Leg. Biomedicinsk analytiker", Befattning.biomedicinskanalytiker),
            entry("Undersköterska", Befattning.underskoterska),
            entry("Övrig användare", Befattning.ovrigpersonal));


//    public MappingPositions() {
//        initializeBefattning();
//    }

    public static void initializeBefattning(){
        /*
        befattning.put("Överläkare", "300");
        befattning.put("Leg. Läkare", "300");
        befattning.put("Leg. Sjuksköterska", "101");
        befattning.put("Medicinsk sekreterare", "501");
        befattning.put("Administratör", "501");
        befattning.put("Distriktssjuksköterska", "101");
        befattning.put("Läkarsekreterare", "503");
        befattning.put("Specialistläkare", "300");
        befattning.put("Distriktsläkare", "300");
        befattning.put("Leg. Biomedicinsk analytiker", "102");
        befattning.put("Undersköterska", "H12");
        befattning.put("Övrig användare", "X98");

         */
    }

    public String getBefattning(String key) {
        try {
            if (befattning.get(key) == null)
                return "befattning utan yrkeskod";
        }
        catch(NullPointerException exp){
            System.out.println("Null ponter exception: key = " + key);
        }

        return befattning.get(key).toString();
    }
}
