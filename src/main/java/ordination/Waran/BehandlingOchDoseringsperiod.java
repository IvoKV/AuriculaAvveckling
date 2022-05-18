package ordination.Waran;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class BehandlingOchDoseringsperiod {
    private String cid;                              // c.id
    private int pid;                                 // p.pid
    private String SSN;
    private Short SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private Short palTitle;
    private String palFirstname;
    private String palLastname;

    /** BEHANDLING **/
    private String medicintype;
    private String doseMode;
    private String prefferedIntervalStart;
    private String prefferedIntervalEnd;
    private String inrMethod;
    private Double weight;
    private Date weightdate;

    /** LMH DOSERING **/
    private String dose;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date toDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date currentOpStartDate;
    private String periodLength;
    private String lengthComment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date enddate;
    private String reasonStopped;

    public BehandlingOchDoseringsperiod(String cid,
                                        int pid,
                                        String SSN,
                                        Short SSN_TYPE,
                                        String patFirstname,
                                        String patLastname,
                                        Short palTitle,
                                        String palFirstname,
                                        String palLastname,

                                        String medicintype,                     // 9
                                        String doseMode,                        // 10
                                        String prefferedIntervalStart,          // 11
                                        String prefferedIntervalEnd,            // 12
                                        String inrMethod,                       // 13
                                        Double weight,                          // 14
                                        Date weightdate,                        // 15
                                        String dose,                            // 16
                                        Date fromDate,                          // 17
                                        Date toDate,                            // 18
                                        Date currentOpStartDate,                // 19
                                        String periodLength,                    // 20
                                        String lengthComment,                   // 21
                                        Date enddate,                           // 22
                                        String reasonStopped                    // 23
    ) {
        this.cid = cid;
        this.pid = pid;
        this.SSN = SSN;
        this.SSN_TYPE = SSN_TYPE;
        this.patFirstname = patFirstname;
        this.patLastname = patLastname;
        this.palTitle = palTitle;
        this.palFirstname = palFirstname;
        this.palLastname = palLastname;
        this.medicintype = medicintype;
        this.doseMode = doseMode;
        this.prefferedIntervalStart = prefferedIntervalStart;
        this.prefferedIntervalEnd = prefferedIntervalEnd;
        this.inrMethod = inrMethod;
        this.weight = weight;
        this.weightdate = weightdate;
        this.dose = dose;
        this.fromDate = fromDate;  //new Date(fromDate);
        this.toDate = toDate;
        this.currentOpStartDate = currentOpStartDate;
        this.periodLength = periodLength;
        this.lengthComment = lengthComment;
        this.enddate = enddate;
        this.reasonStopped = reasonStopped;
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

    public String getMedicintype() {
        return medicintype;
    }

    public void setMedicintype(String medicintype) {
        this.medicintype = medicintype;
    }

    public String getDoseMode() {
        return doseMode;
    }

    public void setDoseMode(String doseMode) {
        this.doseMode = doseMode;
    }

    public String getPrefferedIntervalStart() {
        return prefferedIntervalStart;
    }

    public void setPrefferedIntervalStart(String prefferedIntervalStart) {
        this.prefferedIntervalStart = prefferedIntervalStart;
    }

    public String getPrefferedIntervalEnd() {
        return prefferedIntervalEnd;
    }

    public void setPrefferedIntervalEnd(String prefferedIntervalEnd) {
        this.prefferedIntervalEnd = prefferedIntervalEnd;
    }

    public String getInrMethod() {
        return inrMethod;
    }

    public void setInrMethod(String inrMethod) {
        this.inrMethod = inrMethod;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getWeightdate() {
        return weightdate;
    }

    public void setWeightdate(Date weightdate) {
        this.weightdate = weightdate;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getCurrentOpStartDate() {
        return currentOpStartDate;
    }

    public void setCurrentOpStartDate(Date currentOpStartDate) {
        this.currentOpStartDate = currentOpStartDate;
    }

    public String getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(String periodLength) {
        this.periodLength = periodLength;
    }

    public String getLengthComment() {
        return lengthComment;
    }

    public void setLengthComment(String lengthComment) {
        this.lengthComment = lengthComment;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getReasonStopped() {
        return reasonStopped;
    }

    public void setReasonStopped(String reasonStopped) {
        this.reasonStopped = reasonStopped;
    }

    @Override
    public String toString() {
        return "BehandlingOchDoseringsperiod{" +
                "cid='" + cid + '\'' +
                ", pid=" + pid +
                ", SSN='" + SSN + '\'' +
                ", SSN_TYPE=" + SSN_TYPE +
                ", patFirstname='" + patFirstname + '\'' +
                ", patLastname='" + patLastname + '\'' +
                ", palTitle=" + palTitle +
                ", palFirstname='" + palFirstname + '\'' +
                ", palLastname='" + palLastname + '\'' +
                ", medicintype='" + medicintype + '\'' +
                ", doseMode='" + doseMode + '\'' +
                ", prefferedIntervalStart='" + prefferedIntervalStart + '\'' +
                ", prefferedIntervalEnd='" + prefferedIntervalEnd + '\'' +
                ", inrMethod='" + inrMethod + '\'' +
                ", weight=" + weight +
                ", weightdate=" + weightdate +
                ", dose='" + dose + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", currentOpStartDate=" + currentOpStartDate +
                ", periodLength='" + periodLength + '\'' +
                ", lengthComment='" + lengthComment + '\'' +
                ", enddate=" + enddate +
                ", reasonStopped='" + reasonStopped + '\'' +
                '}';
    }
}
