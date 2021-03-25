package eg.edu.alexu.csd.oop.db;

public class Path {
    private static String path ="";
    private static String DB= "";

    public void setPath (String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public void setdefaultDB (String def){
        this.DB = def;
    }

    public String getdefaultDB(){
        return DB;
    }
}
