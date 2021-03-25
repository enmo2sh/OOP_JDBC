package eg.edu.alexu.csd.oop.db;
 
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
 
public class DB implements Database{
    private static int counter=0;
    Facade facade = new Facade();
    BasicFacade basicfacade = new BasicFacade();
 
    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        if(dropIfExists) {
            try {
                executeStructureQuery("DROP DATABASE " + databaseName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            executeStructureQuery("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        counter++;
        return facade.path.getPath();
    }
 
    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
        try {
            facade.pars = facade.factory.Object(query);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String Sub = null;
        boolean res = false;
        if (facade.pars.validation(query)) {
            query=facade.queryOperations.remove_extra_spaces(query);
            if(query.contains("(")){
                Sub= query.substring(query.indexOf('('),query.indexOf(')')+1);
                query=query.replace(Sub," ");
            }
            String[] split=query.split(" ");
            if(split[1].equalsIgnoreCase("database")){
                    if (split[2].contains(":"))
                        facade.path.setPath(split[2]);
                    else
                        facade.path.setPath(System.getProperty("user.dir") + File.separator + split[2]);
                basicfacade.creatOrDrop = new ImpData();
                if(split[0].equalsIgnoreCase("create"))
                    res = basicfacade.creatOrDrop.create();
                else if(split[0].equalsIgnoreCase("drop"))
                    res = basicfacade.creatOrDrop.drop();
            }
            if(split[1].equalsIgnoreCase("table")) {
                if (!facade.path.getdefaultDB().equals("")) {
                    basicfacade.creatOrDrop = new ImpTable(split[2], Sub);
                    if (split[0].equalsIgnoreCase("create"))
                        res = basicfacade.creatOrDrop.create();
                    else if (split[0].equalsIgnoreCase("drop"))
                        res = basicfacade.creatOrDrop.drop();
                }
                else {
                    System.out.println("you created table without DB");
                    throw new SQLException("No Database Found");
                }
            }
        }
        else{
            System.out.println("the query isnot valid");
            throw new SQLException();
        }
        counter=0;
        return res;
    }
 
 
    @Override
    public Object[][] executeQuery(String query) throws SQLException {
        Object[][] table = null;
        try {
            facade.pars = facade.factory.Object(query);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (facade.pars.validation(query)) {
            table= basicfacade.select.select(query);
        }
        return table;
    }
 
 
    @Override
    public int executeUpdateQuery(String query) throws SQLException {
        ArrayList<String>[] Base= facade.base.GetBase();
        LinkedList<String> Tables= facade.Tables.getTables();
        int result=0;
        try {
 
            facade.pars = facade.factory.Object(query);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (facade.pars.validation(query)) {
            query=facade.queryOperations.remove_extra_spaces(query);
            String h=query;
            if(h.contains("(")) {
                String s = facade.query.inside(h);
                h= h.replace(s, " ");
            }
            String []split=h.split(" ");
            SearchInDB FindTable = new SearchInDB(split[2]);
            if(split[0].equalsIgnoreCase("insert")){
                if((Tables.contains(split[2].toLowerCase()))|| FindTable.search()) {
                    try {
                        basicfacade.saveORread.Save(split[2],counter);
                        basicfacade.saveORread.read(split[2]);
                        if(counter==5)
                            counter=0;
                        facade.Tables.SetDefaultTable(split[2]);
                        result=basicfacade.insert.add(query);
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("you didn't create this table");
                }
            }
            else if(split[0].equalsIgnoreCase("delete")){
                if(((Tables.contains(split[2].toLowerCase())) || FindTable.search())&&(Base[0].size()>1)){
                    try {
                        basicfacade.saveORread.Save(split[2],counter);
                        basicfacade.saveORread.read(split[2]);
                        if(counter==5)
                            counter=0;
                        facade.Tables.SetDefaultTable(split[2]);
                        result = basicfacade.delete.Delete(query);
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {

                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("you didnt create this table or the table is empty");
                }
            }
            else if(split[0].equalsIgnoreCase("update")){
                SearchInDB FindUpdated = new SearchInDB(split[1]);
                if((Tables.contains(split[1].toLowerCase())) || FindUpdated.search()){
                    if(Base[0].size()>1) {
                    try {
                        basicfacade.saveORread.Save(split[1],counter);
                        basicfacade.saveORread.read(split[1]);
                        if(counter==5)
                            counter=0;
                        facade.Tables.SetDefaultTable(split[1]);
                        result = basicfacade.update.Update_Path(query);
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
                    else{
                        System.out.println("the table is empty");
                    }
                }
                else {
                    System.out.println("you didnt create this table");
                    throw new SQLException();
                }
            }
        }
        counter++;
        return result;
    }

    public String[] Names (String query) {
        String temp=query.toLowerCase();
        int n;
        String noC[] = null;
        if (!temp.contains("*")) {
        int count = query.length() - query.replace(",", "").length();
        query =  query.replace(",", "");
        query =  query.replace("*", " ");
        query=facade.queryOperations.remove_extra_spaces(query);
        String[] split=query.split("( )");     
        n=count+1;
            noC= new  String[n];
            if (temp.contains("as")) {
                noC[0]=split[3];
                int k=6;
                for (int i=1;i<n;i++) {
                    noC[i]= split[k];
                    k=k+3;
                }
            }else {
            int j=1;
            noC[0]=split[1];
            for (int i=1;i<n;i++) {
                        j =i+1;                   
                    noC[i]=split[j];
            }
            }
        }else {
            ArrayList<String>[] Base= facade.base.GetBase();
            noC= new  String[Base.length];         
            for (int i=0;i<Base.length;i++)
            {
                noC[i] = Base[i].get(0);
            }
        }
        return noC;  
    }
}