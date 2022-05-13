package auxilliary;

import ordination.KontrollerProvtagningDoseringar.OrdinationID;

import java.util.HashSet;
import java.util.List;

public class ListGenerics <T> {

    //List<OrdinationID> listGen;
    List<OrdinationID> listGen;
    private HashSet<Integer> extractedOIDs;
    private int countTotOidItems;

    //public ListGenerics(List<OrdinationID> listGeneric){
    public ListGenerics(List<OrdinationID> listGeneric){
        this.listGen = listGeneric;
        this.extractedOIDs = new HashSet<>();
        extractOid();
    }

    public String getCountTotOfOids(){
        return Integer.toString(countTotOidItems);
    }

    private void extractOid(){
        int currentOID = 0;
        for(int i = 0; i < listGen.size(); i++){
            if(currentOID != listGen.get(i).getOid()){
                extractedOIDs.add(listGen.get(i).getOid());
                extractedOIDs.add(listGen.get(i).getOid());
            }
        }
        countTotOidItems = extractedOIDs.size();
    }

    public int getOid(){
        return countTotOidItems;
    }
}
