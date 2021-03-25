package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ParsCreatTable extends Parser {


    @Override
    public boolean validation (String query){
        String whole="(\\s*((?i)\\bcreate\\b)\\s+((?i)\\btable\\b)\\s+[a-zA-Z_?0-9/]+\\s*\\(\\s*(((?i)\\b[a-zA-Z_?0-9/]+\\b)\\s+((?i)\\bint|varchar)\\s*\\,?\\s*)+\\)\\s*$)";
        Pattern help = Pattern.compile(whole);
        Matcher A=help.matcher(query);
        if(A.find())
            return true;
        else
            return false;
    }

    @Override
    public Object calls(String query) throws SQLException {
        boolean x=helper.executeStructureQuery(query);
        return x;
    }

}