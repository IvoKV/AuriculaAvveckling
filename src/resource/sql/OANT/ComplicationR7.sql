SELECT c.ID AS CENTRE_ID,
       pat.PID AS PATIENT_PID, pat.SSN AS SSN, pat.SSN_TYPE AS SSN_TYPE,
       rp.FIRSTNAME, rp.LASTNAME,
       op.OID AS ORDINATIONPERIOD_ID,
       comp.*

FROM centre as c
             JOIN centrepatient as cp on c.id = cp.CENTREID
             JOIN regionpatient as rp on cp.RPID = rp.RPID
             JOIN ordinationperiod as op on  cp.CPID = op.CPID
             JOIN complication AS comp ON op.OID = comp.OID
             JOIN patient as pat on rp.PID = pat.PID
WHERE c.ID = ?
AND pat.SSN = ?
;

