package Mott;

import auxilliary.TextWrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Journalcomment {
    private int oid;
    private String journalcomment;
    private String createdBy;
    private Timestamp tsCreated;

    private List<String> wrappedJournalComment;

    public Journalcomment(int oid, String journalcomment, String createdBy, Timestamp tsCreated) throws JournalcommentException {
        this.oid = oid;
        this.journalcomment = journalcomment;
        this.createdBy = createdBy;
        this.tsCreated = tsCreated;
        this.wrappedJournalComment = new ArrayList<>();

        try{
            Objects.requireNonNullElse(oid, "oid is null");
            Objects.requireNonNullElse(journalcomment, "journalcomment is null");
            Objects.requireNonNullElse(createdBy, "createdBy is null");
        }
        catch(Exception exp){
            throw new JournalcommentException(exp.getMessage());
        }
        wrapJournalcomment();
    }

    private void wrapJournalcomment(){
        var tw = new TextWrapper(journalcomment, 65, true);
        wrappedJournalComment = tw.getPolishedStringArray();
    }

    public List<String> getWrappedJournalComment() {
        return wrappedJournalComment;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getJournalcomment() {
        return journalcomment;
    }

    public void setJournalcomment(String journalcomment) {
        this.journalcomment = journalcomment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getTsCreated() {
        return tsCreated;
    }

    public void setTsCreated(Timestamp tsCreated) {
        this.tsCreated = tsCreated;
    }

    @Override
    public String toString() {
        return "Journalcomment{" +
                "oid=" + oid +
                ", journalcomment='" + journalcomment + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", tsCreated=" + tsCreated +
                '}';
    }
}
