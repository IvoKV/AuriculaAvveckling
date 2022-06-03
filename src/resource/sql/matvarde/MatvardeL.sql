SELECT c.ID AS CENTREID,
		pat.SSN, pat.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,

        /***  HEMORRHAGES ***/
        hmr.H1 AS HMR_LEVERNJURSJUKDOM, hmr.E1 AS HMR_ETANOLMISSBRUK , hmr.M AS HMR_MALIGNINITET,
        hmr.R1 AS HMR_REDUCERAT_TROMBOCYTANTAL_FUNK, hmr.R2 AS HMR_TIDIGARE_BLÖDNING, hmr.H2 AS HMR_HYPERTONI,
        hmr.A AS HMR_ANEMI, hmr.G AS HMR_GENETISKAFAKTORER, hmr.E2 AS HMR_STOR_RISK_FÖR_FALL, hmr.S AS HMR_STROKE,
        hmr.CREATEDBY AS HMR_CREATEDBY, hmr.UPDATEDBY AS HMR_UPDATEDBY, hmr.TSCREATED AS HMR_TSCREATED,

		/*** CHADS2  ***/
        cha2.C AS CHA2_HJÄRTSVIKT, cha2.H AS CHA2_HYPERTONI, cha2.D AS CHA2_DIABETES, cha2.S AS CHA2_STROKE,
        cha2.CREATEDBY AS CHA2_CREATEDBY, cha2.UPDATEDBY AS CHA2_UPDATEDBY, cha2.TSCREATED AS CHA2_TSCREATED

FROM centre AS c
         JOIN centrepatient AS cp ON c.ID = cp.CENTREID
         JOIN regionpatient AS rp ON cp.RPID = rp.RPID
         JOIN patient as pat on rp.PID = pat.PID
         LEFT JOIN hemorrhages AS hmr ON rp.RPID = hmr.RPID
         LEFT JOIN chads2 as cha2 ON rp.RPID = cha2.RPID
WHERE c.ID = ?
AND pat.SSN = ?
ORDER BY hmr.CREATEDBY
         ;