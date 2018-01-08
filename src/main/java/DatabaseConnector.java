import java.sql.Connection;
import java.util.Optional;

public interface DatabaseConnector {

    public Optional<Connection> getConnection();
}
