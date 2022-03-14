package ordination.Matvarde;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class LMH {

    private String cid;                                 // c.id
    private int pid;                                    // p.pid
    private String SSN;
    private Short SSN_TYPE;
    private String patFirstname;
    private String patLastname;
    private Short palTitle;
    private String palFirstname;
    private String palLastname;

    /* LMH */
    private double inrValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date inrDate;
    private String labremComment;
    private String specimenComment;
    private String inrAnalysisCommennt;

}
