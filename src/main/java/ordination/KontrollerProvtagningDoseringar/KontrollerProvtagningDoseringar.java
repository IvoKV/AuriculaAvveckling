package ordination.KontrollerProvtagningDoseringar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.Objects;


public class KontrollerProvtagningDoseringar {
    @JsonProperty(index = 1)
    private String id;
    @JsonProperty(index = 2)
    private String ssn;
    @JsonProperty(index = 3)
    private Short ssnType;
    @JsonProperty(index = 4)
    private String firstName;
    @JsonProperty(index = 5)
    private String lastName;
    @JsonProperty(index = 6)
    private String city;

    /* ansvarig läkare */
    @JsonProperty(index = 7)
    private String palText;

    /* Nästa kontroll om X månader eller ett datum */
    @JsonProperty(index = 8)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateNextVisit;
    @JsonProperty(index = 9)
    private int inrIntervalId;
    @JsonProperty(index = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date ordinationDate;

    /* veckodoser */
    @JsonProperty(index = 11)
    private float mondayDose;
    @JsonProperty(index = 12)
    private float tuesdayDose;
    @JsonProperty(index = 13)
    private float wednesdayDose;
    @JsonProperty(index = 14)
    private float thursdayDose;
    @JsonProperty(index = 15)
    private float fridayDose;
    @JsonProperty(index = 16)
    private float saturdayDose;
    @JsonProperty(index = 17)
    private float sundayDose;
    @JsonProperty(index = 18)
    private String commentDose;

    /* medicin text (ordinationperiod) */
    @JsonProperty(index = 19)
    private String medicinText;
    @JsonProperty(index = 20)
    private Short doseMode;


    /* waran dose reduced */
    @JsonProperty(index = 21)
    private String reducedTypeTXT;
    @JsonProperty(index = 22)
    private float mondayDoseReduced;
    @JsonProperty(index = 23)
    private float tuesdayDoseReduced;
    @JsonProperty(index = 24)
    private float wednesdayDoseReduced;
    @JsonProperty(index = 25)
    private float thursdayDoseReduced;
    @JsonProperty(index = 26)
    private float fridayDoseReduced;
    @JsonProperty(index = 27)
    private float saturdayDoseReduced;
    @JsonProperty(index = 28)
    private float sundayDoseReduced;
    @JsonProperty(index = 29)
    private String reducedComment;

    /* inr */
    @JsonProperty(index = 30)
    private float inrValue;
    @JsonProperty(index = 31)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date inrDate;
    @JsonProperty(index = 32)
    private String laboratoryId;
    @JsonProperty(index = 33)
    private String medicineTypeText;

    /* Coagucheck, datum för kalibrering */
    @JsonProperty(index = 34)
    private String inrMethod;

    /* Kreatinin (eller pk(INR) */
    @JsonProperty(index = 35)
    private int labresultId;
    @JsonProperty(index = 36)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date planedDateCreatinin;
    @JsonProperty(index = 37)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date testDateCreatinin;
    @JsonProperty(index = 38)
    private Short creatinin;
    @JsonProperty(index = 39)
    private Short egfr;
    @JsonProperty(index = 40)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date followupDate;
    @JsonProperty(index = 41)
    private String commentCreatinin;

    /* labresult */
    @JsonProperty(index = 42)
    private String analysisCodeLab;
    @JsonProperty(index = 43)
    private float sampleValueLab;
    @JsonProperty(index = 44)
    private String sampleValueText;
    @JsonProperty(index = 45)
    private String analysisCommentLab;

    /* Change */
    @JsonProperty(index = 46)
    private float systemsOrdinationSugg;
    @JsonProperty(index = 47)
    private int systemsIntervalSugg;
    @JsonProperty(index = 49)
    private float userOrdination;
    @JsonProperty(index = 50)
    private int userInterval;

    public KontrollerProvtagningDoseringar(String id,
                                           String ssn,
                                           Short ssnType,
                                           String firstName,
                                           String lastName,
                                           String city,
                                           String palText,
                                           Date dateNextVisit,
                                           int inrIntervalId,
                                           Date ordinationDate,    /* 10 */

                                           float mondayDose,
                                           float tuesdayDose,
                                           float wednesdayDose,
                                           float thursdayDose,
                                           float fridayDose,
                                           float saturdayDose,
                                           float sundayDose,
                                           String commentDose,
                                           String medicinText,
                                           Short doseMode,                  /* 20 */

                                           String reducedTypeTXT,
                                           float mondayDoseReduced,
                                           float tuesdayDoseReduced,
                                           float wednesdayDoseReduced,
                                           float thursdayDoseReduced,
                                           float fridayDoseReduced,
                                           float saturdayDoseReduced,
                                           float sundayDoseReduced,
                                           String reducedComment,
                                           float inrValue,                  /* 30 */

                                           Date inrDate,
                                           String laboratoryId,
                                           String medicineTypeText,
                                           String inrMethod,
                                           int labresultId,
                                           Date planedDateCreatinin,
                                           Date testDateCreatinin,
                                           Short creatinin,
                                           Short egfr,
                                           Date followupDate,      /* 40 */

                                           String commentCreatinin,
                                           String analysisCodeLab,
                                           float sampleValueLab,
                                           String sampleValueText,
                                           String analysisCommentLab,
                                           float systemsOrdinationSugg,
                                           int systemsIntervalSugg,
                                           float userOrdination,
                                           int userInterval) throws KontrollerProvtagningDoseringarException {
        try {
            Objects.requireNonNullElse(id, "id is null");
            Objects.requireNonNullElse(ssn, "ssn is null");
            Objects.requireNonNullElse(ssnType, "ssnType is null");
            Objects.requireNonNullElse(firstName, "fistName is null");
            Objects.requireNonNullElse(lastName, "lastName is null");
            Objects.requireNonNullElse(city, "city is null");
            Objects.requireNonNullElse(palText, "patText is null");
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
        }
        catch (NullPointerException npe){
            throw new KontrollerProvtagningDoseringarException(npe.getMessage());
        }

        this.id = id;
        this.ssn = ssn;
        this.ssnType = ssnType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.palText = palText;
        this.dateNextVisit = dateNextVisit;
        this.inrIntervalId = inrIntervalId;
        this.ordinationDate = ordinationDate;
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
        this.laboratoryId = laboratoryId;
        this.medicineTypeText = medicineTypeText;
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Short getSsnType() {
        return ssnType;
    }

    public void setSsnType(Short ssnType) {
        this.ssnType = ssnType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPatText() {
        return palText;
    }

    public void setPatText(String patText) {
        this.palText = patText;
    }

    public Date getDateNextVisit() {
        return dateNextVisit;
    }

    public void setDateNextVisit(Date dateNextVisit) {
        this.dateNextVisit = dateNextVisit;
    }

    public int getInrIntervalId() {
        return inrIntervalId;
    }

    public void setInrIntervalId(int inrIntervalId) {
        this.inrIntervalId = inrIntervalId;
    }

    public Date getOrdinationDate() {
        return ordinationDate;
    }

    public void setOrdinationDate(Date ordinationDate) {
        this.ordinationDate = ordinationDate;
    }

    public float getMondayDose() {
        return mondayDose;
    }

    public void setMondayDose(float mondayDose) {
        this.mondayDose = mondayDose;
    }

    public float getTuesdayDose() {
        return tuesdayDose;
    }

    public void setTuesdayDose(float tuesdayDose) {
        this.tuesdayDose = tuesdayDose;
    }

    public float getWednesdayDose() {
        return wednesdayDose;
    }

    public void setWednesdayDose(float wednesdayDose) {
        this.wednesdayDose = wednesdayDose;
    }

    public float getThursdayDose() {
        return thursdayDose;
    }

    public void setThursdayDose(float thursdayDose) {
        this.thursdayDose = thursdayDose;
    }

    public float getFridayDose() {
        return fridayDose;
    }

    public void setFridayDose(float fridayDose) {
        this.fridayDose = fridayDose;
    }

    public float getSaturdayDose() {
        return saturdayDose;
    }

    public void setSaturdayDose(float saturdayDose) {
        this.saturdayDose = saturdayDose;
    }

    public float getSundayDose() {
        return sundayDose;
    }

    public void setSundayDose(float sundayDose) {
        this.sundayDose = sundayDose;
    }

    public String getCommentDose() {
        return commentDose;
    }

    public void setCommentDose(String commentDose) {
        this.commentDose = commentDose;
    }

    public String getMedicinText() {
        return medicinText;
    }

    public void setMedicinText(String medicinText) {
        this.medicinText = medicinText;
    }

    public Short getDoseMode() {
        return doseMode;
    }

    public void setDoseMode(Short doseMode) {
        this.doseMode = doseMode;
    }

    public String getReducedTypeTXT() {
        return reducedTypeTXT;
    }

    public void setReducedTypeTXT(String reducedTypeTXT) {
        this.reducedTypeTXT = reducedTypeTXT;
    }

    public float getMondayDoseReduced() {
        return mondayDoseReduced;
    }

    public void setMondayDoseReduced(float mondayDoseReduced) {
        this.mondayDoseReduced = mondayDoseReduced;
    }

    public float getTuesdayDoseReduced() {
        return tuesdayDoseReduced;
    }

    public void setTuesdayDoseReduced(float tuesdayDoseReduced) {
        this.tuesdayDoseReduced = tuesdayDoseReduced;
    }

    public float getWednesdayDoseReduced() {
        return wednesdayDoseReduced;
    }

    public void setWednesdayDoseReduced(float wednesdayDoseReduced) {
        this.wednesdayDoseReduced = wednesdayDoseReduced;
    }

    public float getThursdayDoseReduced() {
        return thursdayDoseReduced;
    }

    public void setThursdayDoseReduced(float thursdayDoseReduced) {
        this.thursdayDoseReduced = thursdayDoseReduced;
    }

    public float getFridayDoseReduced() {
        return fridayDoseReduced;
    }

    public void setFridayDoseReduced(float fridayDoseReduced) {
        this.fridayDoseReduced = fridayDoseReduced;
    }

    public float getSaturdayDoseReduced() {
        return saturdayDoseReduced;
    }

    public void setSaturdayDoseReduced(float saturdayDoseReduced) {
        this.saturdayDoseReduced = saturdayDoseReduced;
    }

    public float getSundayDoseReduced() {
        return sundayDoseReduced;
    }

    public void setSundayDoseReduced(float sundayDoseReduced) {
        this.sundayDoseReduced = sundayDoseReduced;
    }

    public String getReducedComment() {
        return reducedComment;
    }

    public void setReducedComment(String reducedComment) {
        this.reducedComment = reducedComment;
    }

    public float getInrValue() {
        return inrValue;
    }

    public void setInrValue(float inrValue) {
        this.inrValue = inrValue;
    }

    public Date getInrDate() {
        return inrDate;
    }

    public void setInrDate(Date inrDate) {
        this.inrDate = inrDate;
    }

    public String getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(String laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public String getMedicineTypeText() {
        return medicineTypeText;
    }

    public void setMedicineTypeText(String medicineTypeText) {
        this.medicineTypeText = medicineTypeText;
    }

    public String getInrMethod() {
        return inrMethod;
    }

    public void setInrMethod(String inrMethod) {
        this.inrMethod = inrMethod;
    }

    public int getLabresultId() {
        return labresultId;
    }

    public void setLabresultId(int labresultId) {
        this.labresultId = labresultId;
    }

    public Date getPlanedDateCreatinin() {
        return planedDateCreatinin;
    }

    public void setPlanedDateCreatinin(Date planedDateCreatinin) {
        this.planedDateCreatinin = planedDateCreatinin;
    }

    public Date getTestDateCreatinin() {
        return testDateCreatinin;
    }

    public void setTestDateCreatinin(Date testDateCreatinin) {
        this.testDateCreatinin = testDateCreatinin;
    }

    public Short getCreatinin() {
        return creatinin;
    }

    public void setCreatinin(Short creatinin) {
        this.creatinin = creatinin;
    }

    public Short getEgfr() {
        return egfr;
    }

    public void setEgfr(Short egfr) {
        this.egfr = egfr;
    }

    public Date getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(Date followupDate) {
        this.followupDate = followupDate;
    }

    public String getCommentCreatinin() {
        return commentCreatinin;
    }

    public void setCommentCreatinin(String commentCreatinin) {
        this.commentCreatinin = commentCreatinin;
    }

    public String getAnalysisCodeLab() {
        return analysisCodeLab;
    }

    public void setAnalysisCodeLab(String analysisCodeLab) {
        this.analysisCodeLab = analysisCodeLab;
    }

    public float getSampleValueLab() {
        return sampleValueLab;
    }

    public void setSampleValueLab(float sampleValueLab) {
        this.sampleValueLab = sampleValueLab;
    }

    public String getSampleValueText() {
        return sampleValueText;
    }

    public void setSampleValueText(String sampleValueText) {
        this.sampleValueText = sampleValueText;
    }

    public String getAnalysisCommentLab() {
        return analysisCommentLab;
    }

    public void setAnalysisCommentLab(String analysisCommentLab) {
        this.analysisCommentLab = analysisCommentLab;
    }

    public float getSystemsOrdinationSugg() {
        return systemsOrdinationSugg;
    }

    public void setSystemsOrdinationSugg(float systemsOrdinationSugg) {
        this.systemsOrdinationSugg = systemsOrdinationSugg;
    }

    public int getSystemsIntervalSugg() {
        return systemsIntervalSugg;
    }

    public void setSystemsIntervalSugg(int systemsIntervalSugg) {
        this.systemsIntervalSugg = systemsIntervalSugg;
    }

    public float getUserOrdination() {
        return userOrdination;
    }

    public void setUserOrdination(float userOrdination) {
        this.userOrdination = userOrdination;
    }

    public int getUserInterval() {
        return userInterval;
    }

    public void setUserInterval(int userInterval) {
        this.userInterval = userInterval;
    }

    @Override
    public String toString() {
        return "KontrollerProvtagningDoseringar{" +
                "id='" + id + '\'' +
                ", ssn='" + ssn + '\'' +
                ", ssnType=" + ssnType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", patText='" + palText + '\'' +
                ", dateNextVisit=" + dateNextVisit +
                ", inrIntervalId=" + inrIntervalId +
                ", ordinationDate=" + ordinationDate +
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
                ", laboratoryId='" + laboratoryId + '\'' +
                ", medicineTypeText='" + medicineTypeText + '\'' +
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
                '}';
    }
}
