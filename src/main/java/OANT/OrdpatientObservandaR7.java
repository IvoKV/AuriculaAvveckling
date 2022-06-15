package OANT;

import Person.PatientGeneralData;
import Person.PatientGeneralDataException;

import java.sql.Time;
import java.sql.Timestamp;

public class OrdpatientObservandaR7 extends PatientGeneralData {
    private int ordpatRPID;
    private String observandaComment;
    private String serverityObservanda1;

    /* Created by, etc. */
    private String createdBy;
    private String updatedBy;
    private Timestamp tsCreated;

    public OrdpatientObservandaR7(int pid,
                                  String patFirstName,
                                  String patLastName,
                                  String ssn,
                                  Short ssnType,

                                  int ordRPID,
                                  String observandaComment,
                                  String serverityObservanda1,
                                  String createdBy,
                                  String updatedBy,
                                  Timestamp tsCreated
                                  ) throws PatientGeneralDataException {
        super(pid, patFirstName, patLastName, ssn, ssnType);
        this.ordpatRPID = ordRPID;
        this.observandaComment = observandaComment;
        this.serverityObservanda1 = serverityObservanda1;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.tsCreated = tsCreated;
    }

    public int getOrdpatRPID() {
        return ordpatRPID;
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

    public String getObservandaComment() {
        return observandaComment;
    }

    public String getServerityObservanda1() {
        return serverityObservanda1;
    }

    @Override
    public String toString() {
        return "OrdpatientObservandaR7{" +
                "ordpatRPID=" + ordpatRPID +
                ", observandaComment='" + observandaComment + '\'' +
                ", serverityObservanda1='" + serverityObservanda1 + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", tsCreated=" + tsCreated +
                '}';
    }
}
