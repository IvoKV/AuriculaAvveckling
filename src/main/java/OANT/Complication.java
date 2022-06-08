package OANT;

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
                        String complexExists,
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
        this.complexExists = complexExists;
        this.bleeding = bleeding;
        this.trombosis = trombosis;
        this.daysOfCare = daysOfCare;
        this.PKINR = PKINR;
        this.status = status;
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

    public String getComplexExists() {
        return complexExists;
    }

    public void setComplexExists(String complexExists) {
        this.complexExists = complexExists;
    }

    public String getBleeding() {
        return bleeding;
    }

    public void setBleeding(String bleeding) {
        this.bleeding = bleeding;
    }

    public String getTrombosis() {
        return trombosis;
    }

    public void setTrombosis(String trombosis) {
        this.trombosis = trombosis;
    }

    public int getDaysOfCare() {
        return daysOfCare;
    }

    public void setDaysOfCare(int daysOfCare) {
        this.daysOfCare = daysOfCare;
    }

    public BigDecimal getPKINR() {
        return PKINR;
    }

    public void setPKINR(BigDecimal PKINR) {
        this.PKINR = PKINR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Complication{" +
                "cid='" + cid + '\'' +
                ", pid=" + pid +
                ", SSN='" + SSN + '\'' +
                ", SSN_TYPE=" + SSN_TYPE +
                ", patFirstname='" + patFirstname + '\'' +
                ", patLastname='" + patLastname + '\'' +
                ", palTitle=" + palTitle +
                ", palFirstname='" + palFirstname + '\'' +
                ", palLastname='" + palLastname + '\'' +
                ", complexExists='" + complexExists + '\'' +
                ", bleeding='" + bleeding + '\'' +
                ", trombosis='" + trombosis + '\'' +
                ", daysOfCare=" + daysOfCare +
                ", PKINR=" + PKINR +
                ", status='" + status + '\'' +
                '}';
    }
}
