package eg.edu.alexu.csd.oop.jdbc;

import java.util.LinkedList;
import java.util.Queue;

public class Batch {
    private static Queue<String> queries=new LinkedList<>();

    public static void setQueries(Queue<String> queries) {
        Batch.queries = queries;
    }
    public void add(String sql) {
        queries.add(sql);
    }

    public void clear() {
        queries.clear();
    }
    public Queue GET(){
        return queries;
    }
}
