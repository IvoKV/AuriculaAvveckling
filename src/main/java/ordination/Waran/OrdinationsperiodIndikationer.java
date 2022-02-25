package ordination.Waran;

import java.sql.Date;

public class OrdinationsperiodIndikationer {

    private String cid;                                 // c.id
    private int pid;                                 // p.pid
    private String SSN;
    private String SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private String palTitle;
    private String palFirstname;
    private String palLastname;
    private Date STARTDATE;                             // Behandlingsstart
    private String comment;
    private Short MEDICIN_TXT;                         // Läkemedel t.ex. Waran eller Xarelto samt tablettstyrka
    private String ATRIAL_FIB_TXT;                      // Indikation - FF
    private Short VALVE_MALFUNCTION_TXT;               // Indikation - Klaff
    private Short VENOUS_TROMB_TXT;                    // Indikation - Venös tromboembolism
    private Short INDICATION_OTHER_TXT;                // Indikation - Övrigt
    private Short OTHERCHILDINDICATION_TXT;            // Övrig Banrspecifik indikation
    private Short DCCONVERSION_TXT;                    // Elkonvertering
    private Short PERIOD_LENGTH_TXT;                   // Behandlingstid

    /* Bevakningar */
    private String ovrigt;

    public OrdinationsperiodIndikationer(String cid,
                                         int pid,
                                         String SSN,
                                         String SSN_TYPE,
                                         String patFirstname,
                                         String patLastname,
                                         String palTitle,
                                         String palFirstname,
                                         String palLastname,
                                         Date STARTDATE,
                                         String comment,
                                         Short MEDICIN_TXT,
                                         String ATRIAL_FIB_TXT,
                                         Short VALVE_MALFUNCTION_TXT,
                                         Short VENOUS_TROMB_TXT,
                                         Short INDICATION_OTHER_TXT,
                                         Short OTHERCHILDINDICATION_TXT,
                                         Short DCCONVERSION_TXT,
                                         Short PERIOD_LENGTH_TXT
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
    }
    /*
    public OrdinationsperiodIndikationer(String cid, int pid) throws OrdinationsperiodInitializeException {
        this.cid = cid;
        this.pid = pid;

        try {
            this.cid = Objects.requireNonNullElse(cid,"cid is null");
            this.pid = Objects.requireNonNullElse(pid, 0);
        }
        catch (NullPointerException npe) {
            throw new OrdinationsperiodInitializeException(npe.getMessage());
        }
    }

     */



    @Override
    public String toString() {
        return "OrdinationsperiodIndikationer{" +
                "cid='" + cid + '\'' +
                ", pid=" + pid +
                ", SSN='" + SSN + '\'' +
                ", SSN_TYPE='" + SSN_TYPE + '\'' +
                ", patFirstname='" + patFirstname + '\'' +
                ", patLastname='" + patLastname + '\'' +
                ", palTitle='" + palTitle + '\'' +
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
                ", PERIOD_LENGTH_TXT=" + PERIOD_LENGTH_TXT +
                ", ovrigt='" + ovrigt + '\'' +
                '}';
    }
}
