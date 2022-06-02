package auxilliary;

import Ordinationperiod.KontrollerProvtagningDoseringar.OrdinationID;

import java.util.HashSet;
import java.util.List;

public class ListGenerics <T extends OrdinationID> {

    List<T> listGen;
    private HashSet<Integer> extractedOIDs;
    private int countTotOidItems;

    public ListGenerics(List<T> listGeneric){
        this.listGen = listGeneric;
        this.extractedOIDs = new HashSet<>();
        extractOid();
    }

    private void extractOid(){
        int currentOID = 0;
        for(int i = 0; i < listGen.size(); i++){
            if(currentOID != listGen.get(i).getOid()){
                extractedOIDs.add(listGen.get(i).getOid());
            }
        }
        countTotOidItems = extractedOIDs.size();
    }

    public String getTotOidItems(){
        return Integer.toString(countTotOidItems);
    }
}
