package eg.edu.alexu.csd.oop.db;

import java.util.LinkedList;

public class tablesName {
    private static LinkedList<String> Tables=new LinkedList<String>();
    private static String defaultTable ="";

    public void addTable (String table){
        Tables.add(table);
        defaultTable = table;
    }

    public LinkedList<String> getTables (){
        return Tables;
    }

    public void RemoveFromTable (String table){
        Tables.remove(table);
    }

    public void clearTables(){
        if(Tables.size()>0)
            Tables.clear();
    }

    public void SetDefaultTable(String newDefault){
        defaultTable= newDefault;
    }

    public String GetDefaultTable(){
        return  defaultTable;
    }

}
