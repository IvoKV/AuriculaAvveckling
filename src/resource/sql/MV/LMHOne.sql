# LMH, ONE patients
    SELECT
           /* centre */
           c.id,
           /* patient */
           p.pid, p.SSN, p.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,

        /* Patientansvarig läkare/enhet */
            pal.TITLE AS PAL_TITLE,
            pal.FIRSTNAME AS PAL_FIRSTNAME,
            pal.LASTNAME as PAL_LASTNAME,

        /* LMH */
            CASE LMH.LMHTYPE
                WHEN 1 THEN 'Fragmin 5000E'
                WHEN 2 THEN 'Fragmin (dos anges vid dosering)'
                WHEN 3 THEN 'Klexane 40mg'
                WHEN 4 THEN 'Klexane (dos anges vid dosering)'
                WHEN 5 THEN 'Innohep 4500E'
                WHEN 6 THEN 'Innohep (dos anges vid dosering)'
                WHEN 7 THEN 'Anges i brevmeddelandet'
                ELSE ''
            END AS LMH_TYPE ,                                    # tynint

            LMH.DOSE,                                       # int
            LMH.FROMDATE,                                   # date
            LMH.TODATE                                     # date

    FROM centre as c
             JOIN centrepatient AS cp ON c.id = cp.CENTREID
             JOIN regionpatient AS rp ON cp.RPID = rp.RPID
             JOIN ordinationperiod AS op ON  cp.CPID = op.CPID
             JOIN inr AS INR ON op.OID = INR.OID
             JOIN lmh AS LMH ON INR.INRID = LMH.INRID
             JOIN patient AS p ON rp.PID = p.PID
             JOIN people AS pal ON cp.PAL = pal.PEOPLEID
    WHERE c.ID = ?
    AND p.PID = ?
    ORDER BY p.PID;