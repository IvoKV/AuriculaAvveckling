package OANT;

import Person.PatientGeneralData;
import Person.PatientGeneralDataException;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class ComplicationR7 extends PatientGeneralData {
    private String cid;                              // c.id

    /** OID **/
    private int oid;

    /** Complication **/
    private String complexExists;                    // CASE!!
    private String bleeding;                        // CASE!!
    private String trombosis;                       // CASE!!
    private int daysOfCare;
    private BigDecimal PKINR;
    private String status;

    private String createdBy;
    private String updatedBy;
    private Timestamp tsCreated;

    public ComplicationR7(String cid,
                          int pid,
                          String SSN,
                          Short SSN_TYPE,
                          String patFirstname,
                          String patLastname,

                          int oid,
                          String complexExists,
                          String bleeding,
                          String trombosis,
                          int daysOfCare,
                          BigDecimal PKINR,
                          String status,

                          String createdBy,
                          String updatedBy,
                          Timestamp tsCreated
    ) throws PatientGeneralDataException, ComplicationR7Exception {
        super(pid, patFirstname, patLastname, SSN, SSN_TYPE);
        this.cid = cid;

        this.oid = oid;
        this.complexExists = complexExists;
        this.bleeding = bleeding;
        this.trombosis = trombosis;
        this.daysOfCare = daysOfCare;
        this.PKINR = PKINR;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.tsCreated = tsCreated;

        try{
            Objects.requireNonNullElse(cid, "cid is null");
            Objects.requireNonNullElse(pid, "pid is null");
            Objects.requireNonNullElse(SSN, "SSN is null");
            Objects.requireNonNullElse(SSN_TYPE, "SSN_TYPE is null");
            Objects.requireNonNullElse(patFirstname, "patFirstname is null");
            Objects.requireNonNullElse(patLastname, "patLastname is null");
            Objects.requireNonNullElse(oid, "oid is null");
        }
        catch (Exception exp){
            throw new ComplicationR7Exception(exp.getMessage());
        }
    }

    public int getOid() {
        return oid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Timestamp getTsCreated() {
        return tsCreated;
    }

    public String getCid() {

        return cid;
    }

    public String getComplexExists() {
        return complexExists;
    }

    public String getBleeding() {
        return bleeding;
    }

    public String getTrombosis() {
        return trombosis;
    }

    public int getDaysOfCare() {
        return daysOfCare;
    }

    public BigDecimal getPKINR() {
        return PKINR;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ComplicationR7{" +
                "cid='" + cid + '\'' +
                ", complexExists='" + complexExists + '\'' +
                ", bleeding='" + bleeding + '\'' +
                ", trombosis='" + trombosis + '\'' +
                ", daysOfCare=" + daysOfCare +
                ", PKINR=" + PKINR +
                ", status='" + status + '\'' +
                '}';
    }
}
