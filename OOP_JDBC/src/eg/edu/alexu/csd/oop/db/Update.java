package eg.edu.alexu.csd.oop.db;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Update {
    private Facade facade = new Facade();

    public int Update_Path (String query ) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        String []query1;
        String Condition;
        String[] query2;
        String[] query3;
        String New_value;
        String Table_Name;
        int num=0;
        String temp=query.toLowerCase();
        if(temp.contains("where")) {
            query1 = query.split("((?i)\\bwhere\\b)");
            Condition =query1[1];
            query2=query1[0].split("((?i)\\bset\\b)");
            New_value =query2[1];
            query3=query2[0].split(" ");
            Table_Name=query3[1];
            num= UpDate_Method (Table_Name, Condition , New_value);
        }
        else {
            query2=query.split("((?i)\\bset\\b)");
            New_value =query2[1];
            query3=query2[0].split(" ");
            Table_Name=query3[1];
            num= UpDate_Method (Table_Name, "" , New_value);
        }
        return num;
    }

    private int UpDate_Method (String Table_Name, String condition ,String New_value) {
        String []temp=New_value.split(",");
        String [][]new_date = new String[temp.length][2];
        ArrayList<String>[] Base= facade.base.GetBase();
        for(int q=0;q<temp.length;q++) {
            temp[q]=facade.queryOperations.remove_extra_spaces(temp[q]);
            String[] equal=temp[q].split("=");
            new_date[q][0]=equal[0];
            new_date[q][1]=equal[1];

        }
        LinkedList<Integer> target=new LinkedList();
        CheckValues check_new=new CheckValues();
        new_date=check_new.check(new_date);
        if(new_date!=null) {
            if (!(condition.equals("")||condition.equals(" "))) {
                condition=facade.queryOperations.remove_extra_spaces(condition);
                target= facade.Where.where_condition(condition);
            }
            else {
                for(int o=1;o<Base[0].size();o++) {
                    target.add(o);
                }
            }
            for(int i=0;i<target.size();i++) {
                for(int j=0;j<Base.length;j++){
                    for(int z=0;z<new_date.length;z++) {
                        if (Base[j].get(0).equalsIgnoreCase(new_date[z][0])) {
                            int s= Integer.parseInt(String.valueOf(target.get(i)));
                            facade.base.RemoveSpecific(j,s);
                            facade.base.AddSpecific(j, target.get(i), new_date[z][1]);
                        }

                    }
                }
            }
        }else
        {
            System.out.println("Invalid values to be updated with ");
        }

        return target.size();
    }
}
