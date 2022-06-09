package Person;

import java.util.Objects;

public abstract class PatientGeneralData {
    private int pid;
    private String patFirstName;
    private String patLastName;
    private String ssn;
    private Short ssnType;

    public PatientGeneralData(int pid, String patFirstName, String patLastName, String ssn, Short ssnType) throws PatientGeneralDataException {
        this.pid = pid;
        this.patFirstName = patFirstName;
        this.patLastName = patLastName;
        this.ssn = ssn;
        this.ssnType = ssnType;

        try {
            Objects.requireNonNullElse(pid, "pat. pid is null");
            Objects.requireNonNullElse(patFirstName, "patFirstName is null");
            Objects.requireNonNullElse(patLastName, "patLastName is null");
            Objects.requireNonNullElse(ssn, "ssn is null");
            Objects.requireNonNullElse(ssnType, "ssnType is null");
        }
        catch (Exception exp) {
            throw new PatientGeneralDataException(exp.getMessage());
        }
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPatFirstName() {
        return patFirstName;
    }

    public void setPatFirstName(String patFirstName) {
        this.patFirstName = patFirstName;
    }

    public String getPatLastName() {
        return patLastName;
    }

    public void setPatLastName(String patLastName) {
        this.patLastName = patLastName;
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

    @Override
    public String toString() {
        return "PatientGeneralData{" +
                "pid=" + pid +
                ", patFirstName='" + patFirstName + '\'' +
                ", patLastName='" + patLastName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", ssnType=" + ssnType +
                '}';
    }
}
