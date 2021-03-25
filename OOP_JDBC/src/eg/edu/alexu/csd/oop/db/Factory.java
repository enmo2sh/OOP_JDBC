package eg.edu.alexu.csd.oop.db;

public class Factory {
    Parser parser= null;
    private static final Exception SQLException = null;
    public static Database helper= new DB();
    public Parser Object(String query)  {
        try {
            String temp = query.toLowerCase();

             if(temp.contains("database")&&(temp.contains("drop")))
                parser = new ParsDropDatabase();
           else  if (temp.contains("database")&&(temp.contains("create")))
                parser = new ParsCreatDatabase();

            else if (temp.contains("drop"))
                parser = new ParsDropTable();

            else if (temp.contains("create"))
                parser = new ParsCreatTable();

            else if (temp.contains("select"))
                parser = new ParsSelectTable();

            else if (temp.contains("update"))
                parser = new ParsUpdateTable();

            else if (temp.contains("insert"))
                parser = new ParsInsert();

            else if (temp.contains("delete"))
                parser = new ParsDeleteTable();
        }catch(Exception e){

        }
        return parser;
    }


}