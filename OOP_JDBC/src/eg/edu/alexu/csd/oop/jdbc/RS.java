package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class RS implements UI {
    Scanner n=new Scanner(System.in);
    Connection conn;
    String query;
    Statement stmt;
    RS (Connection conn, String query) throws SQLException{
        this.conn=conn;
        this.query=query;
        this.stmt= conn.createStatement();
    }
    
    public void print() {
        System.out.println("n1.Set the cursor after last row \n2.Set the cursor before first row" +
                "\n3.Set the cursor at the last row \n4.Set the cursor at the first row" +
                "\n5.Set the position of the cursor \n6.Move to the next row" +
                "\n7.Move to the previous row \n8.Get the index of a column" +
                "\n9.Get an integer value by column's index \n10.Get an integer value by column's name" +
                "\n11.Get a String value by column's index \n12.Get a String value by column's name" +
                "\n13.Get a Object by column's index \n14.Check if the cursor is after last row" +
                "\n15.Check if the cursor is before first row \n16.Check if the result set is closed" +
                "\n17.Check if the cursor is at the first row \n18.Check if the cursor is at the last row" +
                "\n19.Close"+
                "\n20.Get meta data \n");
    }

    @Override
    public void call(int ch) throws SQLException {
        if(ch==1)
        ((MyStatement)stmt).executeQuery2(query).afterLast();

        else if(ch==2){
            ((MyStatement)stmt).executeQuery2(query).beforeFirst();
        }
        else if(ch==3){
            boolean check=((MyStatement)stmt).executeQuery2(query).last();
            if (check==false){
                System.out.println("There are no rows in the result set");
            }else {
                System.out.println("Done");
            }
        }
        else if(ch==4){
            boolean check=((MyStatement)stmt).executeQuery2(query).first();
            if (check==false){
                System.out.println("There are no rows in the result set");
            }else {
                System.out.println("Done");
            }
        }
        else if(ch==5){
            System.out.println("Enter the row \n");
            int row=n.nextInt();
            boolean check=((MyStatement)stmt).executeQuery2(query).absolute(row);
            if (check==false){
                System.out.println("The Cursor Is Out Of Length Of The result Set");
            }else {
                System.out.println("Done");
            }
        }
        else if(ch==6){
           
            boolean check=((MyStatement)stmt).executeQuery2(query).next();
            if (check==false){
                System.out.println("There are no more rows in the result set");
            }else {
                System.out.println("Done");
            }
        }else if(ch==7){
            boolean check= ((MyStatement)stmt).executeQuery2(query).previous();
            if (check==false){
                System.out.println("The cursor is positioned before the first row");
            }else {
                System.out.println("Done");
            }
        }
        else if(ch==8){
            System.out.println("Enter column Name \n");
            String columnLabel=n.next();
            int index=((MyStatement)stmt).executeQuery2(query).findColumn(columnLabel);
            System.out.println("The index of the column = " +index);
        }
        else if(ch==9){
            System.out.println("Enter column's index \n");
            int columnIndex=n.nextInt();
            int value=((MyStatement)stmt).executeQuery2(query).getInt(columnIndex);
            System.out.println("Value = " +value);
        }
        else if(ch==10){
            System.out.println("Enter column's name \n");
            String columnLabel=n.next();
            int value=((MyStatement)stmt).executeQuery2(query).getInt(columnLabel);
            System.out.println("Value = " +value);
        }else if(ch==11){
            System.out.println("Enter column's index \n");
            int columnIndex=n.nextInt();
            String value = ((MyStatement)stmt).executeQuery2(query).getString(columnIndex);
            System.out.println("Value = " +value);
        }else if(ch==12){
            System.out.println("Enter column's name \n");
            String columnLabel=n.next();
            String value=((MyStatement)stmt).executeQuery2(query).getString(columnLabel);
            System.out.println("Value = " +value);
        }
        else if(ch==13){
            System.out.println("Enter column's index \n");
            int columnIndex=n.nextInt();
            Object value =((MyStatement)stmt).executeQuery2(query).getObject(columnIndex);
            System.out.println("Value = " +value);
        }
        else if(ch==14){
            boolean check =((MyStatement)stmt).executeQuery2(query).isAfterLast();
            if (check==false){
                System.out.println("No");
            }else {
                System.out.println("Yes");
            }
        }
        else if(ch==15){
            boolean check =((MyStatement)stmt).executeQuery2(query).isBeforeFirst();
            if (check==false){
                System.out.println("No");
            }else {
                System.out.println("Yes");
            }
        }else if(ch==16){
            boolean check=((MyStatement)stmt).executeQuery2(query).isClosed();
            if (check==false){
                System.out.println("No");
            }else {
                System.out.println("Yes");
            }
        }
        else if(ch==17){
            boolean check= ((MyStatement)stmt).executeQuery2(query).isFirst();
            if (check==false){
                System.out.println("No");
            }else {
                System.out.println("Yes");
            }
        }else if(ch==18){
            boolean check= ((MyStatement)stmt).executeQuery2(query).isLast();
            if (check==false){
                System.out.println("No");
            }else {
                System.out.println("Yes");
            }
        }
        else if(ch==19){
            ((MyStatement)stmt).executeQuery2(query).close();
        }else if(ch==20){
            ((MyStatement)stmt).executeQuery2(query).getMetaData();
        }

    }
}