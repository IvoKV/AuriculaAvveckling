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
            LMH.LMHTYPE,                                    # tynint
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