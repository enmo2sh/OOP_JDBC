package eg.edu.alexu.csd.oop.db;

public class GetQuery {

    ModifyQuery modify;

    public String Name(String query){
        String[] split=query.split(" ");
        return  split[2];
    }

    public String inside(String query){
        String s = query.substring(query.indexOf('('), query.indexOf(')') + 1);
        return  s;
    }

}
