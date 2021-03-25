package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.awt.SystemColor.info;

public class ST implements UI {
    Connection conn;
    Scanner n=new Scanner(System.in);
    String query="";
    Statement st;
    Time_singleton intial_time=new Time_singleton();
    ST(Connection conn){
       this.conn=conn;
        try {
            st=conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void print() {
        System.out.println("1.add to Batch \n2.clear the Batch \n3.execute Batch " +
                "\n4.create/drop DB or Table \n5.Select the table" +
                " \n6.set sleeping time \n7.know the query timeout \n8.close");
    }

    @Override
    public void call(int ch) throws SQLException {
        if(ch==1){
            System.out.println("enter the query");
            query=n.nextLine();
            intial_time.set(System.currentTimeMillis());
            st.addBatch(query);
            
        }
        else if(ch==2)
            st.clearBatch();

        else if(ch==3){
        	intial_time.set(System.currentTimeMillis());
            int[]k=st.executeBatch();
            for(int i=0;i<k.length;i++)
                System.out.println(k[i]);
        }
        else if(ch==4){
            System.out.println("enter the query");
             query=n.nextLine();
             intial_time.set(System.currentTimeMillis());
            boolean k=st.execute(query);
            if(k==true)
                System.out.println("succeeded");
            else
                System.out.println("Failed");
        }
        else if(ch==5){
            System.out.println("enter the query");
             query=n.nextLine();
             intial_time.set(System.currentTimeMillis());
            st.executeQuery(query);
        }
        else if(ch==6){
            System.out.println("enter Sleep time in mille Second");
             long q=n.nextLong();
             try {
				TimeUnit.SECONDS.sleep(q);
				intial_time.set(System.currentTimeMillis());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
           // st.executeQuery(query);
        }
        else if(ch==7){
           // System.out.println("enter the query");
           // query=n.nextLine(); 
        	
            int k=st.getQueryTimeout();
            System.out.println(k+"Mille Seconds");
        }
        else if(ch==8){
            st.close();
        }
    }
    public  String GetQuery(){
        return query;
    }
}
