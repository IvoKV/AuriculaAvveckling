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

        /* CREATININ */
            crea.CREATININ,                     # smallint
            crea.TESTDATE,                      # date
            lab.specimenComment,                # varchar
            lab.remissComment,                  # varchar
            lab.analysisComment                 # varchar

    FROM centre as c
             join centrepatient as cp on c.id = cp.CENTREID
             join regionpatient as rp on cp.RPID = rp.RPID
             join ordinationperiod as op on  cp.CPID = op.CPID
             LEFT OUTER JOIN creatinin crea on op.OID = crea.OID
             LEFT OUTER JOIN labresult lab on crea.LABRESULTID = lab.id
             join patient as p on rp.PID = p.PID
             join people AS pal on cp.PAL = pal.PEOPLEID
    WHERE c.ID = ?
    AND p.SSN = ?
    order by p.PID;