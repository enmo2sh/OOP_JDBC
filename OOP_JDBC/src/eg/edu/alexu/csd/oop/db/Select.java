package eg.edu.alexu.csd.oop.db;
 
import java.util.ArrayList;
import java.util.LinkedList;
 
public class Select {
    Facade facade = new Facade();
    public Object[][] select(String query) {
        ArrayList<String>[] Base= facade.base.GetBase();
        int m=0,n = 0,s=0,condition2=0,c2=0,c1=0;
        Object[][] table = null;
        Object value="";
        m= Base[0].size()-1;
        LinkedList<Integer> list = null;
        String temp=query.toLowerCase(),condition;
        if (temp.contains("where")) {
            condition = temp.substring(temp.indexOf("where")+6,temp.length());
            m= facade.Where.where_condition(condition).size();
            list =facade.Where.where_condition(condition);
        }
        String[] split=temp.split("( )|(\\*)|(\\,)");
        String[] noC = null;
        for (int i=0;i<split.length;i++) {
            if (split[i].contentEquals(""))
                s++;
            if (split[i].contains("from")) {
                n= i-1-s;
                break;
            }
        }
        if (!temp.contains("*")) {
            condition2=1;
            noC= new  String[n];
            for (int i=0;i<n;i++) {
                int k=i+1;
                if (!split[k].contentEquals(""))
                    noC[i]=split[k];
                else
                    noC[i]=split[k+1];
            }
        }else {
            n=Base.length;
        }
        table = new Object[m][n];
 
        for (int i=1;i<Base[0].size();i++){
            c2=0;
            for (int j=0;j<Base.length;j++){
                value=Base[j].get(i);
                if(value==null)
                    value="null";
                else if (((String)value).contains("'")){
                    value=((String)value).substring(1,((String)value).length()-1 );
                }else{
                    value= Integer.parseInt(((String)value));
                }
                if ((temp.contains("where")) ){
                    for (int k=0;k<list.size();k++) {
                        if (i==list.get(k)) {
                            if (((condition2==1)&&(find (Base[j].get(0),noC)))|| (condition2==0)){
                                table[c1][c2]= value;
                                if (c2==n-1){
                                    c1++;
                                }
                                c2++;
                            }
                            break;
                        }
                    }
                }else if (((condition2==1)&&(find (Base[j].get(0),noC)))||(condition2==0)){
                    table[c1][c2]= value;
                    if (c2==n-1){
                        c1++;
                    }
                    c2++;
                }
            }
        }
       
        int index=0;
        if (temp.contains("order"))
        {          
            String[] split2=temp.split("by");
            String[] split3=split2[1].split(" ");
            String type;
            String specific = "jjj";
            if (temp.contains("des"))
            {
                type ="des";
            }else {
                type ="asc";
            }
            specific = split3[1];
            if (temp.contains("*")&&temp.contains("order")) {
                    for (int j=0;j<Base.length;j++){
                      if (split3[1].contentEquals(Base[j].get(0)))
                          index=j;
                    }            
            }else if  (!(temp.contains("*"))&&temp.contains("order")) {
                for (int j=0;j<noC.length;j++){
                    if (split3[1].contentEquals(noC[j]))
                        index=j;
                  }
            }
            table=facade.order.Order_Select(table, index, type);
        }
     
        return table;
    }
 
    private boolean find (String s, String[] arr) {
        s=s.toLowerCase();
        for (int i=0;i<arr.length;i++)
        {
            if (s.contentEquals(arr[i]))
            {
                return true;
            }
        }
        return false;
    }
}