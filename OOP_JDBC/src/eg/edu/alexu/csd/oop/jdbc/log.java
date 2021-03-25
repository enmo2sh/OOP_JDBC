package eg.edu.alexu.csd.oop.jdbc;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class log {
    
    public static void main(String[] args) throws SQLException {
       /* Driver w=new MyDriver();
        Properties info = new Properties();
        File dbDir = new File("sample" + System.getProperty("file.separator") + ((int)(Math.random() * 100000)));
        info.put("path", dbDir.getAbsoluteFile());
        Connection connection = w.connect("jdbc:xmldb://localhost", info);
        Statement statement = connection.createStatement();
        statement.execute("DROP DATABASE " + "dd");
        if(true)
            statement.execute("CREATE DATABASE " + "dd");
        statement.close();*/
    }

}