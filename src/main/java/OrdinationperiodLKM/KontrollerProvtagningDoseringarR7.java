package OrdinationperiodLKM;

import Person.PatientGeneralData;
import Person.PatientGeneralDataException;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;


public class KontrollerProvtagningDoseringarR7 extends PatientGeneralData implements OrdinationID {
    private int oid;

    /* Nästa kontroll om X månader eller ett datum */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateNextVisit;

    private int inrIntervalId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date ordinationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    /* veckodoser */
    private float mondayDose;
    private float tuesdayDose;
    private float wednesdayDose;
    private float thursdayDose;
    private float fridayDose;
    private float saturdayDose;
    private float sundayDose;
    private String commentDose;

    /* medicin text (ordinationperiod) */
    private String medicinText;
    private Short doseMode;
    /* waran dose reduced */
    private String reducedTypeTXT;
    private float mondayDoseReduced;
    private float tuesdayDoseReduced;
    private float wednesdayDoseReduced;
    private float thursdayDoseReduced;
    private float fridayDoseReduced;
    private float saturdayDoseReduced;
    private float sundayDoseReduced;

    private String reducedComment;
    /* inr */
    private float inrValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date inrDate;

    private String labremComment;
    private String specimenComment;
    private String analysisComment;

    private String laboratoryId;
    private String medicineTypeText;
    private String analysisPathol;
    /* Coagucheck, datum för kalibrering */
    private String inrMethod;
    /* Kreatinin (eller pk(INR) */
    private int labresultId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date planedDateCreatinin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date testDateCreatinin;

    private Short creatinin;
    private Short egfr;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date followupDate;
    private String commentCreatinin;
    /* labresult */
    private String analysisCodeLab;
    private float sampleValueLab;
    private String sampleValueText;
    private String analysisCommentLab;
    /* Change */
    private float systemsOrdinationSugg;
    private int systemsIntervalSugg;

    private float userOrdination;
    private int userInterval;

    /* ANSVARIG och TIDSSTÄMPEL */
    private String opCreatedBy;
    private String opUpdateddBy;
    private Timestamp opTSCreated;

    private String inrCreatedBy;
    private String inrUpdateddBy;
    private Timestamp inrTSCreated;

    private String creaCreatedBy;
    private String creaUpdateddBy;
    private Timestamp creaTSCreated;

    private String labCreatedBy;

    private String warCreatedBy;
    private String warUpdateddBy;
    private Timestamp warTSCreated;

    public KontrollerProvtagningDoseringarR7(int oid,
                                             int pid,
                                             String patFirstName,
                                             String patLastName,
                                             String ssn,

                                             Short ssnType,
                                             Date dateNextVisit,
                                             int inrIntervalId,
                                             Date ordinationDate,
                                             Date startDate,

                                             Date endDate,
                                             float mondayDose,
                                             float tuesdayDose,
                                             float wednesdayDose,
                                             float thursdayDose,

                                             float fridayDose,
                                             float saturdayDose,
                                             float sundayDose,
                                             String commentDose,
                                             String medicinText,

                                             Short doseMode,
                                             String reducedTypeTXT,
                                             float mondayDoseReduced,
                                             float tuesdayDoseReduced,
                                             float wednesdayDoseReduced,

                                             float thursdayDoseReduced,
                                             float fridayDoseReduced,
                                             float saturdayDoseReduced,
                                             float sundayDoseReduced,
                                             String reducedComment,

                                             float inrValue,
                                             Date inrDate,
                                             String labremComment,
                                             String specimenComment,
                                             String analysisComment,

                                             String laboratoryId,
                                             String medicineTypeText,
                                             String analysisPathol,
                                             String inrMethod,
                                             int labresultId,

                                             Date planedDateCreatinin,
                                             Date testDateCreatinin,
                                             Short creatinin,
                                             Short egfr,
                                             Date followupDate,

                                             String commentCreatinin,
                                             String analysisCodeLab,
                                             float sampleValueLab,
                                             String sampleValueText,
                                             String analysisCommentLab,

                                             float systemsOrdinationSugg,
                                             int systemsIntervalSugg,
                                             float userOrdination,
                                             int userInterval,
                                             String opCreatedBy,

                                             String opUpdateddBy,
                                             Timestamp opTSCreated,
                                             String inrCreatedBy,
                                             String inrUpdateddBy,
                                             Timestamp inrTSCreated,

                                             String creaCreatedBy,
                                             String creaUpdateddBy,
                                             Timestamp creaTSCreated,
                                             String labCreatedBy,                       // LAB has not: updatedby, tscreatedby

                                             String warCreatedBy,
                                             String warUpdateddBy,
                                             Timestamp warTSCreated) throws PatientGeneralDataException, KontrollerProvtagningDoseringarException {
        super(pid, patFirstName, patLastName, ssn, ssnType);
        this.oid = oid;
        this.dateNextVisit = dateNextVisit;
        this.inrIntervalId = inrIntervalId;
        this.ordinationDate = ordinationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mondayDose = mondayDose;
        this.tuesdayDose = tuesdayDose;
        this.wednesdayDose = wednesdayDose;
        this.thursdayDose = thursdayDose;
        this.fridayDose = fridayDose;
        this.saturdayDose = saturdayDose;
        this.sundayDose = sundayDose;
        this.commentDose = commentDose;
        this.medicinText = medicinText;
        this.doseMode = doseMode;
        this.reducedTypeTXT = reducedTypeTXT;
        this.mondayDoseReduced = mondayDoseReduced;
        this.tuesdayDoseReduced = tuesdayDoseReduced;
        this.wednesdayDoseReduced = wednesdayDoseReduced;
        this.thursdayDoseReduced = thursdayDoseReduced;
        this.fridayDoseReduced = fridayDoseReduced;
        this.saturdayDoseReduced = saturdayDoseReduced;
        this.sundayDoseReduced = sundayDoseReduced;
        this.reducedComment = reducedComment;
        this.inrValue = inrValue;
        this.inrDate = inrDate;
        this.labremComment = labremComment;
        this.specimenComment = specimenComment;
        this.analysisComment = analysisComment;
        this.laboratoryId = laboratoryId;
        this.medicineTypeText = medicineTypeText;
        this.analysisPathol = analysisPathol;
        this.inrMethod = inrMethod;
        this.labresultId = labresultId;
        this.planedDateCreatinin = planedDateCreatinin;
        this.testDateCreatinin = testDateCreatinin;
        this.creatinin = creatinin;
        this.egfr = egfr;
        this.followupDate = followupDate;
        this.commentCreatinin = commentCreatinin;
        this.analysisCodeLab = analysisCodeLab;
        this.sampleValueLab = sampleValueLab;
        this.sampleValueText = sampleValueText;
        this.analysisCommentLab = analysisCommentLab;
        this.systemsOrdinationSugg = systemsOrdinationSugg;
        this.systemsIntervalSugg = systemsIntervalSugg;
        this.userOrdination = userOrdination;
        this.userInterval = userInterval;
        this.opCreatedBy = opCreatedBy;
        this.opUpdateddBy = opUpdateddBy;
        this.opTSCreated = opTSCreated;
        this.inrCreatedBy = inrCreatedBy;
        this.inrUpdateddBy = inrUpdateddBy;
        this.inrTSCreated = inrTSCreated;
        this.creaCreatedBy = creaCreatedBy;
        this.creaUpdateddBy = creaUpdateddBy;
        this.creaTSCreated = creaTSCreated;
        this.labCreatedBy = labCreatedBy;
        this.warCreatedBy = warCreatedBy;
        this.warUpdateddBy = warUpdateddBy;
        this.warTSCreated = warTSCreated;

        try {
            Objects.requireNonNullElse(oid, "oid is null");
            Objects.requireNonNullElse(patFirstName, "firstName is null");
            Objects.requireNonNullElse(patLastName, "lastName is null");
            Objects.requireNonNullElse(ssn, "ssn is null");
            Objects.requireNonNullElse(ssnType, "ssnType is null");

            Objects.requireNonNullElse(dateNextVisit, "dateNextVisit is null");
            Objects.requireNonNullElse(inrIntervalId, "inrIntervalId is null");
            Objects.requireNonNullElse(ordinationDate, "ordinationDate is null");
            Objects.requireNonNullElse(mondayDose, "mondayDose is null");
            Objects.requireNonNullElse(tuesdayDose, "tuesdayDose is null");
            Objects.requireNonNullElse(wednesdayDose, "wednesdayDose is null");
            Objects.requireNonNullElse(thursdayDose, "thursdayDose is null");
            Objects.requireNonNullElse(fridayDose, "fridayDose is null");
            Objects.requireNonNullElse(saturdayDose, "saturdayDose is null");
            Objects.requireNonNullElse(sundayDose, "sundayDose is null");
            Objects.requireNonNullElse(commentDose, "commentDose is null");

            Objects.requireNonNullElse(medicinText, "medicinText is null");
            Objects.requireNonNullElse(doseMode, "doseMode is null");
            Objects.requireNonNullElse(reducedTypeTXT, "reducedTypeTXT is null");
            Objects.requireNonNullElse(mondayDoseReduced, "mondayDoseReduced is null");
            Objects.requireNonNullElse(tuesdayDoseReduced, "tuesdayDoseReduced is null");
            Objects.requireNonNullElse(wednesdayDoseReduced, "wednesdayDoseReduced is null");
            Objects.requireNonNullElse(thursdayDoseReduced, "thursdayDoseReduced is null");
            Objects.requireNonNullElse(fridayDoseReduced, "fridayDoseReduced is null");
            Objects.requireNonNullElse(saturdayDoseReduced, "saturdayDoseReduced is null");
            Objects.requireNonNullElse(sundayDoseReduced, "sundayDoseReduced is null");

            Objects.requireNonNullElse(reducedComment, "reducedComment is null");
            Objects.requireNonNullElse(inrValue, "inrValue is null");
            Objects.requireNonNullElse(inrDate, "inrDate is null");
            Objects.requireNonNullElse(laboratoryId, "laboratoryId is null");
            Objects.requireNonNullElse(medicineTypeText, "medicineTypeText is null");
            Objects.requireNonNullElse(analysisPathol, "analysisPathol is null");
            Objects.requireNonNullElse(inrMethod, "inrMethod is null");
            Objects.requireNonNullElse(labresultId, "labresultId is null");
            Objects.requireNonNullElse(planedDateCreatinin, "planedDateCreatinin is null");
            Objects.requireNonNullElse(testDateCreatinin, "testDateCreatinin is null");

            Objects.requireNonNullElse(creatinin, "creatinin is null");
            Objects.requireNonNullElse(egfr, "egfr is null");
            Objects.requireNonNullElse(followupDate, "followupDate is null");
            Objects.requireNonNullElse(commentCreatinin, "commentCreatinin is null");
            Objects.requireNonNullElse(analysisCodeLab, "analysisCodeLab is null");
            Objects.requireNonNullElse(sampleValueLab, "sampleValueLab is null");
            Objects.requireNonNullElse(sampleValueText, "sampleValueText is null");
            Objects.requireNonNullElse(analysisCommentLab, "analysisCommentLab is null");
            Objects.requireNonNullElse(systemsOrdinationSugg, "systemsOrdinationSugg is null");
            Objects.requireNonNullElse(systemsIntervalSugg, "systemsIntervalSugg is null");

            Objects.requireNonNullElse(userOrdination, "userOrdination is null");
            Objects.requireNonNullElse(userInterval, "userInterval is null");
        } catch (Exception exp) {
            throw new KontrollerProvtagningDoseringarException(exp.getMessage());
        }
    }

    @Override
    public int getOid() {
        return oid;
    }

    public Date getDateNextVisit() {
        return dateNextVisit;
    }

    public int getInrIntervalId() {
        return inrIntervalId;
    }

    public Date getOrdinationDate() {
        return ordinationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getMondayDose() {
        return mondayDose;
    }

    public float getTuesdayDose() {
        return tuesdayDose;
    }

    public float getWednesdayDose() {
        return wednesdayDose;
    }

    public float getThursdayDose() {
        return thursdayDose;
    }

    public float getFridayDose() {
        return fridayDose;
    }

    public float getSaturdayDose() {
        return saturdayDose;
    }

    public float getSundayDose() {
        return sundayDose;
    }

    public String getCommentDose() {
        return commentDose;
    }

    public String getMedicinText() {
        return medicinText;
    }

    public Short getDoseMode() {
        return doseMode;
    }

    public String getReducedTypeTXT() {
        return reducedTypeTXT;
    }

    public float getMondayDoseReduced() {
        return mondayDoseReduced;
    }

    public float getTuesdayDoseReduced() {
        return tuesdayDoseReduced;
    }

    public float getWednesdayDoseReduced() {
        return wednesdayDoseReduced;
    }

    public float getThursdayDoseReduced() {
        return thursdayDoseReduced;
    }

    public float getFridayDoseReduced() {
        return fridayDoseReduced;
    }

    public float getSaturdayDoseReduced() {
        return saturdayDoseReduced;
    }

    public float getSundayDoseReduced() {
        return sundayDoseReduced;
    }

    public String getReducedComment() {
        return reducedComment;
    }

    public float getInrValue() {
        return inrValue;
    }

    public Date getInrDate() {
        return inrDate;
    }

    public String getLabremComment() {
        return labremComment;
    }

    public String getSpecimenComment() {
        return specimenComment;
    }

    public String getAnalysisComment() {
        return analysisComment;
    }

    public String getLaboratoryId() {
        return laboratoryId;
    }

    public String getMedicineTypeText() {
        return medicineTypeText;
    }

    public String getAnalysisPathol() {
        return analysisPathol;
    }

    public String getInrMethod() {
        return inrMethod;
    }

    public int getLabresultId() {
        return labresultId;
    }

    public Date getPlanedDateCreatinin() {
        return planedDateCreatinin;
    }

    public Date getTestDateCreatinin() {
        return testDateCreatinin;
    }

    public Short getCreatinin() {
        return creatinin;
    }

    public Short getEgfr() {
        return egfr;
    }

    public Date getFollowupDate() {
        return followupDate;
    }

    public String getCommentCreatinin() {
        return commentCreatinin;
    }

    public String getAnalysisCodeLab() {
        return analysisCodeLab;
    }

    public float getSampleValueLab() {
        return sampleValueLab;
    }

    public String getSampleValueText() {
        return sampleValueText;
    }

    public String getAnalysisCommentLab() {
        return analysisCommentLab;
    }

    public float getSystemsOrdinationSugg() {
        return systemsOrdinationSugg;
    }

    public int getSystemsIntervalSugg() {
        return systemsIntervalSugg;
    }

    public float getUserOrdination() {
        return userOrdination;
    }

    public int getUserInterval() {
        return userInterval;
    }

    public String getOpCreatedBy() {
        return opCreatedBy;
    }

    public String getOpUpdateddBy() {
        return opUpdateddBy;
    }

    public Timestamp getOpTSCreated() {
        return opTSCreated;
    }

    public String getInrCreatedBy() {
        return inrCreatedBy;
    }

    public String getInrUpdateddBy() {
        return inrUpdateddBy;
    }

    public Timestamp getInrTSCreated() {
        return inrTSCreated;
    }

    public String getCreaCreatedBy() {
        return creaCreatedBy;
    }

    public String getCreaUpdateddBy() {
        return creaUpdateddBy;
    }

    public Timestamp getCreaTSCreated() {
        return creaTSCreated;
    }

    public String getLabCreatedBy() {
        return labCreatedBy;
    }

    public String getWarCreatedBy() {
        return warCreatedBy;
    }

    public String getWarUpdateddBy() {
        return warUpdateddBy;
    }

    public Timestamp getWarTSCreated() {
        return warTSCreated;
    }

    @Override
    public String toString() {
        return "KontrollerProvtagningDoseringarR7{" +
                "oid=" + oid +
                ", dateNextVisit=" + dateNextVisit +
                ", inrIntervalId=" + inrIntervalId +
                ", ordinationDate=" + ordinationDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", mondayDose=" + mondayDose +
                ", tuesdayDose=" + tuesdayDose +
                ", wednesdayDose=" + wednesdayDose +
                ", thursdayDose=" + thursdayDose +
                ", fridayDose=" + fridayDose +
                ", saturdayDose=" + saturdayDose +
                ", sundayDose=" + sundayDose +
                ", commentDose='" + commentDose + '\'' +
                ", medicinText='" + medicinText + '\'' +
                ", doseMode=" + doseMode +
                ", reducedTypeTXT='" + reducedTypeTXT + '\'' +
                ", mondayDoseReduced=" + mondayDoseReduced +
                ", tuesdayDoseReduced=" + tuesdayDoseReduced +
                ", wednesdayDoseReduced=" + wednesdayDoseReduced +
                ", thursdayDoseReduced=" + thursdayDoseReduced +
                ", fridayDoseReduced=" + fridayDoseReduced +
                ", saturdayDoseReduced=" + saturdayDoseReduced +
                ", sundayDoseReduced=" + sundayDoseReduced +
                ", reducedComment='" + reducedComment + '\'' +
                ", inrValue=" + inrValue +
                ", inrDate=" + inrDate +
                ", labremComment='" + labremComment + '\'' +
                ", specimenComment='" + specimenComment + '\'' +
                ", analysisComment='" + analysisComment + '\'' +
                ", laboratoryId='" + laboratoryId + '\'' +
                ", medicineTypeText='" + medicineTypeText + '\'' +
                ", analysisPathol='" + analysisPathol + '\'' +
                ", inrMethod='" + inrMethod + '\'' +
                ", labresultId=" + labresultId +
                ", planedDateCreatinin=" + planedDateCreatinin +
                ", testDateCreatinin=" + testDateCreatinin +
                ", creatinin=" + creatinin +
                ", egfr=" + egfr +
                ", followupDate=" + followupDate +
                ", commentCreatinin='" + commentCreatinin + '\'' +
                ", analysisCodeLab='" + analysisCodeLab + '\'' +
                ", sampleValueLab=" + sampleValueLab +
                ", sampleValueText='" + sampleValueText + '\'' +
                ", analysisCommentLab='" + analysisCommentLab + '\'' +
                ", systemsOrdinationSugg=" + systemsOrdinationSugg +
                ", systemsIntervalSugg=" + systemsIntervalSugg +
                ", userOrdination=" + userOrdination +
                ", userInterval=" + userInterval +
                ", opCreatedBy='" + opCreatedBy + '\'' +
                ", opUpdateddBy='" + opUpdateddBy + '\'' +
                ", opTSCreated=" + opTSCreated +
                ", inrCreatedBy='" + inrCreatedBy + '\'' +
                ", inrUpdateddBy='" + inrUpdateddBy + '\'' +
                ", inrTSCreated=" + inrTSCreated +
                ", creaCreatedBy='" + creaCreatedBy + '\'' +
                ", creaUpdateddBy='" + creaUpdateddBy + '\'' +
                ", creaTSCreated=" + creaTSCreated +
                ", labCreatedBy='" + labCreatedBy + '\'' +
                ", warCreatedBy='" + warCreatedBy + '\'' +
                ", warUpdateddBy='" + warUpdateddBy + '\'' +
                ", warTSCreated=" + warTSCreated +
                '}';
    }
}
