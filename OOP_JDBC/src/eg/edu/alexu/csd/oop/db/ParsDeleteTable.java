package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ParsDeleteTable extends Parser {


    @Override
    public boolean validation(String query) {
        String whole= "(\\s*((?i)\\bdelete\\s+from\\b)\\s+((?i)\\b[a-zA-Z_?0-9/]+\\b)(\\s+((?i)\\bwhere\\b)\\s+(((?i)\\bnot\\b)\\s+)?[a-zA-Z_?0-9\\s/]+\\s*[\\=\\<\\>]\\s*[//'a-zA-Z_?0-9\\s/]+((((\\s+(?i)\\band|or\\b)+\\s+)[a-zA-Z_?0-9/]+\\s*[\\=\\<\\>]\\s*[//'a-zA-Z_?0-9\\s/]+)*)?)?\\s*$)";
        Pattern help = Pattern.compile(whole);
        Matcher A=help.matcher(query);
        if(A.find())
            return true;
        else
            return false;
    }


    @Override
    public Object calls(String query) throws SQLException {
        int counter =helper.executeUpdateQuery(query);
        return counter;
    }

}