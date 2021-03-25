package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ParsInsert extends Parser {


    @Override
    public boolean validation(String query) {
        String whole="(((?i)\\binsert\\s+into\\b)\\s+((?i)\\b[a-zA-Z_?0-9/]+\\b)\\s*(\\((((?i)\\b[a-zA-Z_?0-9/]+\\b)\\s*\\,?\\s*)+\\))?\\s*((?i)\\bvalues\\b)\\s*\\(\\s*((\\'?(?i)\\b[a-zA-Z_0-9/]+\\b\\'?)\\s*\\,?\\s*)+\\)\\s*$)";

        Pattern help = Pattern.compile(whole);
        Matcher A=help.matcher(query);
        if(A.find())
            return true;
        else
            return false;
    }


    @Override
    public Object calls(String query) throws SQLException {
        int counter= helper.executeUpdateQuery(query);
        return counter;
    }

}