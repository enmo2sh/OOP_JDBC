package eg.edu.alexu.csd.oop.db;

import java.util.ArrayList;
import java.util.LinkedList;

public class whereCondition {

    public LinkedList<Integer> where_condition (String condition) {
        LinkedList<Integer> target1=new LinkedList<Integer>();
        String[] oop;
        int type=0;
        String sara=condition.toLowerCase();
        if(sara.contains("and")) {
            oop=condition.split("((?i)\\band\\b)");
            type=1;
        }
        else if (sara.contains("or"))
        {
            oop=condition.split("((?i)\\bor\\b)");
            type=0;
        }
        else if(sara.contains("not"))
        {
            String oop1=sara.replace("not","");
            // oop.length=oop1.length-1;
            oop=oop1.split(",");
            type=2;
        }
        else
        {
            oop=condition.split(",");
            type=0;
        }
        int counting = 0;
        Facade facade = new Facade();
        ArrayList<String>[] Base = facade.base.GetBase();
        ModifyQuery obj2 = new ModifyQuery();
        for(int i=1; i<Base[0].size();i++) {
            counting=0;
            for(int e=0;e<Base.length;e++) {
                for(int z=0;z< oop.length;z++) {
                    oop[z]=obj2.remove_extra_spaces(oop[z]);
                    oop[z]=oop[z].replaceAll(" ","");
                    String temp_space=oop[z];
                    String[] y = oop[z].split("(\\>)|(\\<)|(\\=)");
                    temp_space=temp_space.replace(y[0],"");
                    temp_space=temp_space.replace(y[1],"");
                    if(temp_space.equals("=")) {
                        //System.out.println(name.getTagName() +" "+ name.getTextContent());
                        if ((Base[e].get(0).equalsIgnoreCase(y[0]))&&(Base[e].get(i).equalsIgnoreCase(y[1]))) {
                            counting++;
                        }
                    }
                    else if(temp_space.equals(">")) {
                        //System.out.println(name.getTagName() +" "+ (name.getTextContent())+ " "+ Double.valueOf(y[1]));
                        if ((Base[e].get(0).equalsIgnoreCase(y[0]))&&((Double.valueOf(Base[e].get(i)))>(Double.valueOf(y[1])))) {
                            counting++;
                        }
                    }
                    else if(temp_space.equals("<"))
                    {
                        //System.out.println(name.getTagName() +" "+ name.getTextContent());
                        if ((Base[e].get(0).equalsIgnoreCase(y[0]))&&(Double.valueOf(Base[e].get(i))<Double.valueOf(y[1])))
                        {
                            counting++;
                        }
                    }
                }
            }
            if((counting==oop.length)&&(type==1)) {
                target1.add(i);
            }
            else if((counting>=1)&&(type==0)) {
                target1.add(i);
            }
            else if((type==2)&&(counting==0)) {
                target1.add(i);
            }
        }
        return target1;
    }
}
