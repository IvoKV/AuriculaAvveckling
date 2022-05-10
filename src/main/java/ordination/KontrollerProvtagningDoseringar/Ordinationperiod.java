package ordination.KontrollerProvtagningDoseringar;

import java.sql.Date;
import java.util.Objects;

public class Ordinationperiod {

    private String fornamn;                 // regionpatient
    private String efternamn;               // regionpatient
    private String ssn;                     // patient
    private Short ssntype;                  // patient
    private String paltext;                 // centrepatient
    private Short medicinetype;             // (sql)
    private Short atrialFib;                // se xml!
    private Short valveMalfunction;         // se xml!
    private Short venousTromb;              // se xml!
    private Short other;                    // se xml!

    private Short otherChildIndication;     // se xml!
    private Short dcconversion;             // se xml!
    private Short dctherapydropout;         // -
    private Short periodLength;             // -
    private Short medicin;                  // se xml!
    private Short doseMode;                 // se xml!
    private Short creaintervalFirstyear;    // se xml!
    private Date startDate;
    private Date endDate;
    private Date creaComplaTestcreated;

    private int creaComplFolYear;           // (90,182,365)
    private int creaComplFirstYear;         // se xml!  (0,1,3,4)
    private Short reasonStopped;            // se xml!
    private Date continueLateCheck;
    private String createdBy;
    private String updatedBy;
    private String lengthcomment;
    private Short inrmethod;

    public Ordinationperiod(String fornamn,
                            String efternamn,
                            String ssn,
                            Short ssntype,
                            String paltext,
                            Short medicinetype,
                            Short atrialFib,
                            Short valveMalfunction,
                            Short venousTromb,
                            Short other,

                            Short otherChildIndication,
                            Short dcconversion,
                            Short dctherapydropout,
                            Short periodLength,
                            Short medicin,
                            Short doseMode,
                            Short creaintervalFirstyear,
                            Date startDate,
                            Date endDate,
                            Date creaComplaTestcreated,

                            int creaComplFolYear,
                            int creaComplFirstYear,
                            Short reasonStopped,
                            Date continueLateCheck,
                            String createdBy,
                            String updatedBy,
                            String lengthcomment,
                            Short inrmethod) throws OrdinationperiodException {
        this.fornamn = fornamn;
        this.efternamn = efternamn;
        this.ssn = ssn;
        this.ssntype = ssntype;
        this.paltext = paltext;
        this.medicinetype = medicinetype;
        this.atrialFib = atrialFib;
        this.valveMalfunction = valveMalfunction;
        this.venousTromb = venousTromb;
        this.other = other;
        this.otherChildIndication = otherChildIndication;
        this.dcconversion = dcconversion;
        this.dctherapydropout = dctherapydropout;
        this.periodLength = periodLength;
        this.medicin = medicin;
        this.doseMode = doseMode;
        this.creaintervalFirstyear = creaintervalFirstyear;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creaComplaTestcreated = creaComplaTestcreated;
        this.creaComplFolYear = creaComplFolYear;
        this.creaComplFirstYear = creaComplFirstYear;
        this.reasonStopped = reasonStopped;
        this.continueLateCheck = continueLateCheck;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.lengthcomment = lengthcomment;
        this.inrmethod = inrmethod;

        try {
            Objects.requireNonNullElse(fornamn, "inget förnamn");
            Objects.requireNonNullElse(efternamn, "inget efternamn");
            Objects.requireNonNullElse(ssn, "inget personnummer");
            Objects.requireNonNullElse(ssntype, "ingen typ för personnummer");
            Objects.requireNonNullElse(paltext, "paltext is null");
            Objects.requireNonNullElse(medicinetype, "medicinetype is null");
            Objects.requireNonNullElse(atrialFib, "atrialFib is null");
            Objects.requireNonNullElse(valveMalfunction, "valveMalfunction is null");
            Objects.requireNonNullElse(venousTromb, "venousTromb is null");
            Objects.requireNonNullElse(other, "other is null");

            Objects.requireNonNullElse(otherChildIndication, "otherChildIndication is null");
            Objects.requireNonNullElse(dcconversion, "dcconversion is null");
            Objects.requireNonNullElse(dctherapydropout, "dctherapydropout is null");
            Objects.requireNonNullElse(periodLength, "periodLength is null");
            Objects.requireNonNullElse(medicin, "medicin is null");
            Objects.requireNonNullElse(doseMode, "doseMode is null");
            Objects.requireNonNullElse(creaintervalFirstyear, "creaintervalFirstyear is null");
            Objects.requireNonNullElse(startDate, "startDate is null");
            Objects.requireNonNullElse(endDate, "startDate is null");
            Objects.requireNonNullElse(creaComplaTestcreated, "creaComplaTestcreated is null");

            Objects.requireNonNullElse(creaComplFolYear, "creaComplFolYear is null");
            Objects.requireNonNullElse(creaComplFirstYear, "creaComplFirstYear is null");
            Objects.requireNonNullElse(reasonStopped, "reasonStopped is null");
            Objects.requireNonNullElse(continueLateCheck, "continueLateCheck is null");
            Objects.requireNonNullElse(createdBy, "createdBy is null");
            Objects.requireNonNullElse(updatedBy, "updatedBy is null");
            Objects.requireNonNullElse(lengthcomment, "lengthcomment is null");
            Objects.requireNonNullElse(inrmethod, "inrmethod is null");
        } catch (Exception exp) {
            throw new OrdinationperiodException(exp.getMessage());
        }
    }

    public String getFornamn() {
        return fornamn;
    }

    public void setFornamn(String fornamn) {
        this.fornamn = fornamn;
    }

    public String getEfternamn() {
        return efternamn;
    }

    public void setEfternamn(String efternamn) {
        this.efternamn = efternamn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Short getSsntype() {
        return ssntype;
    }

    public void setSsntype(Short ssntype) {
        this.ssntype = ssntype;
    }

    public String getPaltext() {
        return paltext;
    }

    public void setPaltext(String paltext) {
        this.paltext = paltext;
    }

    public Short getMedicinetype() {
        return medicinetype;
    }

    public void setMedicinetype(Short medicinetype) {
        this.medicinetype = medicinetype;
    }

    public Short getAtrialFib() {
        return atrialFib;
    }

    public void setAtrialFib(Short atrialFib) {
        this.atrialFib = atrialFib;
    }

    public Short getValveMalfunction() {
        return valveMalfunction;
    }

    public void setValveMalfunction(Short valveMalfunction) {
        this.valveMalfunction = valveMalfunction;
    }

    public Short getVenousTromb() {
        return venousTromb;
    }

    public void setVenousTromb(Short venousTromb) {
        this.venousTromb = venousTromb;
    }

    public Short getOther() {
        return other;
    }

    public void setOther(Short other) {
        this.other = other;
    }

    public Short getOtherChildIndication() {
        return otherChildIndication;
    }

    public void setOtherChildIndication(Short otherChildIndication) {
        this.otherChildIndication = otherChildIndication;
    }

    public Short getDcconversion() {
        return dcconversion;
    }

    public void setDcconversion(Short dcconversion) {
        this.dcconversion = dcconversion;
    }

    public Short getDctherapydropout() {
        return dctherapydropout;
    }

    public void setDctherapydropout(Short dctherapydropout) {
        this.dctherapydropout = dctherapydropout;
    }

    public Short getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(Short periodLength) {
        this.periodLength = periodLength;
    }

    public Short getMedicin() {
        return medicin;
    }

    public void setMedicin(Short medicin) {
        this.medicin = medicin;
    }

    public Short getDoseMode() {
        return doseMode;
    }

    public void setDoseMode(Short doseMode) {
        this.doseMode = doseMode;
    }

    public Short getCreaintervalFirstyear() {
        return creaintervalFirstyear;
    }

    public void setCreaintervalFirstyear(Short creaintervalFirstyear) {
        this.creaintervalFirstyear = creaintervalFirstyear;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreaComplaTestcreated() {
        return creaComplaTestcreated;
    }

    public void setCreaComplaTestcreated(Date creaComplaTestcreated) {
        this.creaComplaTestcreated = creaComplaTestcreated;
    }

    public int getCreaComplFolYear() {
        return creaComplFolYear;
    }

    public void setCreaComplFolYear(int creaComplFolYear) {
        this.creaComplFolYear = creaComplFolYear;
    }

    public int getCreaComplFirstYear() {
        return creaComplFirstYear;
    }

    public void setCreaComplFirstYear(int creaComplFirstYear) {
        this.creaComplFirstYear = creaComplFirstYear;
    }

    public Short getReasonStopped() {
        return reasonStopped;
    }

    public void setReasonStopped(Short reasonStopped) {
        this.reasonStopped = reasonStopped;
    }

    public Date getContinueLateCheck() {
        return continueLateCheck;
    }

    public void setContinueLateCheck(Date continueLateCheck) {
        this.continueLateCheck = continueLateCheck;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getLengthcomment() {
        return lengthcomment;
    }

    public void setLengthcomment(String lengthcomment) {
        this.lengthcomment = lengthcomment;
    }

    public Short getInrmethod() {
        return inrmethod;
    }

    public void setInrmethod(Short inrmethod) {
        this.inrmethod = inrmethod;
    }

    @Override
    public String toString() {
        return "Ordinationperiod{" +
                "fornamn='" + fornamn + '\'' +
                ", efternamn='" + efternamn + '\'' +
                ", ssn='" + ssn + '\'' +
                ", ssntype=" + ssntype +
                ", paltext='" + paltext + '\'' +
                ", medicinetype=" + medicinetype +
                ", atrialFib=" + atrialFib +
                ", valveMalfunction=" + valveMalfunction +
                ", venousTromb=" + venousTromb +
                ", other=" + other +
                ", otherChildIndication=" + otherChildIndication +
                ", dcconversion=" + dcconversion +
                ", dctherapydropout=" + dctherapydropout +
                ", periodLength=" + periodLength +
                ", medicin=" + medicin +
                ", doseMode=" + doseMode +
                ", creaintervalFirstyear=" + creaintervalFirstyear +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", creaComplaTestcreated=" + creaComplaTestcreated +
                ", creaComplFolYear=" + creaComplFolYear +
                ", creaComplFirstYear=" + creaComplFirstYear +
                ", reasonStopped=" + reasonStopped +
                ", continueLateCheck=" + continueLateCheck +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", lengthcomment='" + lengthcomment + '\'' +
                ", inrmethod=" + inrmethod +
                '}';
    }
}
