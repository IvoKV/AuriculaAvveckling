package org.example;

/** GÖR INTE NÅGON IMPORTOPTIMERING!! **/
import Person.PersonInChargeException;
import Person.PersonInitializationException;
import ordination.Waran.OrdinationsperiodInitializeException;
import ordination.Waran.OrdinationsperiodIndikationerBuilder;

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

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException, OrdinationsperiodInitializeException {

        Path filePath = Path.of(connectionFilePath);

//        var personPat = new PersonPatientBuilder(connectionFilePath);
//        personPat.buildPersonPatient(true);         // boolean: write to file

//        var personCharge = new PersonInChargeBuilder(connectionFilePath);
//        personCharge.buildPersonInCharge(true, "GroupBy");     // boolean: write to file; Group || All


        String centreID = "11012AK";
        int regpatId = 0;   // <0>: all patients, <regpatid>: only chosen patient
        //int regpatId = 54241;
        OrdinationsperiodIndikationerBuilder ordinationsPeriodBuilder = new OrdinationsperiodIndikationerBuilder(connectionFilePath);
        ordinationsPeriodBuilder.buildPersonPatient(centreID, regpatId, true);   // true: write to file

    }
}
