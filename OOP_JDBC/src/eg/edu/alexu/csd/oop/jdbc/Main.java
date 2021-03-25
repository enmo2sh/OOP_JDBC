package eg.edu.alexu.csd.oop.jdbc;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean x = true;
        Scanner n = new Scanner(System.in);
        String RSQ="";
        String RSMDQ="";
        System.out.print("enter the path:");
        String path=n.nextLine();
        Driver d = new MyDriver();
        Properties info = new Properties();
        File dbDir = new File(path);
        String ch="";
        if(!path.equals(""))
             info.put("path", dbDir.getAbsoluteFile());
        while (x) {
            Connection conn=null;
            try {
                conn = d.connect("jdbc:xmldb://localhost", info);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("1.Statement \n2.RESULTSEt \n3.ResultSetMetaData \n4.exit");
            int b = n.nextInt();
            int choice;
            if (b == 1) {
                ST k = new ST(conn);
                k.print();
                 choice = n.nextInt();
                 if (choice==5){
                     RSQ=k.GetQuery();
                     RSMDQ=k.GetQuery();
                 }
                try {
                    k.call(choice);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ch="st";
            }
            else if (b == 2) {
                if(RSQ.equals("")) {
                    System.out.println("enter the query");
                    while(RSQ.equals("")) {
                        RSQ = n.nextLine();
                    }
                }
                RS k = null;
                try {
                    k = new RS(conn, RSQ);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                k.print();
                    choice = n.nextInt();
                    try {
                        k.call(choice);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ch="rs";
            }
            else if (b == 3) {
                if(RSMDQ.equals("")) {
                    System.out.println("enter the query");
                    while(RSMDQ.equals("")) {
                        RSMDQ = n.nextLine();
                    }
                }
                RSMD k = new RSMD(conn,RSMDQ);
                k.print();
                choice = n.nextInt();
                try {
                    k.call(choice);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ch="rsmd";
            }
            else if(b==4)
                x=false;
            else
                System.out.println("wrong number!!!");
        }
    }
}
