package org.example;

import DBSource.DBConnection;
import Person.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        var personPat = new PersonPatientBuilder(host, uName, uPass);
        personPat.buildPersonPatient(false);         // boolean: write to file

        var personCharge = new PersonInChargeBuilder(host, uName, uPass);
        personCharge.buildPersonInCharge(true);     // boolean: write to file

    }
}
