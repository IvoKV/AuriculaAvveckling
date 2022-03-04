# Hemorrhages, ALL patients
SELECT  c.id, p.pid, p.SSN, p.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,
    /* Patientansvarig läkare/enhet */
        pal.TITLE AS PAL_TITLE,
        pal.FIRSTNAME AS PAL_FIRSTNAME,
        pal.LASTNAME as PAL_LASTNAME,

        /* Hemorrhages (blödningar) */
        CASE herr.h1
            WHEN 1 THEN 'test'
            WHEN 2 THEN 'test'
            WHEN 3 THEN 'test'
            or ''
            END  AS 'Lever_eller_njursjukdom',

        CASE herr.e1
            WHEN 1 THEN 'test'
            WHEN 2 THEN 'test'
            WHEN 3 THEN 'test'
            OR ''
            END AS 'Etanolmissburk',

        CASE herr.m
            WHEN 1 THEN 'test'
            WHEN 2 THEN 'test'
            WHEN 3 THEN 'test'
            OR ''
            END AS 'Malignitet',

        CASE herr.r1
            WHEN 1 THEN 'test'
            WHEN 2 THEN 'test'
            WHEN 3 THEN 'test'
            OR ''
            END AS 'Reducerat trombocytantal/funktion',

        CASE herr.r2
            WHEN 1 THEN 'test'
            WHEN 2 THEN 'test'
            WHEN 3 THEN 'test'
            OR ''
            END AS 'Tidigare blödning',

        CASE herr.h2
           WHEN 1 THEN 'test'
           WHEN 2 THEN 'test'
           WHEN 3 THEN 'test'
           OR ''
           END AS 'Hypertoni',

        CASE herr.a
            WHEN 1 THEN 'test'
            WHEN 2 THEN 'test'
            WHEN 3 THEN 'test'
            OR ''
            END AS 'Anemi',

        CASE herr.g
            WHEN 1 THEN 'test'
            WHEN 2 THEN 'test'
            WHEN 3 THEN 'test'
            OR ''
            END AS 'Genetiska faktorer (CYP2C9, VKORC1)',

       CASE herr.e2
           WHEN 1 THEN 'test'
           WHEN 2 THEN 'test'
           WHEN 3 THEN 'test'
           OR ''
           END AS 'Stor risk för fall',

       CASE herr.s
           WHEN 1 THEN 'test'
           WHEN 2 THEN 'test'
           WHEN 3 THEN 'test'
           OR ''
           END AS Stroke

FROM centre as c
         join centrepatient as cp on c.id = cp.CENTREID
         join regionpatient as rp on cp.RPID = rp.RPID
         JOIN hemorrhages as herr on rp.rpid = herr.rpid
         join patient as p on rp.PID = p.PID
         join people AS pal on cp.PAL = pal.PEOPLEID
where C.ID = ?
order by p.PID;