select c.ID,
       op.OID,              /* extra fält, anv. i Listoperations.java */
       /*-- person id --*/
       rp.FIRSTNAME,
	   rp.LASTNAME,
	   p.SSN,
	   p.SSN_TYPE,

/*-- Patientansvarig läkare/enhet, adress, telefon*/
       cp.PAL_TEXT AS CPPAL_TXT,
       pal.FIRSTNAME AS PAL_FIRSTNAME,
       pal.LASTNAME AS PAL_LASTNAME,
       pal.TITLE AS PAL_TITLE,

/*-- Nästa omprövning alternativt behandlingsslut - datum (?)*/
/*-- Nästa kontroll om X veckor, om X månader eller ett datum*/
	   w.DATE_NEXT_VISIT,
	   w.INR_INTERVAL_ID,
	   w.ORDINATIONDATE,
       w.MONDAY_DOSE,
       w.TUESDAY_DOSE,
       w.WEDNSDAY_DOSE,
       w.THURSDAY_DOSE,
       w.FRIDAY_DOSE,
       w.SATURDAY_DOSE,
       w.SUNDAY_DOSE,
	   w.COMMENT as COMMENT_DOSE,

/*-- Antal tabletter*/
/*-- Läkemedel t.ex. Waran eller Xarelto samt tablettstyrka*/
/*-- Läkemedel Namn på handelsvara*/
       CASE op.MEDICIN
           WHEN 1 THEN 'Waran 2,5 mg tabl'
           WHEN 2 THEN 'Vit Waran 2,5 mg tabl'
           WHEN 5 THEN 'Warfarin Orion 2,5 mg tabl'
           WHEN 3 THEN 'Marcumar 3 mg tabl'
           WHEN 4 THEN 'Sintrom 1 mg tabl'
           WHEN 13 THEN 'Pradaxa 75 mg kapsel 2 gånger dagligen'
           WHEN 11 THEN 'Pradaxa 110 mg kapsel 2 gånger dagligen'
           WHEN 12 THEN 'Pradaxa 150 mg kapsel 2 gånger dagligen'
           WHEN 15 THEN 'Xarelto 15 mg tabl 1 gång dagligen'
           WHEN 16 THEN 'Xarelto 20 mg tabl 1 gång dagligen'
           WHEN 17 THEN 'Xarelto 15 mg x 2 i 3 veckor därefter 20 mg x 1 tabl COMBINATION_1'
           WHEN 18 THEN 'Eliquis 2,5 mg tabl 2 gånger dagligen'
           WHEN 19 THEN 'Eliquis 5 mg tabl 2 gånger dagligen'
           WHEN 31 THEN 'Lixiana 30 mg tabl 1 gång dagligen'
           WHEN 32 THEN 'Lixiana 60 mg tabl 1 gång dagligen'
           WHEN 21 THEN 'Warankapsel 0,3 mg kapsel'
           WHEN 22 THEN 'Warankapsel 0,9 mg kapsel'
        END as      MEDICIN_TXT,

	  op.DOSE_MODE,

/*-- Särkild dosjustering i klartext - meddelande till patient (?)*/
	  CASE wr.REDUCED_TYPE
	  	WHEN 1 THEN 'Operation'
		WHEN 2 THEN 'Tandläkare'
		WHEN 3 THEN 'Undersökning/behandling'
		WHEN 4 THEN 'Saknar dosering'
		WHEN 5 THEN 'Larmvärde'
		WHEN 6 THEN 'Extra LMH'
		WHEN 7 THEN 'Övrigt'
	  END AS REDUCED_TYPE_TXT,

		wr.MONDAY_DOSE as MONDAY_DOSE_REDUCED,
		wr.TUESDAY_DOSE as TUESDAY_DOSE_REDUCED,
		wr.WEDNSDAY_DOSE as WEDNSDAY_DOSE_REDUCED,
		wr.THURSDAY_DOSE as THURSDAY_DOSE_REDUCED,
		wr.FRIDAY_DOSE as FRIDAY_DOSE_REDUCED,
        wr.SATURDAY_DOSE AS SATURDAY_DOSE_REDUCED,
        wr.SUNDAY_DOSE AS SUNDAY_DOSE_REDUCED,
		wr.REDUCED_COMMENT,
	   inr1.INRVALUE,
	   inr1.INRDATE,
	   inr1.LABORATORY_ID,
	   inr1.ANALYSIS_PATHOL,

	   CASE op.MEDICINETYPE
	   	WHEN 1 THEN 'Traditionella'
		WHEN 2 THEN 'Nya'
	   END as MEDICINETYPE_TXT,
/*-- Coaguckeck, datum för kalibrering*/
	   CASE op.INRMETHOD
	   	WHEN 1 THEN 'Venöst'
		WHEN 2 THEN 'Kapillärt'
		WHEN 3 THEN 'Coaguchek'
	   END as INRMETHOD_TXT,

#	   op.OID,

/*-- Kreatinin (ELLER pk(INR))*/
	   crea.LABRESULTID as LABRESULTID_CREATININ,
	   crea.PLANEDDATE as PLANEDDATE_CREATININ,
	   crea.TESTDATE as TESTDATE_CREATININ,
	   crea.CREATININ,
	   crea.EGFR,
	   crea.FOLLOWUPDATE as FOLLOWUPDATE_CREATININ,
	   crea.COMMENT as COMMENT_CREATININ,
/*Labresult*/
	   lab.analysisCode as ANALYSISCODE_LAB,
	   lab.sampleValue as SAMPLEVALUE_LAB,
       lab.sampleValueText AS SAMPLEVALUE_TEXT,
	   lab.analysisComment as ANALYSISCOMMENT_LAB,

/*Change*/
	   change1.SYSTEMS_ORDINATION_SUGG,
	   change1.SYSTEMS_INTERVAL_SUGG,
	   change1.USER_ORDINATION,
	   change1.USER_INTERVAL

FROM centre AS c
         JOIN centrepatient AS cp ON c.ID = cp.CENTREID
         JOIN regionpatient AS rp ON cp.RPID = rp.RPID
         LEFT JOIN people AS pal ON cp.PAL = pal.PEOPLEID
         JOIN patient as p on rp.PID = p.PID
         JOIN ordinationperiod as op on op.CPID = cp.CPID

 		LEFT JOIN inr AS inr1 ON inr1.OID = op.OID
		LEFT JOIN waranordination AS w ON w.INRID = inr1.INRID
    	LEFT JOIN creatinin AS crea ON  crea.OID = op.OID
		LEFT JOIN labresult AS lab ON lab.ID = crea.LABRESULTID
		LEFT JOIN waranordinationreduced AS wr ON wr.INRID = inr1.INRID
		LEFT JOIN changed_inr_interval AS change1 ON change1.INRID = inr1.INRID

WHERE c.ID = ?
AND p.SSN = ?
ORDER BY op.oid, w.ORDINATIONDATE
;