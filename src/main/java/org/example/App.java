package org.example;

import Person.PersonInChargeException;
import Person.PersonInitializationException;
import Person.PersonPatientBuilder;

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

    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException {

        Path filePath = Path.of(connectionFilePath);

        var personPat = new PersonPatientBuilder(connectionFilePath);
        personPat.buildPersonPatient(true);         // boolean: write to file

//        var personCharge = new PersonInChargeBuilder(host, uName, uPass);
//        personCharge.buildPersonInCharge(true, "Group");     // boolean: write to file; Group || All



    }
}
