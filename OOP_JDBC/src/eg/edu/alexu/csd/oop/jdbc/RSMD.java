package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class RSMD implements UI {
    Connection conn;
    Scanner n=new Scanner(System.in);
    String query;
    ResultSetMetaData meta;
    RSMD(Connection conn, String query){
        this.conn=conn;
        this.query=query;
        try {
            meta=conn.createStatement().executeQuery(query).getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void print() {
        System.out.println("1.getColumnCount \n2.getColumnName \n3.getColumnType \n4.getTableName \n5.getColumnLabel");
    }

    @Override
    public void call(int ch) throws SQLException {
        if(ch==1){
            int res=meta.getColumnCount();
            System.out.println("the number of columns: "+res);
        }
        else if(ch==2){
            System.out.println("enter the column number");
            int index=n.nextInt();
            String res=meta.getColumnName(index);
            System.out.println("the name of the coulmn: "+res);
        }
        else if(ch==3){
            System.out.println("enter the column number");
            int index=n.nextInt();
            int res=meta.getColumnType(index);
            if(res==4)
                System.out.println("INTEGER");
            else
                System.out.println("VARCHAR");
        }
        else if(ch==4){
            System.out.println("enter the column number");
            int index=n.nextInt();
            String res=meta.getTableName(index);
            System.out.println("the table name is: "+res);
        }
        else if(ch==5){
            System.out.println("enter the column number");
            int index=n.nextInt();
            String res=meta.getColumnLabel(index);
            System.out.println("the new name of the column is: "+res);
        }
    }
}
