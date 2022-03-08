package ordination.Waran;

import java.math.BigDecimal;

public class Complication {
    private String cid;                              // c.id
    private int pid;                                 // p.pid
    private String SSN;
    private Short SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private Short palTitle;
    private String palFirstname;
    private String palLastname;

    /** Complication **/
    private String complexExists;                    // CASE!!
    private String bleeding;                        // CASE!!
    private String trombosis;                       // CASE!!
    private int daysOfCare;
    private BigDecimal PKINR;
    private String status;

    public Complication(String cid,
                        int pid,
                        String SSN,
                        Short SSN_TYPE,
                        String patFirstname,
                        String patLastname,
                        Short palTitle,
                        String palFirstname,
                        String palLastname,
                        String comlexExists,
                        String bleeding,
                        String trombosis,
                        int daysOfCare,
                        BigDecimal PKINR,
                        String status)
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
        this.comlexExists = comlexExists;
        this.bleeding = bleeding;
        this.trombosis = trombosis;
        this.daysOfCare = daysOfCare;
        this.PKINR = PKINR;
        this.status = status;
    }
}
