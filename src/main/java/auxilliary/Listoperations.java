package auxilliary;

import ordination.KontrollerProvtagningDoseringar.KontrollerProvtagningDoseringar;

import java.util.*;

public class Listoperations {

    List<KontrollerProvtagningDoseringar> list;
    private int countTotOfSpecialItems;
    private List<Integer> extractedOIDList;

    public Listoperations(List<KontrollerProvtagningDoseringar> list) {
        this.list = list;
        this.extractedOIDList = new ArrayList<>();
        extractSpecialItems();
    }

    public String getCountTotOfSpecialItems(){
        return Integer.toString(countTotOfSpecialItems);
    }

    private void extractSpecialItems(){
        int currentOID = 0;
        for(int i = 0; i < list.size(); i++){
            if(currentOID != list.get(i).getOid()){
                extractedOIDList.add(list.get(i).getOid());
                currentOID = list.get(i).getOid();
            }
        }
        countTotOfSpecialItems = extractedOIDList.size();
    }
}
