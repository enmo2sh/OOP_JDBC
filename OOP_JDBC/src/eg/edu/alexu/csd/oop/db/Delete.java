package eg.edu.alexu.csd.oop.db;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Delete {
    Facade facade = new Facade();

    public int Delete(String query) throws ParserConfigurationException, IOException, SAXException, TransformerException, XPathExpressionException {
       query=query.toLowerCase();
        String []split=query.split("where ");
        int res;
        ArrayList<String>[] Base= facade.base.GetBase();
        if(split.length>1) {
            LinkedList<Integer> accepted=new LinkedList<Integer>();
            accepted= facade.Where.where_condition(split[1]);
            res=accepted.size();
            int z=0;
            for(int x=0;x<accepted.size();x++) {
                for (int j = 0; j < Base.length; j++)
                    facade.base.RemoveFromBase(j,Base[j].get(accepted.get(x)-z));

                z++;
            }
        }
        else{
            int l=Base[0].size()-1;
            for(int j=0;j<Base.length;j++){
                for(int f=1;f<Base[j].size();f++){
                    facade.base.RemoveFromBase(j,Base[j].get(f));
                    f--;
                }
            }
            res= l;
        }
        return res;
    }

}
