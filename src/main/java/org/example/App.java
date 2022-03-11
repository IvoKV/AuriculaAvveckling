package org.example;

/** GÖR INTE NÅGON IMPORTOPTIMERING!! **/
import Person.PersonInChargeException;
import Person.PersonInitializationException;
import ordination.Matvarde.LabInr;
import ordination.Matvarde.LabInrBuilder;
import ordination.Matvarde.MatvardeBuilder;
import ordination.Matvarde.MatvardeInitializationException;
import ordination.Waran.*;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

/**
 * Auricula Personobjekt
 *
 */
public class App 
{
    private static final String connectionFilePath = "src/resource/ConnectionString.txt";

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException, OrdinationsperiodInitializeException, MatvardeInitializationException {

        Path filePath = Path.of(connectionFilePath);

//        var personPat = new PersonPatientBuilder(connectionFilePath);
//        personPat.buildPersonPatient(true);         // boolean: write to file

//        var personCharge = new PersonInChargeBuilder(connectionFilePath);
//        personCharge.buildPersonInCharge(true, "GroupBy");     // boolean: write to file; Group || All


        String centreID = "11012AK";
        //int regpatId = 0;   // <0>: all patients, <regpatid>: only chosen patient
        //int regpatId = 54241;
        int regpatId = 489980;
        //int regpatId = 0;
//        OrdinationsperiodIndikationerBuilder ordinationsPeriodBuilder = new OrdinationsperiodIndikationerBuilder(connectionFilePath);
//        ordinationsPeriodBuilder.buildOrdinationPeriodIndikation(centreID, regpatId, true);   // true: write to file

//        HemorrhagesBuilder hemorrhagesBuilder = new HemorrhagesBuilder(connectionFilePath);
//        hemorrhagesBuilder.buildHemorrhages(centreID, regpatId, true);

//        BehandlingOchDoseringsperiodBuilder behandlingOchDoseringsperiodBuilder = new BehandlingOchDoseringsperiodBuilder(connectionFilePath);
//        behandlingOchDoseringsperiodBuilder.buildBehandlingOchDosering(centreID, regpatId, true);

//        MatvardeBuilder matvardeBuilder = new MatvardeBuilder(connectionFilePath);
//        matvardeBuilder.buildMatvarde(centreID, regpatId, true);

          LabInrBuilder labInrBuilder = new LabInrBuilder(connectionFilePath);
          labInrBuilder.buildLabINR(centreID, regpatId, Boolean.TRUE);
    }
}
