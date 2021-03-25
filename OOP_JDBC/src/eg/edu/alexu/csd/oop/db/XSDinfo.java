package eg.edu.alexu.csd.oop.db;

public class XSDinfo {

    private static String[][] xsd;

    public void SetXSD( String[][]type){
        xsd = type;
    }

    public String [][] GetXSD (){
        return xsd;
    }
}
