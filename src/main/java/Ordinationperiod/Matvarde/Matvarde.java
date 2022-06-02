package Ordinationperiod.Matvarde;

import java.sql.Date;
import java.util.Objects;

public class Matvarde {

    private String cid;                              // c.id
    private int pid;                                 // p.pid
    private String SSN;
    private Short SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private Short palTitle;
    private String palFirstname;
    private String palLastname;

    /** CREATININE **/
    private Short creatinin;
    private Date testdate;
    private String specimentComment;
    private String remissComment;
    private String analysisComment;

    public Matvarde(String cid,
                    int pid,
                    String SSN,
                    Short SSN_TYPE,
                    String patFirstname,
                    String patLastname,
                    Short palTitle,
                    String palFirstname,
                    String palLastname,
                    Short creatinin,
                    Date testdate,
                    String specimentComment,
                    String remissComment,
                    String analysisComment) throws MatvardeInitializationException {
        this.cid = cid;
        this.pid = pid;
        this.SSN = SSN;
        this.SSN_TYPE = SSN_TYPE;
        this.patFirstname = patFirstname;
        this.patLastname = patLastname;
        this.palTitle = palTitle;
        this.palFirstname = palFirstname;
        this.palLastname = palLastname;
        this.creatinin = creatinin;
        try {
            this.testdate = Objects.requireNonNullElse(testdate, new Date(0));
            this.specimentComment = Objects.requireNonNullElse(specimentComment, "ingen kommentar");
            this.remissComment = Objects.requireNonNullElse(remissComment, "ingen kommentar");
            this.analysisComment = Objects.requireNonNullElse(analysisComment, "ingen kommentar");
        }
        catch (NullPointerException e){
            throw new MatvardeInitializationException(e.getMessage());
        }
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

    public Short getCreatinin() {
        return creatinin;
    }

    public void setCreatinin(Short creatinin) {
        this.creatinin = creatinin;
    }

    public Date getTestdate() {
        return testdate;
    }

    public void setTestdate(Date testdate) {
        this.testdate = testdate;
    }

    public String getSpecimentComment() {
        return specimentComment;
    }

    public void setSpecimentComment(String specimentComment) {
        this.specimentComment = specimentComment;
    }

    public String getRemissComment() {
        return remissComment;
    }

    public void setRemissComment(String remissComment) {
        this.remissComment = remissComment;
    }

    public String getAnalysisComment() {
        return analysisComment;
    }

    public void setAnalysisComment(String analysisComment) {
        this.analysisComment = analysisComment;
    }

    @Override
    public String toString() {
        return "Matvarde{" +
                "cid='" + cid + '\'' +
                ", pid=" + pid +
                ", SSN='" + SSN + '\'' +
                ", SSN_TYPE=" + SSN_TYPE +
                ", patFirstname='" + patFirstname + '\'' +
                ", patLastname='" + patLastname + '\'' +
                ", palTitle=" + palTitle +
                ", palFirstname='" + palFirstname + '\'' +
                ", palLastname='" + palLastname + '\'' +
                ", creatinin='" + creatinin + '\'' +
                ", testdate=" + testdate +
                ", specimentComment='" + specimentComment + '\'' +
                ", remissComment='" + remissComment + '\'' +
                ", analysisComment='" + analysisComment + '\'' +
                '}';
    }
}
