package org.example;

/** GÖR INTE NÅGON IMPORTOPTIMERING!! **/
import Person.*;
import auxilliary.MyConnection;
import com.jcraft.jsch.JSchException;
import ordination.KontrollerProvtagningDoseringar.KontrollerProvtagningDoseringarBuilder;
import ordination.KontrollerProvtagningDoseringar.KontrollerProvtagningDoseringarException;
import ordination.Matvarde.MatvardeInitializationException;
import ordination.Waran.*;

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

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException, OrdinationsperiodInitializeException, MatvardeInitializationException, JSchException, KontrollerProvtagningDoseringarException {

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
        String regpatSSN = "19420807-0815";
        //String regpatSSN = "19470707-1157";
        //String regpatSSN = "19121212-1212";
        //regpatSSN = "";

        var kontrollerProvtagningDoseringarBuilder = new KontrollerProvtagningDoseringarBuilder(dbConnection);
        kontrollerProvtagningDoseringarBuilder.buildKontrollerProvtagningDoseringar(centreID, regpatSSN, false);

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
