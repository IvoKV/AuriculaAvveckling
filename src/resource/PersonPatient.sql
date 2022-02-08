SELECT rp.pid, rp.lastname, rp.firstname
FROM centre AS c JOIN centrepatient AS cp ON c.ID = cp.CENTREID JOIN regionpatient AS rp ON cp.RPID = rp.RPID
WHERE c.ID = '11012AK';