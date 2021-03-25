package eg.edu.alexu.csd.oop.db;

public class ModifyQuery {

    public String [][] splitting(String s,String t){
        s=s.replace("(","");
        s=s.replace(")","");
        t=t.replace("(","");
        t=t.replace(")","");
        String [] names1=s.split(",");
        String [] names2=t.split(",");
        String[][] result=new String[names1.length][2];
        for(int x=0;x<names1.length;x++){
            names1[x]=remove_extra_spaces(names1[x]);
            names2[x]=remove_extra_spaces(names2[x]);
            result[x][0]=names1[x];
            result[x][1]=names2[x];
        }
        return result;
    }

    public String remove_extra_spaces (String o){
        int d=0;
        if(o.charAt(0)!=' ')
            d=0;
        else {
            while (d < o.length()) {
                if (o.charAt(d) == ' ')
                    d++;
                else
                    break;
            }
        }
        System.out.println(d);
        String n="";
        for(int i=d;i<o.length()-1;i++){
            if((o.charAt(i)==' ')&&(o.charAt(i+1)==' '))
                n=n;
            else
                n=n+o.charAt(i);
        }
        if(o.charAt(o.length()-1)!=' ')
            n=n+o.charAt(o.length()-1);
        System.out.println(n);
        return n;

    }
}
