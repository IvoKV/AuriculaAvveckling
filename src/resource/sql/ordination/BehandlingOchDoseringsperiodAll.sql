# BehandlingOchDoseringsperiod, ALL patients
    SELECT  c.id, p.pid, p.SSN, p.SSN_TYPE, rp.FIRSTNAME, rp.LASTNAME,
        /* Patientansvarig läkare/enhet */
            pal.TITLE AS PAL_TITLE,
            pal.FIRSTNAME AS PAL_FIRSTNAME,
            pal.LASTNAME as PAL_LASTNAME,

        /* Behandling */
           CASE op.MEDICINETYPE                                     /* 9 */
               WHEN 1 THEN 'Waran 2,5 mg'
               WHEN 2 THEN 'Vit Waran 2,5 mg'
               WHEN 5 THEN 'Warfarin Orion 2.5 mg'
               WHEN 3 THEN 'Marcumar 3 mg'
               WHEN 4 THEN 'Sintrom 1 mg'
               WHEN 13 THEN 'Pradaxa 75 mg'
               WHEN 11 THEN 'Pradaxa 110 mg'
               WHEN 12 THEN 'Pradaxa 150 mg'
               WHEN 15 THEN 'Xarelto 15 mg'
               WHEN 16 THEN 'Xarelto 20 mg'
               WHEN 16 THEN 'Xarelto 20 mg'
               WHEN 17 THEN 'Xarelto 15 mg x 2 i 3 veckor därefter 20 mg x 1'
               WHEN 18 THEN 'Eliquis 2.5 mg'
               WHEN 19 THEN 'Eliquis 5 mg'
               WHEN 31 THEN 'Lixiana 30 mg'
               WHEN 32 THEN 'Lixiana 60 mg'
               WHEN 21 THEN 'Warankapsel 0,3 mg'
               WHEN 22 THEN 'Warankapsel 0,9 mg'
               ELSE ''
               END AS MEDICIN,

            CASE op.DOSE_MODE                                           /* 10 */
                WHEN 1 THEN 'Alltid hela tabletter'
                WHEN 2 THEN 'Alltid halva tabletter'
                WHEN 5 THEN 'Kan doseras i kvartstabletter'
                WHEN 4 THEN 'Brytpunkt vid 7 tabletter/v'
                WHEN 3 THEN 'Brytpunkt vid 14 tabletter/v'
                ELSE ''
                END AS DOSE_MODE,

            op.PREFERED_INTERVAL_START,                                 /* 11 */
            op.PREFERED_INTERVAL_END,                                   /* 12 */
            op.INRMETHOD,                                               /* 13 */
            op.WEIGHT,                                                  /* 14 */
            op.WEIGHTDATE,                                              /* 15 */

            lmh.DOSE,                                                   /* 16*/
            lmh.FROMDATE,                                               /* 17 */
            lmh.TODATE,                                                 /* 18 */

        /* Doseringsperiod */
            op.STARTDATE,                                               /* 19 */
            op.PERIOD_LENGTH,                                           /* 20 */
            op.LENGTHCOMMENT,                                            /* 21 */

        /* Avslut */
            op.ENDDATE,                                                 /* 22 */

            CASE op.REASON_STOPPED
                WHEN 11 THEN 'Ny behandlingsindikation gäller'
                WHEN 13 THEN 'Ny behandlingsindikation gäller'
                WHEN 99 THEN 'Ny period importerad'
                WHEN 12 THEN 'Patient har avlidit'
                WHEN 7 THEN 'Patient ska ha ny typ av medicin'
                WHEN 16 THEN 'Byte till annan antikoagulantia'
                WHEN 10 THEN 'Patient ska ha nytt målvärde'
                WHEN 1 THEN 'Planenligt'
                WHEN 8 THEN 'På grund av alkoholproblem'
                WHEN 6 THEN 'På grund av andra biverkningar'
                WHEN 3 THEN 'På grund av andra sjukdomar'
                WHEN 2 THEN 'På grund av blödning'
                WHEN 4 THEN 'På grund av dålig följsamhet'
                WHEN 14 THEN 'På grund av falltendens'
                WHEN 15 THEN 'På grund av GI-biverkan'
                WHEN 5 THEN 'På patientens bebäran'
                WHEN 9 THEN 'Utgår på grund av flyttning'
                ELSE ''
            END AS REASON_STOPPED                                     /* 23 */

    FROM centre as c
             join centrepatient as cp on c.id = cp.CENTREID
             join regionpatient as rp on cp.RPID = rp.RPID
             join ordinationperiod as op on  cp.CPID = op.CPID
             JOIN inr on op.oid = inr.OID
             JOIN lmh on inr.INRID  = lmh.INRID
             join patient as p on rp.PID = p.PID
             join people AS pal on cp.PAL = pal.PEOPLEID
    where c.ID = ?
    order by p.PID;