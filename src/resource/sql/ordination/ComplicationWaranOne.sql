# Complication, ALL patients
    SELECT  c.ID, p.pid, p.SSN, p.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,
        /* Patientansvarig läkare/enhet */
            pal.TITLE AS PAL_TITLE,
            pal.FIRSTNAME AS PAL_FIRSTNAME,
            pal.LASTNAME as PAL_LASTNAME,

        /* Complication */
            CASE comp.COMPLEXISTS
                WHEN 0 THEN 'Nej'
                WHEN 1 THEN 'Ja'
                WHEN -1 THEN 'Vet ej'
                ELSE ''
                END AS COMPLICATION_EXISTS,

            CASE comp.BLEEDING
                WHEN 1 THEN 'CNS'
                WHEN 2 THEN 'GI'
                WHEN 3 THEN 'Övrig sjukhuskrävande'
                ELSE ''
                END as BLEEDING,

            CASE comp.TROMBOSIS
                WHEN 0 THEN 'Nej'
                WHEN 1 THEN 'Arteriell'
                WHEN 2 THEN 'Venös'
                ELSE ''
                END AS TROMBOSIS,

            comp.DAYSOFCARE,
            comp.PKVALUE,
            comp.STATUS

    FROM centre as c
             JOIN centrepatient as cp on c.ID = cp.CENTREID
             JOIN regionpatient as rp on cp.RPID = rp.RPID
             JOIN ordinationperiod as op on  cp.CPID = op.CPID
             JOIN complication comp on op.OID = comp.OID
             JOIN patient as p on rp.PID = p.PID
             JOIN people AS pal on cp.PAL = pal.PEOPLEID
    WHERE c.ID = ?
    AND p.pid = ?
    ORDER BY p.PID;