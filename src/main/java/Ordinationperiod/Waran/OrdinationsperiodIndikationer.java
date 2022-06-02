package Ordinationperiod.Waran;

import java.sql.Date;

public class OrdinationsperiodIndikationer {

    private String cid;                                 // c.id
    private int pid;                                 // p.pid
    private String SSN;
    private Short SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private Short palTitle;
    private String palFirstname;
    private String palLastname;
    private Date STARTDATE;                             // Behandlingsstart
    private String comment;
    private String MEDICIN_TXT;                         // Läkemedel t.ex. Waran eller Xarelto samt tablettstyrka
    private String ATRIAL_FIB_TXT;                      // Indikation - FF
    private String VALVE_MALFUNCTION_TXT;               // Indikation - Klaff
    private String VENOUS_TROMB_TXT;                    // Indikation - Venös tromboembolism
    private String INDICATION_OTHER_TXT;                // Indikation - Övrigt
    private String OTHERCHILDINDICATION_TXT;            // Övrig Banrspecifik indikation
    private String DCCONVERSION_TXT;                    // Elkonvertering
    private String PERIOD_LENGTH_TXT;                   // Behandlingstid
    private String lmh;                                 // Klaffel indikator

    /* Bevakningar */
    private String ovrigt;

    public OrdinationsperiodIndikationer(String cid,
                                         int pid,
                                         String SSN,
                                         Short SSN_TYPE,
                                         String patFirstname,
                                         String patLastname,
                                         Short  palTitle,
                                         String palFirstname,
                                         String palLastname,
                                         Date STARTDATE,
                                         String comment,
                                         String MEDICIN_TXT,
                                         String ATRIAL_FIB_TXT,
                                         String VALVE_MALFUNCTION_TXT,
                                         String VENOUS_TROMB_TXT,
                                         String INDICATION_OTHER_TXT,
                                         String OTHERCHILDINDICATION_TXT,
                                         String DCCONVERSION_TXT,
                                         String PERIOD_LENGTH_TXT,
                                         String lmh
                                         )
    {
        this.cid = cid;
        this.pid = pid;
        this.SSN = SSN;
        this.SSN_TYPE = SSN_TYPE;
        this.patFirstname = patFirstname;
        this.patLastname = patLastname;
        this.palTitle = palTitle;
        this.palFirstname = palFirstname;
        this.palLastname = palLastname;
        this.STARTDATE = STARTDATE;
        this.comment = comment;
        this.MEDICIN_TXT = MEDICIN_TXT;
        this.ATRIAL_FIB_TXT = ATRIAL_FIB_TXT;
        this.VALVE_MALFUNCTION_TXT = VALVE_MALFUNCTION_TXT;
        this.VENOUS_TROMB_TXT = VENOUS_TROMB_TXT;
        this.INDICATION_OTHER_TXT = INDICATION_OTHER_TXT;
        this.OTHERCHILDINDICATION_TXT = OTHERCHILDINDICATION_TXT;
        this.DCCONVERSION_TXT = DCCONVERSION_TXT;
        this.PERIOD_LENGTH_TXT = PERIOD_LENGTH_TXT;
        this.ovrigt = ovrigt;
        this.lmh = lmh;
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

    public Date getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(Date STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMEDICIN_TXT() {
        return MEDICIN_TXT;
    }

    public void setMEDICIN_TXT(String MEDICIN_TXT) {
        this.MEDICIN_TXT = MEDICIN_TXT;
    }

    public String getATRIAL_FIB_TXT() {
        return ATRIAL_FIB_TXT;
    }

    public void setATRIAL_FIB_TXT(String ATRIAL_FIB_TXT) {
        this.ATRIAL_FIB_TXT = ATRIAL_FIB_TXT;
    }

    public String getVALVE_MALFUNCTION_TXT() {
        return VALVE_MALFUNCTION_TXT;
    }

    public void setVALVE_MALFUNCTION_TXT(String VALVE_MALFUNCTION_TXT) {
        this.VALVE_MALFUNCTION_TXT = VALVE_MALFUNCTION_TXT;
    }

    public String getVENOUS_TROMB_TXT() {
        return VENOUS_TROMB_TXT;
    }

    public void setVENOUS_TROMB_TXT(String VENOUS_TROMB_TXT) {
        this.VENOUS_TROMB_TXT = VENOUS_TROMB_TXT;
    }

    public String getINDICATION_OTHER_TXT() {
        return INDICATION_OTHER_TXT;
    }

    public void setINDICATION_OTHER_TXT(String INDICATION_OTHER_TXT) {
        this.INDICATION_OTHER_TXT = INDICATION_OTHER_TXT;
    }

    public String getOTHERCHILDINDICATION_TXT() {
        return OTHERCHILDINDICATION_TXT;
    }

    public void setOTHERCHILDINDICATION_TXT(String OTHERCHILDINDICATION_TXT) {
        this.OTHERCHILDINDICATION_TXT = OTHERCHILDINDICATION_TXT;
    }

    public String getDCCONVERSION_TXT() {
        return DCCONVERSION_TXT;
    }

    public void setDCCONVERSION_TXT(String DCCONVERSION_TXT) {
        this.DCCONVERSION_TXT = DCCONVERSION_TXT;
    }

    public String getPERIOD_LENGTH_TXT() {
        return PERIOD_LENGTH_TXT;
    }

    public void setPERIOD_LENGTH_TXT(String PERIOD_LENGTH_TXT) {
        this.PERIOD_LENGTH_TXT = PERIOD_LENGTH_TXT;
    }

    public String getOvrigt() {
        return ovrigt;
    }

    public void setOvrigt(String ovrigt) {
        this.ovrigt = ovrigt;
    }

    public String getLmh() {
        return lmh;
    }

    public void setLmh(String lmh) {
        this.lmh = lmh;
    }

    @Override
    public String toString() {
        return "OrdinationsperiodIndikationer{" +
                "cid='" + cid + '\'' +
                ", pid=" + pid +
                ", SSN='" + SSN + '\'' +
                ", SSN_TYPE=" + SSN_TYPE +
                ", patFirstname='" + patFirstname + '\'' +
                ", patLastname='" + patLastname + '\'' +
                ", palTitle=" + palTitle +
                ", palFirstname='" + palFirstname + '\'' +
                ", palLastname='" + palLastname + '\'' +
                ", STARTDATE=" + STARTDATE +
                ", comment='" + comment + '\'' +
                ", MEDICIN_TXT='" + MEDICIN_TXT + '\'' +
                ", ATRIAL_FIB_TXT='" + ATRIAL_FIB_TXT + '\'' +
                ", VALVE_MALFUNCTION_TXT='" + VALVE_MALFUNCTION_TXT + '\'' +
                ", VENOUS_TROMB_TXT='" + VENOUS_TROMB_TXT + '\'' +
                ", INDICATION_OTHER_TXT='" + INDICATION_OTHER_TXT + '\'' +
                ", OTHERCHILDINDICATION_TXT='" + OTHERCHILDINDICATION_TXT + '\'' +
                ", DCCONVERSION_TXT='" + DCCONVERSION_TXT + '\'' +
                ", PERIOD_LENGTH_TXT='" + PERIOD_LENGTH_TXT + '\'' +
                ", lmh=" + lmh +
                ", ovrigt='" + ovrigt + '\'' +
                '}';
    }
}
