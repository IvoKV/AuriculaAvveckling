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
             join centrepatient as cp on c.id = cp.CENTREID
             join regionpatient as rp on cp.RPID = rp.RPID
             join ordinationperiod as op on  cp.CPID = op.CPID
             JOIN INR AS INR ON op.OID = INR.OID
             JOIN LMH AS LMH ON INR.INRID = LMH.INRID
             join patient as p on rp.PID = p.PID
             join people AS pal on cp.PAL = pal.PEOPLEID
    where C.ID = ?
    AND p.PID = ?
    order by p.PID;