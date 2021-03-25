package eg.edu.alexu.csd.oop.jdbc;
import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class MyStatement implements Statement {
    private Batch k=new Batch();
     boolean close=false;
    int second;
    String tableName;
    logging log =new logging ();
    MyDriver x=new MyDriver();
    Time_singleton intial_time=new Time_singleton();
    long endTime = System.currentTimeMillis();
    @Override
    public void addBatch(String sql) {
        k.add(sql);
        log.help().info("Statement added Successfully ");
        endTime = System.currentTimeMillis();
    }

    @Override
    public void clearBatch() {
        k.clear();
        log.help().info("Clear batch Successfully ");
        endTime = System.currentTimeMillis();
    }

    @Override
    public void close() throws SQLException {
        close=true;
        log.help().info("Statement close Successfully ");
        endTime = System.currentTimeMillis();
    }
    @Override
    public boolean execute(String sql) throws SQLException {
        if(close==false) {
            boolean res;
            String temp = sql.toLowerCase();
            if(temp.contains("create")||temp.contains("drop")) {
                 res = x.k.executeStructureQuery(sql);
                 if(res==true)
                     log.help().info("Database/Table is created or droped Successfully ");
                 else if (res==false)
                         log.help().warning("Failed to execute");
                 endTime = System.currentTimeMillis();
                 return res;
            }
            else if(temp.contains("insert")||temp.contains("delete")||temp.contains("update")){
                int r = x.k.executeUpdateQuery(sql);
                if(r>0) {
                    log.help().info("you updated Successfully ");
                    endTime = System.currentTimeMillis();
                    return true;
                }
                else {
                    log.help().info("Failed to update");
                    endTime = System.currentTimeMillis();
                    return false;
                }
            }
            else if(temp.contains("select")){
                Object[][] r = x.k.executeQuery(sql);
                for (int i=0;i<r.length;i++) {
                    for (int j=0;j<r[0].length;j++) {
                        System.out.print(r[i][j]+ " ");
                    }
                    System.out.print("\n");
                }
                if(r.length>0) {
                    log.help().info("you selected Successfully ");
                    endTime = System.currentTimeMillis();
                    return true;
                }
                else {
                    log.help().info("Failed to select");
                    endTime = System.currentTimeMillis();
                    return false;
                }
            }
        }
        else {
        	log.help().warning("Database/Table is not created or droped because statement is closed ");
        	 endTime = System.currentTimeMillis();
        	throw new SQLException();
        }
       return false;
    }
public Fabraka executeQuery2(String sql) throws SQLException {
        String query = sql;
        Fabraka temp = null;
        if(close==false) {
            Object[][] res=x.k.executeQuery(sql);
            for (int i=0;i<res.length;i++) {
                for (int j=0;j<res[0].length;j++) {
                    System.out.print(res[i][j]+ " ");
                }
                System.out.print("\n");
            }
            String noC [] = x.k.Names(sql);
            log.help().info("Table is Select in execute Query Successfully ");
            query =  query.replace(",", "");    
            temp =  new Fabraka(res,noC,this);
        }
        else
        {
            log.help().warning("Table is not Select in execute Query because statement is closed ");
            throw new SQLException();
        }
        endTime = System.currentTimeMillis(); 
        return  temp;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        if(close==false) {
            Queue<String> queries = new LinkedList<>();
            queries = k.GET();
            int[] count = new int[queries.size()];
            int temp=queries.size();
            for (int i = 0; i < temp; i++) {
                count[i] = executeUpdate(queries.remove()); 
            }
            log.help().info("Table is Updated in execute batch Successfully ");
            endTime = System.currentTimeMillis();
            return count;
        }
        else {
        	log.help().warning("Table is not Updated in execute batch because statement is closed ");
        	endTime = System.currentTimeMillis();
        	 throw new SQLException();
        }
           
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSet temp = null;
        if(close==false) {
            Object[][] res=x.k.executeQuery(sql);
            String noC [] = x.k.Names(sql);
            log.help().info("Table is Select in execute Query Successfully ");
            temp =  new implResultSet(res,noC,this);
            endTime = System.currentTimeMillis();
        }
        else
        {
        	log.help().warning("Table is not Select in execute Query because statement is closed ");
        	endTime = System.currentTimeMillis();
            throw new SQLException();
        }
           
        return temp;
    }
    
    @Override
    public int executeUpdate(String sql) throws SQLException {
        if(close==false){
            int res=x.k.executeUpdateQuery(sql);
            if(res>0)
                log.help().info("Table is Updated Successfully ");
            else
                log.help().warning("failed to update");
           
            endTime = System.currentTimeMillis();
            return res;
        }
        else {
        	log.help().warning("Table is not Updated because statement is closed ");
        	endTime = System.currentTimeMillis();
        	throw new SQLException();
        }
            
    }
    @Override
    public Connection getConnection() throws SQLException {
        ConnectionPool k=new ConnectionPool();
        return k.CurrentConnection();
    }
    @Override
    public int getQueryTimeout() throws SQLException {
    	 long startTime=intial_time.get();
    	 int duration = (int) (endTime - startTime);
    	 System.out.println("the start is" + startTime);
    	 System.out.println("the End is" + endTime);
    	 System.out.println();
        return duration;
    }
//C:\eclipse-workspace\lab4\try
    //
   // CREATE TABLE table_name3(column_name1 varchar, column_name2 int, column_name3 varchar)
  
   // UPDATE table_name3 SET column_name1=='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'
   //
   // CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 varchar)
    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        this.second=seconds;
    }






/*********************************************************************/
    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException();
    }



    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException();
    }
    @Override
    public void cancel() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
