SELECT jour.OID, jour.JOURNALCOMMENT, jour.TSCREATED, jour.CREATEDBY
FROM centre AS c
         JOIN centrepatient AS cp ON c.ID = cp.CENTREID
         JOIN regionpatient AS rp ON cp.RPID = rp.RPID
         JOIN patient AS pat on rp.PID = pat.PID
         JOIN ordinationperiod AS op on op.CPID = cp.CPID
         JOIN journalcomment AS jour ON op.OID = jour.OID
WHERE c.ID = ?
AND pat.SSN = ?
;