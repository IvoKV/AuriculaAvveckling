select c.ID,
        op.OID,
    /*-- person id --*/
       rp.FIRSTNAME,
	   rp.LASTNAME,
	   p.SSN,
	   p.SSN_TYPE,

/*-- Patientansvarig läkare/enhet, adress, telefon*/
        pal.FIRSTNAME AS PAL_FIRSTNAME,
        pal.LASTNAME AS PAL_LASTNAME,
        pal.TITLE AS PAL_TITLE,

       /*-- op.MEDICINTYPE */
    CASE op.MEDICINETYPE
	   	WHEN 1 THEN 'Traditionella'
		WHEN 2 THEN 'Nya'
	END as MEDICINETYPE_TXT,

    /*-- op.ATRIAL_FIB */
    CASE op.ATRIAL_FIB
            WHEN 1 THEN 'FF primärprevention (I489)'
            WHEN 2 THEN 'FF + Stroke/TIA (I489+I639)'
            WHEN 3 THEN 'FF + Artäremboli (I489+I749)'
    END AS ATRIALFIB_TXT,

    /*-- op.VALVE_MALFUNCTION */
    CASE COALESCE (op.VALVE_MALFUNCTION, 'NULLVALUE')
            WHEN 1 THEN 'Mekanisk mitralisklaff (I059+Z952)'
            WHEN 2 THEN 'Mekanisk aortaklaff (I359+Z952)'
            WHEN 3 THEN 'Mekanisk mitralis + aortaklaff (I059+I359+Z952)'
            WHEN 4 THEN 'Biologisk klaffprotes (Z953)'
            WHEN 5 THEN 'Klaffplastik (Z954)'
            WHEN 6 THEN 'Mitralisstenos (I050)'
            WHEN 7 THEN 'Tricuspidalis klaff (I079+Z952)'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
    END AS VALVEMALFUNCTION_TXT,

    /*-- op.VENOUS_TROMB */
    CASE COALESCE(op.VENOUS_TROMB, 'NULLVALUE')
            WHEN 1 THEN 'Bentrombos (I803)'
            WHEN 2 THEN 'Bentrombos recidiv (I803)'
            WHEN 3 THEN 'Lungemboli (I269)'
            WHEN 4 THEN 'Lungemboli recidiv (I269)'
            WHEN 5 THEN 'Bentrombos + Lungemboli (I803+I269)'
            WHEN 7 THEN 'Portavenstrombos (I819)'
            WHEN 8 THEN 'Mesenterialvenstrombos (K550)'
            WHEN 9 THEN 'Sinustrombos (I676)'
            WHEN 10 THEN 'Tromboflebit (I809)'
            WHEN 11 THEN 'Trombos v. jugularis (I828)'
            WHEN 12 THEN 'Subclavia trombos (I828)'
            WHEN 13 THEN 'Vena Cava trombos (I822)'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
    END AS VENOUSTROMB_TXT,

    /*-- op.OTHER*/
    CASE COALESCE(op.OTHER, 'NULLVALUE')
            WHEN 1 THEN 'Vänsterkammardysfunktion (Q234)'
            WHEN 2 THEN 'Vänsterkammartromb (I513)'
            WHEN 3 THEN 'Kardiomyopati (I429)'
            WHEN 4 THEN 'Hjärtinfarkt (I219)'
            WHEN 5 THEN 'Pulmonell hypertension (P239B)'
            WHEN 6 THEN 'Nefrotiskt syndrom (N049)'
            WHEN 7 THEN 'Koagulationsrubbning (D686)'
            WHEN 9 THEN 'Stroke (I639)'
            WHEN 10 THEN 'TIA (I639)'
            WHEN 11 THEN 'Artäremboli (I749)'
            WHEN 12 THEN 'Kärlrekonstruktion (Z959)'
            WHEN 13 THEN 'Carotisstenos (I669)'
            WHEN 14 THEN 'Artärtrombos (I749)'
            WHEN 15 THEN 'Kardiell emboliprofylax (T455)'
            WHEN 101 THEN 'Övrig Barnspecifik indikation'
            WHEN 'NULLVALUE' THEN 'is null'
            ELSE 'okänt nyckelvärde'
    END AS OTHER_TXT,

    /*-- op.OTHERCHILDINDICATION */
    CASE COALESCE(op.OTHERCHILDINDICATION, 'NULLVALUE')
        WHEN 1 THEN 'Medfödd hjärtanomali'
        WHEN 2 THEN 'Fallots tetrad'
        WHEN 'NULLVALUE' THEN 'is null'
        ELSE 'okänt nyckelvärde'
    END AS OTHERCHILDINDICATION_TXT,

    /*-- op.DCCONVERSION */
    CASE COALESCE (op.DCCONVERSION, 'NULLVALUE')
        WHEN 1 THEN 'Nej'
        WHEN 2 THEN 'Ja'
        WHEN 'NULLVALUE' THEN 'is null'
        ELSE 'okänt nyckelvärde'
    END AS DCCONVERSION_TXT,

    op.DCTHERAPYDROPOUT,

        /*-- op.PERIOD_LENGTH */
   CASE COALESCE (op.PERIOD_LENGTH, 'NULLVALUE')
       WHEN 0 THEN 'En månad'
       WHEN 1 THEN 'Två månader'
       WHEN 2 THEN 'Tre månader'
       WHEN 3 THEN 'Sex månader'
       WHEN 4 THEN 'Tolv månader'
       WHEN 5 THEN 'Tillsvidare'
       WHEN 6 THEN 'Annan behandlingslängd'
       WHEN 'NULLVALUE' THEN 'is null'
       ELSE 'okänt nyckelvärde'
   END AS PERIODLENGTH_TXT,

        /*-- Läkemedel Namn på handelsvara*/
   CASE COALESCE (op.MEDICIN, 'NULLVALUE')
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
       WHEN 'NULLVALUE' THEN 'is null'
       ELSE 'okänt nyckelvärde'
    END as MEDICIN_TXT,

	CASE COALESCE (op.DOSE_MODE, 'NULLVALUE')
	    WHEN 1 THEN 'Alltid hela tabletter'
	    WHEN 2 THEN 'Alltid halva tabletter'
	    WHEN 3 THEN 'Brytpunkt vid 14 tabletter/v'
	    WHEN 4 THEN 'Brytpunkt vid 7 tabletter/v'
	    WHEN 5 THEN 'Kan doseras i kvartstabletter'
	    WHEN 'NULLVALUE' THEN 'is null'
	    ELSE 'okänt nyckelvärde'
	END AS DOSEMODE_TXT,

    /*-- op.CREAINTERVALFIRSTYEAR */
    CASE COALESCE (op.CREAINTERVALFIRSTYEAR, 'NULLVALUE')
        WHEN 1 THEN '3, 6, 12:e månad (3 ggr)'
        WHEN 2 THEN 'Var 3:e månad (4 ggr)'
        WHEN 3 THEN 'Var 6:e månad (2 ggr)'
        WHEN 4 THEN 'Var 12:e månad (1 ggr)'
        WHEN 'NULLVALUE' THEN 'is null'
        ELSE 'okänt nyckelvärde'
    END AS CREAINTERVALFIRSTYEAR_TXT,

    /*-- op.CREAINTERVAL */
    CASE COALESCE (op.CREAINTERVAL, 'NULLVALUE')
        WHEN 3 THEN 'Tre månader'
        WHEN 6 THEN 'Sex månader'
        WHEN 12 THEN 'Tolv månader'
        WHEN 'NULLVALUE' THEN 'is null'
        ELSE 'okänt nyckelvärde'
    END AS CREAINTERVAL_TXT,

    op.STARTDATE,
    op.ENDDATE,
    op.CREACOMPLATESTCREATED,
    op.CREACOMPLFOLYEAR,

    CASE COALESCE (op.CREACOMPLFIRSTYEAR, 'NULLVALUE')
        WHEN 3 THEN 'Tre månader'
        WHEN 6 THEN 'Sex månader'
        WHEN 12 THEN 'Tolv månader'
        WHEN 'NULLVALUE' THEN 'is null'
        ELSE 'okänt nyckelvärde'
    END AS CREACOMPLFIRSTYEAR_TXT,

    /*-- op.REASON_STOPPED */
    CASE COALESCE (op.REASON_STOPPED, 'NULLVALUE')
        WHEN 1 THEN 'Planenligt'
        WHEN 2 THEN 'På grund av blödning'
        WHEN 3 THEN 'På grund av andra sjukdomar'
        WHEN 4 THEN 'På grund av dålig följsamhet'
        WHEN 5 THEN 'På patientens begäran'
        WHEN 6 THEN 'På grund av andra biverkningar'
        WHEN 7 THEN 'Patienten ska ha ny typ av medicin'
        WHEN 8 THEN 'På grund av alkoholproblem'
        WHEN 9 THEN 'Utgår på grund av flyttning'
        WHEN 10 THEN 'Patienten ska ha nytt målvärde'
        WHEN 11 THEN 'Ny behandlingsindikation gäller'
        WHEN 12 THEN 'Patienten har avlidit'
        WHEN 13 THEN 'Ny doseringsperiod pga. elkonvertering'
        WHEN 14 THEN 'På grund av falltendens'
        WHEN 15 THEN 'På grund av GI-biverkan'
        WHEN 16 THEN 'Byte till annan antikoagulantia'
        WHEN 99 THEN 'Ny period importerad'
        WHEN 'NULLVALUE' THEN 'is null'
        ELSE 'okänt nyckelvärde'
    END AS REASONSTOPPED_TXT,

    op.CONTINUELATECHECK,
    op.CREATEDBY,
    op.UPDATEDBY,
    op.LENGTHCOMMENT,

    /*-- Coaguckeck, datum för kalibrering*/
	CASE op.INRMETHOD
	   	WHEN 1 THEN 'Venöst'
		WHEN 2 THEN 'Kapillärt'
		WHEN 3 THEN 'Coaguchek'
	END as INRMETHOD_TXT,

	op.COMPLFOLYEAR

FROM centre AS c
         JOIN centrepatient AS cp ON c.ID = cp.CENTREID
         JOIN regionpatient AS rp ON cp.RPID = rp.RPID
         JOIN patient as p on rp.PID = p.PID
         JOIN ordinationperiod as op on op.CPID = cp.CPID
         JOIN people AS pal ON cp.PAL = pal.PEOPLEID
WHERE c.ID = ?
AND p.SSN = ?
ORDER BY op.OID
;