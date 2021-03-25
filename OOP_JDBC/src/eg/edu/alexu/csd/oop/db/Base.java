package eg.edu.alexu.csd.oop.db;
import java.util.ArrayList;

public class Base {
    private static ArrayList<String>[] base;

    public void openBase(int n){
        base = new ArrayList[n];
        for(int i=0;i<n;i++)
            base[i]=new ArrayList<>();
    }

    public void AddToBase(int i, String element){
        base[i].add(element);
    }

    public void AddSpecific(int j, int i, String element){
        base[j].add(i,element);
    }

    public ArrayList<String>[] GetBase (){
        return base;
    }

    public void RemoveFromBase(int i, String element){
        base[i].remove(element);
    }

    public void RemoveSpecific(int j , int s){
        base[j].remove(s);
    }

    public void clear(int i){
        base[i].clear();
    }
}
