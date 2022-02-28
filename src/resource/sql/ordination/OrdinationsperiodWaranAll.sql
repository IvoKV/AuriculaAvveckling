# Listar hur många ordinationsperioder en patient har haft
SELECT  c.id, p.pid, p.SSN, p.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,
       /* Patientansvarig läkare/enhet */
        pal.TITLE AS PAL_TITLE,
        pal.FIRSTNAME AS PAL_FIRSTNAME,
        pal.LASTNAME as PAL_LASTNAME,

    /*	-- Behandlingsstart (doseringsperiodens startdatum)*/
       op.STARTDATE,

       /* anteckning om patienten */
        opat.COMMENT AS COMMENT_ORDPATIENT,
     # -- Läkemedel t.ex Waran eller Xarelto samt tablettstyrka
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

    /*	-- Indikation - FF*/
        CASE op.ATRIAL_FIB
            WHEN 1 THEN 'FF primärprevention (I489)'
            WHEN 3 THEN 'FF + Artäremboli (I489+I749)'
            WHEN 2 THEN 'FF + Stroke/TIA (I489+I639)'
            ELSE ''
            END AS      ATRIAL_FIB_TXT,

    /*-- Indikation - Klaff*/
        CASE op.VALVE_MALFUNCTION
            WHEN 4 THEN 'Biologisk klaffprotes (Z953)'
            WHEN 5 THEN 'Klaffplastik (Z954)'
            WHEN 2 THEN 'Mekanisk aortaklaff (I359+Z952)'
            WHEN 1 THEN 'Mekanisk mitralisklaff (I059+Z952)'
            WHEN 3 THEN 'Mekanisk mitralis + aortaklaff (I059+I359+Z952)'
            WHEN 6 THEN 'Mitralisstenos (I050)'
            WHEN 7 THEN 'Tricuspidalis klaff (I079+Z952)'
            ELSE ''
            END AS      VALVE_MALFUNCTION_TXT,

    /*-- Indikation - Venös tromboembolism*/
        CASE op.VENOUS_TROMB
            WHEN 6 THEN 'Armtrombos (I808)'
            WHEN 1 THEN 'Bentrombos (I803)'
            WHEN 5 THEN 'Bentrombos + Lungemboli (I803+I269)'
            WHEN 2 THEN 'Bentrombos recidiv (I803)'
            WHEN 3 THEN 'Lungemboli (I269)'
            WHEN 4 THEN 'Lungemboli recidiv (I269)'
            WHEN 8 THEN 'Mesenterialvenstrombos (K550)'
            WHEN 7 THEN 'Portavenstrombos (I819)'
            WHEN 9 THEN 'Sinustrombos (I676)'
            WHEN 12 THEN 'Subclavia trombos (I828)'
            WHEN 10 THEN 'Tromboflebit (I809)'
            WHEN 11 THEN 'Trombos v. jugularis (I828)'
            WHEN 13 THEN 'Vena Cava trombos (I822)'
            ELSE ''
            END AS      VENOUS_TROMB_TXT,

    /*-- Indikation - Övrigt*/
        CASE op.OTHER
            WHEN 11 THEN 'Artäremboli (I749)'
            WHEN 14 THEN 'Artärtrombos (I749)'
            WHEN 13 THEN 'Carotisstenos (I669)'
            WHEN 4 THEN  'Hjärtinfarkt (I219)'
            WHEN 15 THEN 'Kardiell emboliprofylax (T455)'
            WHEN 3 THEN  'Kardiomyopati (I429)'
            WHEN 7 THEN  'Koagulationsrubbning (D686)'
            WHEN 12 THEN 'Kärlrekonstruktion (Z959)'
            WHEN 6 THEN  'Nefrotiskt syndrom (N049)'
            WHEN 5 THEN  'Pulmonell hypertension (P239B)'
            WHEN 9 THEN  'Stroke (I639)'
            WHEN 10 THEN 'TIA (I639)'
            WHEN 8 THEN  'Trombosprofylax (T455)'
            WHEN 1 THEN  'Vänsterkammardysfunktion (Q234)'
            WHEN 2 THEN  'Vänsterkammartromb (I513)'
            WHEN 101 THEN 'Övrig Barnspecifik indikation'
            ELSE ''
            END AS      INDICATION_OTHER_TXT,

    /*	-- Övrig Barnspecifik indikation*/
        CASE op.OTHERCHILDINDICATION
            WHEN 1 THEN 'Medfödd hjärtanomali'
            WHEN 2 THEN 'Fallots tetrad'
            ELSE ''
            END AS      OTHERCHILDINDICATION_TXT,

    /*-- Elkonvertering*/
        CASE op.DCCONVERSION
            WHEN 1 THEN 'Nej'
            WHEN 2 THEN 'Ja (DF026)'
            ELSE ''
            END AS      DCCONVERSION_TXT,

/*	-- Behandlingstid*/
        CASE op.PERIOD_LENGTH
            WHEN 0 THEN 'En månad'
            WHEN 1 THEN 'Två månader'
            WHEN 2 THEN 'Tre månader'
            WHEN 3 THEN 'Sex månader'
            WHEN 4 THEN 'Tolv månader'
            WHEN 5 THEN 'Tillsvidare'
            WHEN 6 THEN 'Annan behandlingslängd'
            ELSE ''
            END AS PERIOD_LENGTH_TXT

FROM centre as c
         join centrepatient as cp on c.id = cp.CENTREID
         join regionpatient as rp on cp.RPID = rp.RPID
         join ordpatient as opat on opat.RPID = rp.RPID
         join patient as p on rp.PID = p.PID
         join ordinationperiod as op on op.CPID = cp.CPID
        join people AS pal on cp.PAL = pal.PEOPLEID
WHERE c.ID = ?
ORDER BY rp.pid;