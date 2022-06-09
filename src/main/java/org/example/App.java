package org.example;

/** GÖR INTE NÅGON IMPORTOPTIMERING!! **/
import MV.MatvardeLBuilderException;
import Mott.JournalcommentException;
import OrdinationMOTT.OrdinationperiodBuilderR7;
import OrdinationperiodLKM.KontrollerProvtagningDoseringarBuilder;
import OrdinationperiodLKM.Waran.OrdinationsperiodInitializeException;
import Person.*;
import auxilliary.GeneralBefattningReadJSONException;
import auxilliary.MyConnection;
import com.jcraft.jsch.JSchException;
import OrdinationperiodLKM.KontrollerProvtagningDoseringarException;
import OrdinationMOTT.OrdinationperiodException;
import OrdinationperiodLKM.Matvarde.MatvardeInitializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Auricula Personobjekt
 *
 */
public class App 
{
    private static final String simpleConnectionFilePath = "src/resource/ConnectionString.txt";     // Används endast för öppen kanal till db source/-host
    private static final String databaseUse = "auricula_export_TIO_100";
    private static MyConnection myConnection = null;
    private static Connection dbConnection = null;

    private static final String datasourceHost = "cluster";

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException, OrdinationsperiodInitializeException, MatvardeInitializationException, JSchException, KontrollerProvtagningDoseringarException, OrdinationperiodException, GeneralBefattningException, PatientGeneralDataException, GeneralBefattningReadJSONException, JournalcommentException, MatvardeLBuilderException {

        if(datasourceHost == "cluster") {
            myConnection = new MyConnection(databaseUse);
            myConnection.createSshTunnel();
            dbConnection = myConnection.getSSHDbConnection();
        }
        else if (datasourceHost == "stationär"){
            Path path = Path.of(simpleConnectionFilePath);
            String connectionString = Files.readString(path);

            String host = connectionString.split(";")[0];
            String uName = connectionString.split(";")[1];
            String uPass = connectionString.split(";")[2];

            myConnection = new MyConnection(host, uName, uPass);
            dbConnection = myConnection.getConnection();
        }
        else {
            System.exit(0);
        }

        String centreID = "11012AK";
        //int regpatId = 54241;
        //int regpatId = 489980;
        //String regpatSSN = "19840729-0249";     // Linda Madeleine
        //String regpatSSN = "19420807-0815";
        //String regpatSSN = "19470707-1157";
        //String regpatSSN = "19121212-1212";
        //String regpatSSN = "19510403-5125";         // har waranordination comment 451 tecken (längst)
        //String regpatSSN = "19410603-9375";
        String regpatSSN = "19611010-1125";
        //regpatSSN = "";

//        var mvb = new MatvardeLBuilder(dbConnection);
//        mvb.buildMatvardeL(centreID, regpatSSN, false);

//        var generalBefattning = new GeneralBefattningBuilder(dbConnection);
//        generalBefattning.buildGeneralBefattning();

//        var kontrollerProvtagningDoseringarBuilder = new KontrollerProvtagningDoseringarBuilder(dbConnection);
//        kontrollerProvtagningDoseringarBuilder.buildKontrollerProvtagningDoseringar(centreID, regpatSSN, false);
////
//        var ordprov = new OrdinationperiodBuilder(dbConnection);
//        ordprov.buildOrdinationperiod(centreID, regpatSSN, false);

        var ordprovR7 = new OrdinationperiodBuilderR7(dbConnection);
        ordprovR7.buildOrdinationperiodR7(centreID, regpatSSN, false);


//        var personPat = new PersonPatientBuilder(dbConnection);
//        personPat.buildPersonPatient(centreID, true);         // boolean: write to file

//        var personChargeAllTitlesBuilder = new PersonInChargeAllTitlesBuilder(dbConnection);
//        personChargeAllTitlesBuilder.buildPersonInCharge(false);

//        var personInChargeAllEmployeesBuilder = new PersonInChargeAllEmployeesBuilder(dbConnection);
//        personInChargeAllEmployeesBuilder.buildPersonInChargeEmployees(true);

//        OrdinationsperiodIndikationerBuilder ordinationsPeriodBuilder = new OrdinationsperiodIndikationerBuilder(dbConnection);
//        ordinationsPeriodBuilder.buildOrdinationPeriodIndikation(centreID, regpatSSN, false);

//        HemorrhagesBuilder hemorrhagesBuilder = new HemorrhagesBuilder(dbConnection);
//        hemorrhagesBuilder.buildHemorrhages(centreID, regpatSSN, true);

//        BehandlingOchDoseringsperiodBuilder behandlingOchDoseringsperiodBuilder = new BehandlingOchDoseringsperiodBuilder(dbConnection);
//        behandlingOchDoseringsperiodBuilder.buildBehandlingOchDosering(centreID, regpatSSN, true);

//          LabInrBuilder labInrBuilder = new LabInrBuilder(dbConnection);
//          labInrBuilder.buildLabINR(centreID, regpatSSN, true);

//        MatvardeBuilder matvardeBuilder = new MatvardeBuilder(dbConnection);
//        matvardeBuilder.buildMatvarde(centreID, regpatSSN, true);

//          LMHBuilder lmhBuilder = new LMHBuilder(dbConnection);
//          lmhBuilder.buildLMH(centreID, regpatSSN, true);

            dbConnection.close();
            myConnection.disconnectSession();
    }
}
