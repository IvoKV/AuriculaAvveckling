# Hemorrhages, ALL patients
SELECT  c.id, pat.pid, pat.SSN, pat.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,
        /* Hemorrhages (blödningar) */
        CASE herr.h1
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END  AS 'Lever_eller_njursjukdom',

        CASE herr.e1
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END AS 'Etanolmissburk',

        CASE herr.m
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END AS 'Malignitet',

        CASE herr.r1
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END AS 'Reducerat trombocytantal/funktion',

        CASE herr.r2
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END AS 'Tidigare blödning',

        CASE herr.h2
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END AS 'Hypertoni',

        CASE herr.a
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END AS 'Anemi',

        CASE herr.g
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja'
            WHEN 0 THEN 'Vet ej'
            ELSE ''
            END AS 'Genetiska faktorer (CYP2C9, VKORC1)',

       CASE herr.e2
           WHEN 1 THEN 'Nej'
           WHEN 2 THEN 'Ja'
           WHEN 0 THEN 'Vet ej'
           ELSE ''
           END AS 'Stor risk för fall',

       CASE herr.s
           WHEN 1 THEN 'Nej'
           WHEN 2 THEN 'Ja'
           WHEN 0 THEN 'Vet ej'
           ELSE ''
           END AS Stroke

FROM centre as c
         join centrepatient as cp on c.id = cp.CENTREID
         join regionpatient as rp on cp.RPID = rp.RPID
         JOIN hemorrhages as herr on rp.rpid = herr.rpid
         join patient as pat on rp.PID = pat.PID
where c.ID = ?
order by pat.PID;