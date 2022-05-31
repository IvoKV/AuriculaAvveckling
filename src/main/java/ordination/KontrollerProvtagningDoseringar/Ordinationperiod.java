package ordination.KontrollerProvtagningDoseringar;

import Person.PatientGeneralData;
import Person.PatientGeneralDataException;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.Objects;

public class Ordinationperiod extends PatientGeneralData {
    private int oid;
    private int pid;
    private String medicinetype;             // (sql)
    private String atrialFib;                // se xml!
    private String valveMalfunction;         // se xml!

    private String venousTromb;              // se xml!
    private String other;                    // se xml!
    private String otherChildIndication;     // se xml!
    private String dcconversion;             // se xml!
    private Short dctherapydropout;         // -

    private String periodLength;             // -
    private String medicin;                  // se xml!
    private String doseMode;                 // se xml!
    private String creainterval;
    private String creaintervalFirstyear;    // se xml!

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date creaComplaTestcreated;
    private int creaComplFolYear;           // (90,182,365)
    private String creaComplFirstYear;         // se xml!  (0,1,3,4)

    private String reasonStopped;            // se xml!
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date continueLateCheck;
    private String createdBy;
    private String updatedBy;
    private String lengthcomment;

    private String inrmethod;
    private Short complfolYear;

    public Ordinationperiod(int oid,
                            int pid,
                            String firstName,
                            String lastName,
                            String ssn,

                            Short ssnType,
                            String medicinetype,
                            String atrialFib,
                            String valveMalfunction,
                            String venousTromb,

                            String other,
                            String otherChildIndication,
                            String dcconversion,
                            Short dctherapydropout,
                            String periodLength,

                            String medicin,
                            String doseMode,
                            String creainterval,
                            String creaintervalFirstyear,
                            Date startDate,

                            Date endDate,
                            Date creaComplaTestcreated,
                            int creaComplFolYear,
                            String creaComplFirstYear,
                            String reasonStopped,

                            Date continueLateCheck,
                            String createdBy,
                            String updatedBy,
                            String lengthcomment,
                            String inrmethod,

                            Short complfolYear) throws OrdinationperiodException, PatientGeneralDataException {
        super(pid, firstName, lastName, ssn, ssnType);
        this.oid = oid;
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
        this.creainterval = creainterval;
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

        this.complfolYear = complfolYear;
        try {
            Objects.requireNonNullElse(oid, "inget oid");
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
            Objects.requireNonNullElse(creainterval, "creainterval is null");
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

            Objects.requireNonNullElse(complfolYear, "complfolYear is null");
        } catch (Exception exp) {
            throw new OrdinationperiodException(exp.getMessage());
        }
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    @Override
    public int getPid() {
        return pid;
    }

    @Override
    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getMedicinetype() {
        return medicinetype;
    }

    public void setMedicinetype(String medicinetype) {
        this.medicinetype = medicinetype;
    }

    public String getAtrialFib() {
        return atrialFib;
    }

    public void setAtrialFib(String atrialFib) {
        this.atrialFib = atrialFib;
    }

    public String getValveMalfunction() {
        return valveMalfunction;
    }

    public void setValveMalfunction(String valveMalfunction) {
        this.valveMalfunction = valveMalfunction;
    }

    public String getVenousTromb() {
        return venousTromb;
    }

    public void setVenousTromb(String venousTromb) {
        this.venousTromb = venousTromb;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOtherChildIndication() {
        return otherChildIndication;
    }

    public void setOtherChildIndication(String otherChildIndication) {
        this.otherChildIndication = otherChildIndication;
    }

    public String getDcconversion() {
        return dcconversion;
    }

    public void setDcconversion(String dcconversion) {
        this.dcconversion = dcconversion;
    }

    public Short getDctherapydropout() {
        return dctherapydropout;
    }

    public void setDctherapydropout(Short dctherapydropout) {
        this.dctherapydropout = dctherapydropout;
    }

    public String getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(String periodLength) {
        this.periodLength = periodLength;
    }

    public String getMedicin() {
        return medicin;
    }

    public void setMedicin(String medicin) {
        this.medicin = medicin;
    }

    public String getDoseMode() {
        return doseMode;
    }

    public void setDoseMode(String doseMode) {
        this.doseMode = doseMode;
    }

    public String getCreainterval() {
        return creainterval;
    }

    public void setCreainterval(String creainterval) {
        this.creainterval = creainterval;
    }

    public String getCreaintervalFirstyear() {
        return creaintervalFirstyear;
    }

    public void setCreaintervalFirstyear(String creaintervalFirstyear) {
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

    public String getCreaComplFirstYear() {
        return creaComplFirstYear;
    }

    public void setCreaComplFirstYear(String creaComplFirstYear) {
        this.creaComplFirstYear = creaComplFirstYear;
    }

    public String getReasonStopped() {
        return reasonStopped;
    }

    public void setReasonStopped(String reasonStopped) {
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

    public String getInrmethod() {
        return inrmethod;
    }

    public void setInrmethod(String inrmethod) {
        this.inrmethod = inrmethod;
    }

    public Short getComplfolYear() {
        return complfolYear;
    }

    public void setComplfolYear(Short complfolYear) {
        this.complfolYear = complfolYear;
    }

    @Override
    public String toString() {
        return "Ordinationperiod{" +
                "medicinetype='" + medicinetype + '\'' +
                ", atrialFib='" + atrialFib + '\'' +
                ", valveMalfunction='" + valveMalfunction + '\'' +
                ", venousTromb='" + venousTromb + '\'' +
                ", other='" + other + '\'' +
                ", otherChildIndication='" + otherChildIndication + '\'' +
                ", dcconversion='" + dcconversion + '\'' +
                ", dctherapydropout=" + dctherapydropout +
                ", periodLength='" + periodLength + '\'' +
                ", medicin='" + medicin + '\'' +
                ", doseMode='" + doseMode + '\'' +
                ", creainterval='" + creainterval + '\'' +
                ", creaintervalFirstyear='" + creaintervalFirstyear + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", creaComplaTestcreated=" + creaComplaTestcreated +
                ", creaComplFolYear=" + creaComplFolYear +
                ", creaComplFirstYear='" + creaComplFirstYear + '\'' +
                ", reasonStopped='" + reasonStopped + '\'' +
                ", continueLateCheck=" + continueLateCheck +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", lengthcomment='" + lengthcomment + '\'' +
                ", inrmethod='" + inrmethod + '\'' +
                ", complfolYear=" + complfolYear +
                '}';
    }
}
