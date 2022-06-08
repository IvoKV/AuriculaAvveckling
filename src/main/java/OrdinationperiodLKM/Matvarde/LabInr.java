package OrdinationperiodLKM.Matvarde;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class LabInr {
    private String cid;                                 // c.id
    private int pid;                                 // p.pid
    private String SSN;
    private Short SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private Short palTitle;
    private String palFirstname;
    private String palLastname;

    /* INR */
    private double inrValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date inrDate;
    private String labremComment;
    private String specimenComment;
    private String inrAnalysisCommennt;

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

    public double getInrValue() {
        return inrValue;
    }

    public void setInrValue(double inrValue) {
        this.inrValue = inrValue;
    }

    public Date getInrDate() {
        return inrDate;
    }

    public void setInrDate(Date inrDate) {
        this.inrDate = inrDate;
    }

    public String getLabremComment() {
        return labremComment;
    }

    public void setLabremComment(String labremComment) {
        this.labremComment = labremComment;
    }

    public String getSpecimenComment() {
        return specimenComment;
    }

    public void setSpecimenComment(String specimenComment) {
        this.specimenComment = specimenComment;
    }

    public String getInrAnalysisCommennt() {
        return inrAnalysisCommennt;
    }

    public void setInrAnalysisCommennt(String inrAnalysisCommennt) {
        this.inrAnalysisCommennt = inrAnalysisCommennt;
    }

    @Override
    public String toString() {
        return "LabInr{" +
                "cid='" + cid + '\'' +
                ", pid=" + pid +
                ", SSN='" + SSN + '\'' +
                ", SSN_TYPE=" + SSN_TYPE +
                ", patFirstname='" + patFirstname + '\'' +
                ", patLastname='" + patLastname + '\'' +
                ", palTitle=" + palTitle +
                ", palFirstname='" + palFirstname + '\'' +
                ", palLastname='" + palLastname + '\'' +
                ", inrValue=" + inrValue +
                ", inrDate=" + inrDate +
                ", labremComment='" + labremComment + '\'' +
                ", specimenComment='" + specimenComment + '\'' +
                ", inrAnalysisCommennt='" + inrAnalysisCommennt + '\'' +
                '}';
    }

    public LabInr(String cid,
                  int pid,
                  String SSN,
                  Short SSN_TYPE,
                  String patFirstname,
                  String patLastname,
                  Short palTitle,
                  String palFirstname,
                  String palLastname,
                  double inrValue,
                  Date inrDate,
                  String labremComment,
                  String specimenComment,
                  String inrAnalysisCommennt) {
        this.cid = cid;
        this.pid = pid;
        this.SSN = SSN;
        this.SSN_TYPE = SSN_TYPE;
        this.patFirstname = patFirstname;
        this.patLastname = patLastname;
        this.palTitle = palTitle;
        this.palFirstname = palFirstname;
        this.palLastname = palLastname;
        this.inrValue = inrValue;
        this.inrDate = inrDate;
        this.labremComment = labremComment;
        this.specimenComment = specimenComment;
        this.inrAnalysisCommennt = inrAnalysisCommennt;



    }
}
