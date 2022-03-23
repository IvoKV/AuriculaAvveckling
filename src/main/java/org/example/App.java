package org.example;

/** GÖR INTE NÅGON IMPORTOPTIMERING!! **/
import Person.PersonInChargeException;
import Person.PersonInitializationException;
import auxilliary.MyConnection;
import com.jcraft.jsch.JSchException;
import ordination.Matvarde.MatvardeInitializationException;
import ordination.Waran.*;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Auricula Personobjekt
 *
 */
public class App 
{
    private static final String connectionFilePath = "src/resource/ConnectionString.txt";

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException, OrdinationsperiodInitializeException, MatvardeInitializationException, JSchException {

        Path filePath = Path.of(connectionFilePath);

        //MyInetAddress.getIpAddress();
        MyConnection myConnection = new MyConnection();
        myConnection.createSshTunnel();
        Connection dbConnection = myConnection.getDbConnection();

//        var personPat = new PersonPatientBuilder(connectionFilePath);
//        personPat.buildPersonPatient(true);         // boolean: write to file

//        var personCharge = new PersonInChargeBuilder(connectionFilePath);
//        personCharge.buildPersonInCharge(true, "GroupBy");     // boolean: write to file; Group || All


        String centreID = "11012AK";
        //int regpatId = 0;   // <0>: all patients, <regpatid>: only chosen patient
        //int regpatId = 54241;
        //int regpatId = 489980;
        int regpatId = 0;

//        OrdinationsperiodIndikationerBuilder ordinationsPeriodBuilder = new OrdinationsperiodIndikationerBuilder(connectionFilePath);
//        ordinationsPeriodBuilder.buildOrdinationPeriodIndikation(centreID, regpatId, false);   // true: write to file

//        HemorrhagesBuilder hemorrhagesBuilder = new HemorrhagesBuilder(connectionFilePath);
//        hemorrhagesBuilder.buildHemorrhages(centreID, regpatId, true);

//        BehandlingOchDoseringsperiodBuilder behandlingOchDoseringsperiodBuilder = new BehandlingOchDoseringsperiodBuilder(connectionFilePath);
//        behandlingOchDoseringsperiodBuilder.buildBehandlingOchDosering(centreID, regpatId, true);

//        MatvardeBuilder matvardeBuilder = new MatvardeBuilder(connectionFilePath);
//        matvardeBuilder.buildMatvarde(centreID, regpatId, true);

//          LabInrBuilder labInrBuilder = new LabInrBuilder(connectionFilePath);
//          labInrBuilder.buildLabINR(centreID, regpatId, Boolean.TRUE);

//        MatvardeBuilder matvardeBuilder = new MatvardeBuilder(connectionFilePath);
//        matvardeBuilder.buildMatvarde(centreID, regpatId, true);

//          LMHBuilder lmhBuilder = new LMHBuilder(connectionFilePath);
//          lmhBuilder.buildLMH(centreID, regpatId, false);
    }
}
