package org.example;

import Person.PersonInChargeBuilder;
import Person.PersonInChargeException;
import Person.PersonInitializationException;
import Person.PersonPatientBuilder;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Auricula Personobjekt
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException, PersonInitializationException, PersonInChargeException {
        final String host = "jdbc:mysql://localhost:3306/auricula_export_tio_100";
        final String uName = "root";
        final String uPass = "ivo64..";

//        var personPat = new PersonPatientBuilder(host, uName, uPass);
//        personPat.buildPersonPatient(true);         // boolean: write to file

        var personCharge = new PersonInChargeBuilder(host, uName, uPass);
        personCharge.buildPersonInCharge(true, "Group");     // boolean: write to file; Group || All

    }
}
