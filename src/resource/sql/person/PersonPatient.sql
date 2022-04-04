SELECT rp.pid, pat.SSN,  rp.firstname, rp.lastname
FROM centre AS c
    JOIN centrepatient AS cp ON c.ID = cp.CENTREID
    JOIN regionpatient AS rp ON cp.RPID = rp.RPID
    JOIN patient AS pat ON rp.PID = pat.PID
WHERE c.ID = ?
ORDER BY pat.SSN;