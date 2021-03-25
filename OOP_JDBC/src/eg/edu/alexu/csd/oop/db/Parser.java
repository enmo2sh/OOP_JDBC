package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Parser {
    private static final Exception SQLException = null;
    public static Database helper= new DB();

    public abstract boolean  validation(String query);

    public abstract Object calls(String query) throws SQLException ;

}