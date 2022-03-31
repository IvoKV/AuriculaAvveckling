package org.example;

/** GÖR INTE NÅGON IMPORTOPTIMERING!! **/
import Person.PersonInChargeAllEmployeesBuilder;
import Person.PersonInChargeAllTitlesBuilder;
import Person.PersonInChargeException;
import Person.PersonInitializationException;
import auxilliary.MyConnection;
import com.jcraft.jsch.JSchException;
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

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException, OrdinationsperiodInitializeException, MatvardeInitializationException, JSchException {

        if(datasourceHost == "cluster") {
            myConnection = new MyConnection(databaseUse);
            myConnection.createSshTunnel();
            dbConnection = myConnection.getDbConnection();
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
        //int regpatId = 0;   // <0>: all patients, <regpatid>: only chosen patient
        //int regpatId = 54241;
        //int regpatId = 489980;
        String regpatSSN = "19840729-0249";
        //regpatSSN = "";

        int regpatId = 0;

//        var personPat = new PersonPatientBuilder(connectionFilePath);
//        personPat.buildPersonPatient(true);         // boolean: write to file

//        var personChargeAllTitlesBuilder = new PersonInChargeAllTitlesBuilder(dbConnection);
//        personChargeAllTitlesBuilder.buildPersonInCharge(false);     // boolean: write to file; GroupBy || All

        var personInChargeAllEmployeesBuilder = new PersonInChargeAllEmployeesBuilder(dbConnection);

//        OrdinationsperiodIndikationerBuilder ordinationsPeriodBuilder = new OrdinationsperiodIndikationerBuilder(connectionFilePath);
//        ordinationsPeriodBuilder.buildOrdinationPeriodIndikation(centreID, regpatId, false);   // true: write to file

//        HemorrhagesBuilder hemorrhagesBuilder = new HemorrhagesBuilder(dbConnection);
//        hemorrhagesBuilder.buildHemorrhages(centreID, regpatSSN, false);

//        BehandlingOchDoseringsperiodBuilder behandlingOchDoseringsperiodBuilder = new BehandlingOchDoseringsperiodBuilder(dbConnection);
//        behandlingOchDoseringsperiodBuilder.buildBehandlingOchDosering(centreID, regpatSSN, false);

//          LabInrBuilder labInrBuilder = new LabInrBuilder(connectionFilePath);
//          labInrBuilder.buildLabINR(centreID, regpatId, Boolean.TRUE);

//        MatvardeBuilder matvardeBuilder = new MatvardeBuilder(connectionFilePath);
//        matvardeBuilder.buildMatvarde(centreID, regpatId, true);

//          LMHBuilder lmhBuilder = new LMHBuilder(connectionFilePath);
//          lmhBuilder.buildLMH(centreID, regpatId, false);

            dbConnection.close();
            myConnection.disconnectSession();
    }
}
