package MV;

import Person.PatientGeneralData;
import Person.PatientGeneralDataException;

import java.util.Objects;

public class HemorrhagesR7 extends PatientGeneralData {
    private String cid;


    /** Hemorrhages **/
    private String leverNjursjukdom;
    private String etanolmissbruk;
    private String malignitet;
    private String reduceratTrombocytantalFunktion;
    private String tidigareBlodning;
    private String hypertoni;
    private String anemi;
    private String genetiskaFaktorer;

    /** Genetiska faktorer (CYP2C9, VKORC1 **/
    private String storRiskForFall;
    private String stroke;

    public HemorrhagesR7(String cid,
                         int pid,
                         String SSN,
                         Short SSN_TYPE,
                         String patFirstname,
                         String patLastname,

                         String leverNjursjukdom,
                         String etanolmissbruk,
                         String malignitet,
                         String reduceratTrombocytantalFunktion,
                         String tidigareBlodning,
                         String hypertoni,
                         String anemi,
                         String genetiskaFaktorer,
                         String storRiskForFall,
                         String stroke) throws PatientGeneralDataException, HemorrhagesR7Exception {
        super(pid, patFirstname, patLastname, SSN,SSN_TYPE);
        this.cid = cid;

        this.leverNjursjukdom = leverNjursjukdom;
        this.etanolmissbruk = etanolmissbruk;
        this.malignitet = malignitet;
        this.reduceratTrombocytantalFunktion = reduceratTrombocytantalFunktion;
        this.tidigareBlodning = tidigareBlodning;
        this.hypertoni = hypertoni;
        this.anemi = anemi;
        this.genetiskaFaktorer = genetiskaFaktorer;
        this.storRiskForFall = storRiskForFall;
        this.stroke = stroke;

        try{
            Objects.requireNonNullElse(cid, "cid is null");
            Objects.requireNonNullElse(pid, "pid is null");
        }
        catch (Exception exp){
            throw new HemorrhagesR7Exception(exp.getMessage());
        }
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLeverNjursjukdom() {
        return leverNjursjukdom;
    }

    public void setLeverNjursjukdom(String leverNjursjukdom) {
        this.leverNjursjukdom = leverNjursjukdom;
    }

    public String getEtanolmissbruk() {
        return etanolmissbruk;
    }

    public void setEtanolmissbruk(String etanolmissbruk) {
        this.etanolmissbruk = etanolmissbruk;
    }

    public String getMalignitet() {
        return malignitet;
    }

    public void setMalignitet(String malignitet) {
        this.malignitet = malignitet;
    }

    public String getReduceratTrombocytantalFunktion() {
        return reduceratTrombocytantalFunktion;
    }

    public void setReduceratTrombocytantalFunktion(String reduceratTrombocytantalFunktion) {
        this.reduceratTrombocytantalFunktion = reduceratTrombocytantalFunktion;
    }

    public String getTidigareBlodning() {
        return tidigareBlodning;
    }

    public void setTidigareBlodning(String tidigareBlodning) {
        this.tidigareBlodning = tidigareBlodning;
    }

    public String getHypertoni() {
        return hypertoni;
    }

    public void setHypertoni(String hypertoni) {
        this.hypertoni = hypertoni;
    }

    public String getAnemi() {
        return anemi;
    }

    public void setAnemi(String anemi) {
        this.anemi = anemi;
    }

    public String getGenetiskaFaktorer() {
        return genetiskaFaktorer;
    }

    public void setGenetiskaFaktorer(String genetiskaFaktorer) {
        this.genetiskaFaktorer = genetiskaFaktorer;
    }

    public String getStorRiskForFall() {
        return storRiskForFall;
    }

    public void setStorRiskForFall(String storRiskForFall) {
        this.storRiskForFall = storRiskForFall;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    @Override
    public String toString() {
        return "HemorrhagesR7{" +
                "cid='" + cid + '\'' +
                ", leverNjursjukdom='" + leverNjursjukdom + '\'' +
                ", etanolmissbruk='" + etanolmissbruk + '\'' +
                ", malignitet='" + malignitet + '\'' +
                ", reduceratTrombocytantalFunktion='" + reduceratTrombocytantalFunktion + '\'' +
                ", tidigareBlodning='" + tidigareBlodning + '\'' +
                ", hypertoni='" + hypertoni + '\'' +
                ", anemi='" + anemi + '\'' +
                ", genetiskaFaktorer='" + genetiskaFaktorer + '\'' +
                ", storRiskForFall='" + storRiskForFall + '\'' +
                ", stroke='" + stroke + '\'' +
                '}';
    }
}
