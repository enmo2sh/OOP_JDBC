package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsDropDatabase extends Parser {

    @Override
    public boolean validation (String query){
        String whole="(\\s*((?i)\\bdrop\\b)\\s+((?i)\\bdatabase\\b)\\s+(([a-zA-Z_?0-9/]\\\\?)+\\s*$))";
        Pattern help = Pattern.compile(whole);
        Matcher A=help.matcher(query);
        if(A.find())
            return true;
        else
            return false;
    }

    @Override
    public Object calls(String query) throws SQLException {
        Boolean x=helper.executeStructureQuery(query);
        return x;
    }
}
