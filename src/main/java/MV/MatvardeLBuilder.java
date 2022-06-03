package MV;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatvardeLBuilder {
    private Connection connection;

    private final String sqlScriptFileMatvardeL = "src/resource/sql/matvarde/MatvardeL.sql";

    List<MatvardeL> matvardeLList = null;

    public MatvardeLBuilder(final Connection con) {
        this.connection = con;
        this.matvardeLList = new ArrayList<>();
    }

    public void buildMatvardeL(String centreId, String regpatSSN, Boolean writeToFile) throws IOException, SQLException, MatvardeLBuilderException {
        Path file = Path.of(sqlScriptFileMatvardeL);

        String sqlStatement = Files.readString(file);

        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, centreId);
        preparedStatement.setString(2, regpatSSN);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            MatvardeL matvardeL = new MatvardeL(
                rs.getString("CENTREID"),
                rs.getString("SSN"),
                rs.getShort("SSN_TYPE"),
                rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"),

                    rs.getString("HMR_LEVERNJURSJUKDOM"),
                    rs.getString("HMR_ETANOLMISSBRUK"),
                    rs.getString("HMR_MALIGNINITET"),
                    rs.getString("HMR_REDUCERAT_TROMBOCYTANTAL_FUNK"),
                    rs.getString("HMR_TIDIGARE_BLÖDNING"),

                    rs.getString("HMR_HYPERTONI"),
                    rs.getString("HMR_ANEMI"),
                    rs.getString("HMR_GENETISKAFAKTORER"),
                    rs.getString("HMR_STOR_RISK_FÖR_FALL"),
                    rs.getString("HMR_STROKE"),

                    rs.getString("HMR_CREATEDBY"),
                    rs.getString("HMR_UPDATEDBY"),
                    rs.getTimestamp("HMR_TSCREATED"),
                    rs.getString("CHA2_HJÄRTSVIKT"),
                    rs.getString("CHA2_HYPERTONI"),

                    rs.getString("CHA2_DIABETES"),
                    rs.getString("CHA2_STROKE"),
                    rs.getString("CHA2_CREATEDBY"),
                    rs.getString("CHA2_UPDATEDBY"),
                    rs.getTimestamp("CHA2_TSCREATED")
            );
        }
    }
}
