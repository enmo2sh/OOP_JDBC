package eg.edu.alexu.csd.oop.db;

import java.io.File;

public class SearchInDB {
    private String table;
    SearchInDB(String table){
        this.table=table;
    }
    private Facade facade = new Facade();
    public boolean search(){
        boolean check=false;
        if((!facade.path.getdefaultDB().equals(" "))&&(!facade.path.getdefaultDB().equals(System.getProperty("user.dir"))))
             check= new File(facade.path.getdefaultDB() + File.separator + table + ".xml").exists();

        return check;
    }
}
