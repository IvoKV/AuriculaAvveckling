# Mätvärde, ONE patient
    SELECT
           /* centre */
           c.id,
           /* patient */
           p.pid, p.SSN, p.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,

        /* Patientansvarig läkare/enhet */
            pal.TITLE AS PAL_TITLE,
            pal.FIRSTNAME AS PAL_FIRSTNAME,
            pal.LASTNAME as PAL_LASTNAME,

        /* INR */
            INR.INRVALUE,                                   # decimal
            INR.INRDATE,                                    # DATE
            INR.LABREM_COMMENT,                             # varchar
            INR.SPECIMEN_COMMENT,                           # varchar
            INR.ANALYSIS_COMMENT                            # varchar

    FROM centre AS c
             join centrepatient as cp on c.id = cp.CENTREID
             join regionpatient as rp on cp.RPID = rp.RPID
             join ordinationperiod as op on  cp.CPID = op.CPID
             JOIN inr AS INR ON op.OID = INR.OID
             join patient as p on rp.PID = p.PID
             join people AS pal on cp.PAL = pal.PEOPLEID
    WHERE c.ID = ?
    AND p.SSN = ?
    order by p.PID;