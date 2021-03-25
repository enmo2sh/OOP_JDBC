package eg.edu.alexu.csd.oop.jdbc;
import java.io.File;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import eg.edu.alexu.csd.oop.db.DB;

public class MyDriver implements Driver {
    DB k=new DB();
    logging log =new logging ();

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if (url.contentEquals("jdbc:xmldb://localhost")) {
            log.help().info("Accepted URL");
            return true;}
        else {
            log.help().warning("Wrong URL");
            return false;}
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if(info.size()!=0) {
            File dir = (File) info.get("path");
            String path = dir.getAbsolutePath();
            k.executeStructureQuery("create database " + path);
        }
        ConnectionPool pool = new ConnectionPool();
        pool.createConnections();
        Connection x=pool.getConnection();
        log.help().info("Connection Done");
        return x; // pool
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        DriverPropertyInfo[] res=new DriverPropertyInfo[info.size()];
        res[0].name="path";
        res[0].value=info.getProperty("path");
        log.help().info("Driver Property Info Has Set ");
        return res;
    }

    /**************************************************************************/

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean jdbcCompliant() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }
}