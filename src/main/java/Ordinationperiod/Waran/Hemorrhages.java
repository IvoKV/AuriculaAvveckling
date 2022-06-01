package Ordinationperiod.Waran;

public class Hemorrhages {
    private String cid;                              // c.id
    private int pid;                                 // p.pid
    private String SSN;
    private Short SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private Short palTitle;
    private String palFirstname;
    private String palLastname;

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

    public Hemorrhages(String cid,
                       int pid,
                       String SSN,
                       Short SSN_TYPE,
                       String patFirstname,
                       String patLastname,
                       Short palTitle,
                       String palFirstname,
                       String palLastname,
                       String leverNjursjukdom,
                       String etanolmissbruk,
                       String malignitet,
                       String reduceratTrombocytantalFunktion,
                       String tidigareBlodning,
                       String hypertoni,
                       String anemi,
                       String genetiskaFaktorer,
                       String storRiskForFall,
                       String stroke) {
        this.cid = cid;
        this.pid = pid;
        this.SSN = SSN;
        this.SSN_TYPE = SSN_TYPE;
        this.patFirstname = patFirstname;
        this.patLastname = patLastname;
        this.palTitle = palTitle;
        this.palFirstname = palFirstname;
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
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public Short getSSN_TYPE() {
        return SSN_TYPE;
    }

    public void setSSN_TYPE(Short SSN_TYPE) {
        this.SSN_TYPE = SSN_TYPE;
    }

    public String getPatFirstname() {
        return patFirstname;
    }

    public void setPatFirstname(String patFirstname) {
        this.patFirstname = patFirstname;
    }

    public String getPatLastname() {
        return patLastname;
    }

    public void setPatLastname(String patLastname) {
        this.patLastname = patLastname;
    }

    public Short getPalTitle() {
        return palTitle;
    }

    public void setPalTitle(Short palTitle) {
        this.palTitle = palTitle;
    }

    public String getPalFirstname() {
        return palFirstname;
    }

    public void setPalFirstname(String palFirstname) {
        this.palFirstname = palFirstname;
    }

    public String getPalLastname() {
        return palLastname;
    }

    public void setPalLastname(String palLastname) {
        this.palLastname = palLastname;
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
        return "Hemorrhages{" +
                "cid='" + cid + '\'' +
                ", pid=" + pid +
                ", SSN='" + SSN + '\'' +
                ", SSN_TYPE=" + SSN_TYPE +
                ", patFirstname='" + patFirstname + '\'' +
                ", patLastname='" + patLastname + '\'' +
                ", palTitle=" + palTitle +
                ", palFirstname='" + palFirstname + '\'' +
                ", palLastname='" + palLastname + '\'' +
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
