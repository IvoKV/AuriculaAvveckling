package ordination.KontrollerProvtagningDoseringar;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.Objects;

public class Ordinationperiod {
    private int oid;
    private String firstName;                 // regionpatient
    private String lastName;               // regionpatient
    private String ssn;                     // patient
    private Short ssntype;                  // patient
    /* PAL */
    private String palFirstName;
    private String palLastName;
    private String palTitle;
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
                            String firstName,
                            String lastName,
                            String ssn,
                            Short ssntype,
                            String palFirstName,
                            String palLastName,
                            String palTitle,
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
                            Short complfolYear) throws OrdinationperiodException {
        this.oid = oid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.ssntype = ssntype;
        this.palFirstName = palFirstName;
        this.palLastName = palLastName;
        this.palTitle = palTitle;
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
            Objects.requireNonNullElse(firstName, "inget förnamn");
            Objects.requireNonNullElse(lastName, "inget lastName");
            Objects.requireNonNullElse(ssn, "inget personnummer");
            Objects.requireNonNullElse(ssntype, "ingen typ för personnummer");
            Objects.requireNonNullElse(palFirstName, "ingen typ för palFirstName");
            Objects.requireNonNullElse(palLastName, "ingen typ för palLastName");
            Objects.requireNonNullElse(palTitle, "ingen typ för palTitle");
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public Short getSsntype() {
        return ssntype;
    }

    public String getPalFirstName() {
        return palFirstName;
    }

    public String getPalLastName() {
        return palLastName;
    }

    public String getPalTitle() {
        return palTitle;
    }

    public String getMedicinetype() {
        return medicinetype;
    }

    public String getAtrialFib() {
        return atrialFib;
    }

    public String getValveMalfunction() {
        return valveMalfunction;
    }

    public String getVenousTromb() {
        return venousTromb;
    }

    public String getOther() {
        return other;
    }

    public String getOtherChildIndication() {
        return otherChildIndication;
    }

    public String getDcconversion() {
        return dcconversion;
    }

    public Short getDctherapydropout() {
        return dctherapydropout;
    }

    public String getPeriodLength() {
        return periodLength;
    }

    public String getMedicin() {
        return medicin;
    }

    public String getDoseMode() {
        return doseMode;
    }

    public String getCreainterval() {
        return creainterval;
    }

    public String getCreaintervalFirstyear() {
        return creaintervalFirstyear;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getCreaComplaTestcreated() {
        return creaComplaTestcreated;
    }

    public int getCreaComplFolYear() {
        return creaComplFolYear;
    }

    public String getCreaComplFirstYear() {
        return creaComplFirstYear;
    }

    public String getReasonStopped() {
        return reasonStopped;
    }

    public Date getContinueLateCheck() {
        return continueLateCheck;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getLengthcomment() {
        return lengthcomment;
    }

    public String getInrmethod() {
        return inrmethod;
    }

    public Short getComplfolYear() {
        return complfolYear;
    }

    @Override
    public String toString() {
        return "Ordinationperiod{" +
                "oid=" + oid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", ssntype=" + ssntype +
                ", palFirstName='" + palFirstName + '\'' +
                ", palLastName='" + palLastName + '\'' +
                ", palTitle='" + palTitle + '\'' +
                ", medicinetype='" + medicinetype + '\'' +
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
