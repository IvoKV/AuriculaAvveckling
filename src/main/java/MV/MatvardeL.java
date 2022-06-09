package MV;

import java.sql.Timestamp;
import java.util.Objects;

public class MatvardeL {
    /* CENTREID */
    private String cId;

    /* PATIENT */
    private String ssn;
    private Short ssnType;
    private String firstName;
    private String lastName;            // 5

    /* HEMORRHAGES */
    private String hmrH1;
    private String hmrE1;
    private String hmrM;
    private String hmrR1;
    private String hmrR2;               // 10

    private String hmrH2;
    private String hmrA;
    private String hmrG;
    private String hmrE2;
    private String hmrS;                // 15

    private String hmrCreatedBy;
    private String hmrUpdatedBy;
    private Timestamp hmrTsCreated;

    /* CHADS2 */
    private String chaC;
    private String chaH;                // 20

    private String chaD;
    private String chaS;
    private String chaCreatedBy;
    private String chaUpdatedBy;
    private Timestamp chaTsCreated;     // 25

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
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

    public String getHmrH1() {
        return hmrH1;
    }

    public void setHmrH1(String hmrH1) {
        this.hmrH1 = hmrH1;
    }

    public String getHmrE1() {
        return hmrE1;
    }

    public void setHmrE1(String hmrE1) {
        this.hmrE1 = hmrE1;
    }

    public String getHmrM() {
        return hmrM;
    }

    public void setHmrM(String hmrM) {
        this.hmrM = hmrM;
    }

    public String getHmrR1() {
        return hmrR1;
    }

    public void setHmrR1(String hmrR1) {
        this.hmrR1 = hmrR1;
    }

    public String getHmrR2() {
        return hmrR2;
    }

    public void setHmrR2(String hmrR2) {
        this.hmrR2 = hmrR2;
    }

    public String getHmrH2() {
        return hmrH2;
    }

    public void setHmrH2(String hmrH2) {
        this.hmrH2 = hmrH2;
    }

    public String getHmrA() {
        return hmrA;
    }

    public void setHmrA(String hmrA) {
        this.hmrA = hmrA;
    }

    public String getHmrG() {
        return hmrG;
    }

    public void setHmrG(String hmrG) {
        this.hmrG = hmrG;
    }

    public String getHmrE2() {
        return hmrE2;
    }

    public void setHmrE2(String hmrE2) {
        this.hmrE2 = hmrE2;
    }

    public String getHmrS() {
        return hmrS;
    }

    public void setHmrS(String hmrS) {
        this.hmrS = hmrS;
    }

    public String getHmrCreatedBy() {
        return hmrCreatedBy;
    }

    public void setHmrCreatedBy(String hmrCreatedBy) {
        this.hmrCreatedBy = hmrCreatedBy;
    }

    public String getHmrUpdatedBy() {
        return hmrUpdatedBy;
    }

    public void setHmrUpdatedBy(String hmrUpdatedBy) {
        this.hmrUpdatedBy = hmrUpdatedBy;
    }

    public Timestamp getHmrTsCreated() {
        return hmrTsCreated;
    }

    public void setHmrTsCreated(Timestamp hmrTsCreated) {
        this.hmrTsCreated = hmrTsCreated;
    }

    public String getChaC() {
        return chaC;
    }

    public void setChaC(String chaC) {
        this.chaC = chaC;
    }

    public String getChaH() {
        return chaH;
    }

    public void setChaH(String chaH) {
        this.chaH = chaH;
    }

    public String getChaD() {
        return chaD;
    }

    public void setChaD(String chaD) {
        this.chaD = chaD;
    }

    public String getChaS() {
        return chaS;
    }

    public void setChaS(String chaS) {
        this.chaS = chaS;
    }

    public String getChaCreatedBy() {
        return chaCreatedBy;
    }

    public void setChaCreatedBy(String chaCreatedBy) {
        this.chaCreatedBy = chaCreatedBy;
    }

    public String getChaUpdatedBy() {
        return chaUpdatedBy;
    }

    public void setChaUpdatedBy(String chaUpdatedBy) {
        this.chaUpdatedBy = chaUpdatedBy;
    }

    public Timestamp getChaTsCreated() {
        return chaTsCreated;
    }

    public void setChaTsCreated(Timestamp chaTsCreated) {
        this.chaTsCreated = chaTsCreated;
    }

    @Override
    public String toString() {
        return "MatvardeLBuilder{" +
                "cId='" + cId + '\'' +
                ", ssn='" + ssn + '\'' +
                ", ssnType=" + ssnType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hmrH1='" + hmrH1 + '\'' +
                ", hmrE1='" + hmrE1 + '\'' +
                ", hmrM='" + hmrM + '\'' +
                ", hmrR1='" + hmrR1 + '\'' +
                ", hmrR2='" + hmrR2 + '\'' +
                ", hmrH2='" + hmrH2 + '\'' +
                ", hmrA='" + hmrA + '\'' +
                ", hmrG='" + hmrG + '\'' +
                ", hmrE2='" + hmrE2 + '\'' +
                ", hmrS='" + hmrS + '\'' +
                ", hmrCreatedBy='" + hmrCreatedBy + '\'' +
                ", hmrUpdatedBy='" + hmrUpdatedBy + '\'' +
                ", hmrTsCreated=" + hmrTsCreated +
                ", chaC='" + chaC + '\'' +
                ", chaH='" + chaH + '\'' +
                ", chaD='" + chaD + '\'' +
                ", chaS='" + chaS + '\'' +
                ", chaCreatedBy='" + chaCreatedBy + '\'' +
                ", chaUpdatedBy='" + chaUpdatedBy + '\'' +
                ", chaTsCreated=" + chaTsCreated +
                '}';
    }

    public MatvardeL(String cId, String ssn, Short ssnType, String firstName, String lastName, String hmrH1, String hmrE1, String hmrM, String hmrR1, String hmrR2, String hmrH2, String hmrA, String hmrG, String hmrE2, String hmrS, String hmrCreatedBy, String hmrUpdatedBy, Timestamp hmrTsCreated, String chaC, String chaH, String chaD, String chaS, String chaCreatedBy, String chaUpdatedBy, Timestamp chaTsCreated) throws MatvardeLBuilderException {
        this.cId = cId;
        this.ssn = ssn;
        this.ssnType = ssnType;
        this.firstName = firstName;
        this.lastName = lastName;

        this.hmrH1 = hmrH1;
        this.hmrE1 = hmrE1;
        this.hmrM = hmrM;
        this.hmrR1 = hmrR1;
        this.hmrR2 = hmrR2;

        this.hmrH2 = hmrH2;
        this.hmrA = hmrA;
        this.hmrG = hmrG;
        this.hmrE2 = hmrE2;
        this.hmrS = hmrS;

        this.hmrCreatedBy = hmrCreatedBy;
        this.hmrUpdatedBy = hmrUpdatedBy;
        this.hmrTsCreated = hmrTsCreated;
        this.chaC = chaC;
        this.chaH = chaH;

        this.chaD = chaD;
        this.chaS = chaS;
        this.chaCreatedBy = chaCreatedBy;
        this.chaUpdatedBy = chaUpdatedBy;
        this.chaTsCreated = chaTsCreated;

        try{
            Objects.requireNonNullElse(cId, "cId is null");
            Objects.requireNonNullElse(ssn, "ssn is null");
            Objects.requireNonNullElse(ssnType, "ssnType is null");
            Objects.requireNonNullElse(ssnType, "ssnType is null");
            Objects.requireNonNullElse(firstName, "firstName is null");

            Objects.requireNonNullElse(lastName, "lastName is null");
            Objects.requireNonNullElse(hmrCreatedBy, "hmrCreatedBy is null");
            Objects.requireNonNullElse(hmrUpdatedBy, "hmrUpdatedBy is null");
            Objects.requireNonNullElse(hmrTsCreated, "hmrTsCreated is null");
            Objects.requireNonNullElse(chaCreatedBy, "chaCreatedBy is null");

            Objects.requireNonNullElse(chaUpdatedBy, "chaUpdatedBy is null");
            Objects.requireNonNullElse(chaTsCreated, "chaTsCreated is null");
        }
        catch (Exception exp) {
            throw new MatvardeLBuilderException(exp.getMessage());
        }
    }
}
