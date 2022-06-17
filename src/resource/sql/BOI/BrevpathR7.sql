SELECT
    cp.CPID,
    auto.TSSENT, auto.FILENAME, pat.SSN AS PATSSN, pat.PID,  regpat.FIRSTNAME, regpat.LASTNAME, regpat.ADDRESS, regpat.CITY,
    concat( convert (date_format(auto.TSSENT,'%Y/%m/%d') USING UTF8MB3 ),
            '/', cp.centreid, '/', auto.FILENAME  )  as PATH
FROM centrepatient as cp
         JOIN communication AS comm ON cp.CPID = comm.CPID
         JOIN autosentletters AS auto ON comm.COM_REL_ID = auto.COM_REL_ID AND comm.COM_REL_TYPE = auto.COM_REL_TYPE
         JOIN regionpatient AS regpat ON cp.RPID = regpat.RPID
         JOIN patient AS pat ON regpat.PID = pat.PID
WHERE pat.SSN = ?
;