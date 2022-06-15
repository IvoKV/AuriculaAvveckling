SELECT  c.id AS CID, pat.pid AS PID, pat.SSN AS SSN, pat.SSN_TYPE AS SSN_TYPE, rp.FIRSTNAME AS FIRSTNAME, rp.LASTNAME AS LASTNAME,

        CASE COALESCE(herr.H1, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'LEVER_ELLER_NJURSJUKDOM',

        CASE COALESCE(herr.E1, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'ETANOLMISSBRUK',

        CASE COALESCE(herr.M, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'MALIGNITET',

        CASE COALESCE(herr.R1, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'REDUCERAT_TROMBOCYTANTALFUNKTION',

        CASE COALESCE(herr.R2, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'TIDIGARE_BLODNING',

        CASE COALESCE(herr.H2, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'HYPERTONI',

        CASE COALESCE(herr.A, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'ANEMI',

        /* GENETISKA FAKTORER (CYP2C9, VKORC1) */
        CASE COALESCE(herr.G, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'GENETISKA_FAKTORER',

        CASE COALESCE(herr.E2, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS 'STOR_RISK_FOR_FALL',

        CASE COALESCE(herr.S, 'NULLVALUE')
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
            END AS STROKE,

        herr.CREATEDBY,
        herr.UPDATEDBY,
        herr.TSCREATED

FROM centre AS c
         JOIN centrepatient AS cp ON c.id = cp.CENTREID
         JOIN regionpatient AS rp ON cp.RPID = rp.RPID
         JOIN patient AS pat ON rp.PID = pat.PID
         LEFT JOIN hemorrhages AS herr ON rp.RPID = herr.RPID
WHERE c.ID = ?
AND pat.SSN = ?;