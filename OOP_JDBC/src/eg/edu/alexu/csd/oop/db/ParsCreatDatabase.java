package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsCreatDatabase extends Parser{
    static LinkedList<String> DBS=new LinkedList<String>();
    @Override
    public boolean validation (String query){
        String whole="(\\s*((?i)\\bcreate\\b)\\s+((?i)\\bdatabase\\b)\\s+(([-.:a-zA-Z_?0-9/]\\\\?)+\\s*$))";
        Pattern help = Pattern.compile(whole);
        Matcher A=help.matcher(query);
        if(A.find())
            return true;
        else
            return false;
    }

    @Override
    public Object calls(String query) throws SQLException {
        String n="";
        if(query.charAt(0)!=' ')
            n=n+query.charAt(0);
        for(int i=1;i<query.length()-1;i++){
            if((query.charAt(i)==' ')&&(query.charAt(i+1)==' '))
                n=n;
            else
                n=n+query.charAt(i);
        }
        if(query.charAt(query.length()-1)!=' ')
            n=n+query.charAt(query.length()-1);

        String[]split=n.split(" ");
        String res;
        if(DBS.contains(split[2]))
            res=helper.createDatabase(split[2],true);
        else {
            res = helper.createDatabase(split[2], false);
            DBS.add(split[2]);
        }
        return res;
    }
}
