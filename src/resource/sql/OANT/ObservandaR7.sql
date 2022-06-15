SELECT c.ID AS CENTRE_ID,
       pat.PID AS PATIENT_PID, pat.SSN AS SSN, pat.SSN_TYPE AS SSN_TYPE,
       rp.FIRSTNAME AS FIRSTNAME, rp.LASTNAME AS LASTNAME,

       o.RPID AS ORDP_RPID, o.OBSERVANDA_COMMENT AS ORDP_OBSERVANDA_COMMENT,

       CASE COALESCE(o.SEVERITY_OBSERVANDA_1, 'NULLVALUE')
        WHEN 1 THEN 'Akoholproblem'
        WHEN 2 THEN 'Tidigare allvarlig blödning'
        WHEN 3 THEN 'Cytostaticabehandling'
        WHEN 4 THEN 'Falltendens'
        WHEN 5 THEN 'Interagerande läkemedel'
        WHEN 6 THEN 'Leversjukdom'
        WHEN 7 THEN 'Trombocythämmare'
        WHEN 8 THEN 'Trombocytopeni'
        WHEN 9 THEN 'OBS!'
        WHEN 'NULLVALUE' THEN 'värdet är null'
        ELSE 'okänt nyckelvärde'
        END AS ORDP_SEVERITY_OBSERVANDA,

    /* CREATEDBY, etc. */
    o.CREATEDBY AS CREATEDBY,
    o.UPDATEDBY AS UPDATEDBY,
    o.TSCREATED AS TSCREATED

FROM centre AS c
         JOIN centrepatient AS cp ON c.id = cp.CENTREID
         JOIN regionpatient AS rp ON cp.RPID = rp.RPID
         JOIN ordpatient o on rp.RPID = o.RPID
         JOIN patient AS pat ON rp.PID = pat.PID
WHERE c.ID = ?
  AND pat.SSN = ?
;

