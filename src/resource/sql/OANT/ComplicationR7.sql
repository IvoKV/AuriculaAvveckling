SELECT c.ID AS CENTRE_ID,
       pat.PID AS PATIENT_PID, pat.SSN AS SSN, pat.SSN_TYPE AS SSN_TYPE,
       rp.FIRSTNAME, rp.LASTNAME,
       op.OID AS ORDINATIONPERIOD_ID,
       /* COMPLICATION */
       CASE COALESCE (comp.COMPLEXISTS, 'NULLVALUE')
           WHEN '0' THEN 'Nej'
           WHEN '1' THEN 'Ja'
           WHEN '-1' THEN 'Vet ej'
           WHEN 'NULLVALUE' THEN 'is null'
           ELSE 'Okänt nyckelvärde'
        END AS COMPLEXISTS,

       CASE COALESCE (comp.BLEEDING, 'NULLVALUE')
           WHEN '0' THEN 'Nej'
           WHEN '1' THEN 'CNS'
           WHEN '2' THEN 'GI'
           WHEN '3' THEN 'Övrig sjukhuskrävande'
           WHEN 'NULLVALUE' THEN 'is null'
           ELSE 'Okänt nyckelvärde'
           END AS BLEEDING,

       CASE COALESCE (comp.TROMBOSIS, 'NULLVALUE')
           WHEN '0' THEN 'Nej'
           WHEN '1' THEN 'Arteriell'
           WHEN '2' THEN 'Venös'
           WHEN 'NULLVALUE' THEN 'is null'
           ELSE 'Okänt nyckelvärde'
           END AS TROMBOSIS,

       comp.DAYSOFCARE,
       comp.PKVALUE,
       comp.STATUS,

       /* CREATED BY, ANSVARIG */
        comp.CREATEDBY,
       comp.UPDATEDBY,
       comp.TSCREATED

FROM centre as c
             JOIN centrepatient as cp on c.id = cp.CENTREID
             JOIN regionpatient as rp on cp.RPID = rp.RPID
             JOIN ordinationperiod as op on  cp.CPID = op.CPID
             JOIN complication AS comp ON op.OID = comp.OID
             JOIN patient as pat on rp.PID = pat.PID
WHERE c.ID = ?
AND pat.SSN = ?
ORDER BY comp.TSCREATED
;

