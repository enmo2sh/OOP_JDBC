package eg.edu.alexu.csd.oop.db;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Insert {
    private Facade facade = new Facade();

    public  int add(String query) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        ArrayList <String>[] Base= facade.base.GetBase();
        String s =facade.query.inside(query);
        String t;
        s= query.substring(query.indexOf('('),query.indexOf(')')+1);
        query=query.replace(s," ");
        if(!query.contains("(")) {
            t=s;
            s="("+Base[0].get(0);
            for(int i=1; i<Base.length; i++)
                s+=","+Base[i].get(0);
            s+=")";
        }
        else
            t= query.substring(query.indexOf('('),query.indexOf(')')+1);
        query=query.replace(t," ");

        String data[][]=facade.queryOperations.splitting(s,t);
        String []split=query.split(" ");
        String Table_name=split[2];
        return   addReal(Table_name,data);

    }


    private int addReal(String Table_name,String firstIn[][]) {
        ArrayList <String>[] Base= facade.base.GetBase();
        CheckValues Ch=new CheckValues();
        String [][] arr = Ch.check(firstIn);
        if(arr==null) {
            System.out.println("invalid insert");
            return 0;
        }
        else{
            for(int i=0;i<arr.length; i++)
                facade.base.AddToBase(i,arr[i][1]);
        }
        return 1;

    }
}
