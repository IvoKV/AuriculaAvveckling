# Mätvärde, ALL patients
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

    FROM centre AS c
             JOIN centrepatient AS cp ON c.id = cp.CENTREID
             JOIN regionpatient AS rp ON cp.RPID = rp.RPID
             JOIN ordinationperiod AS op ON  cp.CPID = op.CPID
             LEFT OUTER JOIN creatinin crea ON op.OID = crea.OID
             LEFT OUTER JOIN labresult lab ON crea.LABRESULTID = lab.id
             JOIN patient AS p ON rp.PID = p.PID
             JOIN people AS pal ON cp.PAL = pal.PEOPLEID
    WHERE c.ID = ?
    ORDER BY p.PID;