import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class Testing {
    public static void main(String[] args) {
        System.out.println("Morning");

        DatabaseConnector handle = new DatabaseConnectorImpl
                .Builder()
                .setJdbcPropertiesFile(Paths.get("D:\\Development\\workspaces\\workspace_jetbrains_utils\\DatabaseConnector\\config\\jdbc.properties"))
                .buildOracleDataSource();

        Connection con = handle.getConnection().get();
        try {
            if(con.isValid(1000)){
                System.out.println("All good");
            }
            else
                System.out.println("Not good");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
