package auxilliary;

import ordination.KontrollerProvtagningDoseringar.KontrollerProvtagningDoseringar;
import ordination.KontrollerProvtagningDoseringar.Ordinationperiod;

import java.util.*;

public class Listoperations {

    List<KontrollerProvtagningDoseringar> listKPD;
    private int countTotOfSpecialItems;
    private List<Integer> extractedOIDList;

    public Listoperations(List<KontrollerProvtagningDoseringar> listKPD) {
        this.listKPD = listKPD;
        this.extractedOIDList = new ArrayList<>();
        extractSpecialItems();
    }

    public String getCountTotOfSpecialItems(){
        return Integer.toString(countTotOfSpecialItems);
    }

    private void extractSpecialItems(){
        int currentOID = 0;
        for(int i = 0; i < listKPD.size(); i++){
            if(currentOID != listKPD.get(i).getOid()){
                extractedOIDList.add(listKPD.get(i).getOid());
                currentOID = listKPD.get(i).getOid();
            }
        }
        countTotOfSpecialItems = extractedOIDList.size();
    }
}
