import java.sql.Connection;
import java.sql.SQLException;

public class Testing {
    public static void main(String[] args) {
        System.out.println("Morning");

        DatabaseConnector handle = new DatabaseConnectorImpl
                .Builder()
                .setUsername("SYSTEM")
                .setPassword("oracle")
                .setDatabaseName("XE")
                .setJdbcUrl("localhost").buildOracleDataSource();

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
