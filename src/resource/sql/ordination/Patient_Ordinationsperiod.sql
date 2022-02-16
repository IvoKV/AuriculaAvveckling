# Listar hur många ordinationsperioder en patient har haft
SELECT p.pid, count(p.pid)
FROM centre as c
         join centrepatient as cp on c.id = cp.CENTREID
         join regionpatient as rp on cp.RPID = rp.RPID
         join ordpatient as opat on opat.RPID = rp.RPID
         join patient as p on rp.PID = p.PID
         join ordinationperiod as op on op.CPID = cp.CPID
group by p.PID
order by p.pid;