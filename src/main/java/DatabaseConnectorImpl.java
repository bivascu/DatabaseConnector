import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class DatabaseConnectorImpl implements DatabaseConnector{

    ComboPooledDataSource cpds;

    private DatabaseConnectorImpl(Builder builderArg){
        cpds = builderArg.getBuilderCpds();
    }

    @Override
    public Optional<Connection> getConnection() {
        if(Objects.nonNull(cpds)) {
            try {
                return Optional.of(cpds.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }


    public static class Builder {
        private ComboPooledDataSource builderCpds;
        private String jdbcUrl;
        private String username;
        private String password;
        private String databaseName;
        private int minPoolSize = 5;
        private int aquireIncrement = 5;
        private int maxPoolSize = 20;
        private int maxStatements = 100;
        private int checkoutTimeout=3000;

        public ComboPooledDataSource getBuilderCpds() {
            return builderCpds;
        }

        public void setBuilderCpds(ComboPooledDataSource builderCpds) {
            this.builderCpds = builderCpds;
        }

        public Builder setJdbcUrl(String jdbcUrl) {
            Objects.requireNonNull(jdbcUrl, "jdbcUrl cannot be null");
            this.jdbcUrl = jdbcUrl;
            return this;
        }

        public Builder setUsername(String username) {
            Objects.requireNonNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            Objects.requireNonNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public Builder setDatabaseName(String databaseName) {
            Objects.requireNonNull(databaseName, "databaseName cannot be null");
            this.databaseName = databaseName;
            return this;
        }

        public Builder setMinPoolSize(int minPoolSize) {
            if(!Objects.nonNull(minPoolSize))
                this.minPoolSize = minPoolSize;
            return this;
        }

        public Builder setAquireIncrement(int aquireIncrement) {
            if(Objects.nonNull(aquireIncrement))
                this.aquireIncrement = aquireIncrement;
            return this;
        }

        public Builder setMaxPoolSize(int maxPoolSize) {
            if(Objects.nonNull(maxPoolSize))
                this.maxPoolSize = maxPoolSize;
            return this;
        }

        public Builder setMaxStatements(int maxStatements) {
            if(Objects.nonNull(maxStatements))
                this.maxStatements = maxStatements;
            return this;
        }

        public DatabaseConnectorImpl buildOracleDataSource() {
            Objects.requireNonNull(jdbcUrl, "jdbcUrl cannot be null");
            Objects.requireNonNull(username, "username cannot be null");
            Objects.requireNonNull(password, "password cannot be null");
            Objects.requireNonNull(databaseName, "databaseName cannot be null");

            builderCpds = new ComboPooledDataSource();
            try {
                builderCpds.setDriverClass(DatabaseConstants.ORACLE_DRIVER_CLASS.getValue());
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            builderCpds.setJdbcUrl(String.join("","jdbc:oracle:thin:@//",jdbcUrl ,":1521/",databaseName ));
            builderCpds.setUser(username);
            builderCpds.setPassword(password);
            builderCpds.setMinPoolSize(minPoolSize);
            builderCpds.setMaxPoolSize(maxPoolSize);
            builderCpds.setAcquireIncrement(aquireIncrement);
            builderCpds.setMaxStatements(maxStatements);
            builderCpds.setCheckoutTimeout(checkoutTimeout);
            return new DatabaseConnectorImpl(this);
        }
    }

    private enum DatabaseConstants{
        ORACLE_DRIVER_CLASS("oracle.jdbc.driver.OracleDriver"),
        MYSQL_DRIVER_CLASS("com.mysql.jdbc.Driver");

        public String getValue() {
            return value;
        }

        private String value;

        private DatabaseConstants(String value){
            this.value = value;
        }
    }

}
