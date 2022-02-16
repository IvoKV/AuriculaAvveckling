package org.example;

/** GÖR INTE NÅGON IMPORTOPTIMERING!! **/
import Person.PersonInChargeBuilder;
import Person.PersonInChargeException;
import Person.PersonInitializationException;
import Person.PersonPatientBuilder;
import ordination.OrdinationsInitializeException;
import ordination.OrdinationsPeriodBuilder;
import ordination.Ordinationsperiod;

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

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException, OrdinationsInitializeException {

        Path filePath = Path.of(connectionFilePath);

//        var personPat = new PersonPatientBuilder(connectionFilePath);
//        personPat.buildPersonPatient(true);         // boolean: write to file

//        var personCharge = new PersonInChargeBuilder(connectionFilePath);
//        personCharge.buildPersonInCharge(true, "GroupBy");     // boolean: write to file; Group || All

        OrdinationsPeriodBuilder ordinationsPeriodBuilder = new OrdinationsPeriodBuilder(connectionFilePath);
        ordinationsPeriodBuilder.buildPersonPatient(true);

    }
}
