package eg.edu.alexu.csd.oop.db;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;

public class GUI{

    private JFrame frame;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 784, 502);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setForeground(Color.BLACK);
        Image img=new ImageIcon(System.getProperty("user.dir")+ File.separator+"SQL.png").getImage();
        lblNewLabel.setIcon(new ImageIcon(img));
        lblNewLabel.setBounds(619, 0, 151, 450);
        frame.getContentPane().add(lblNewLabel);

        Factory k=new Factory();
        JButton btnNewButton = new JButton("Submit");
        btnNewButton.setBounds(507, 368, 102, 29);
        btnNewButton.setBackground(Color.decode("#8b0000"));
        textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!textField.getText().equals("")) {
                    btnNewButton.setEnabled(true);
                    btnNewButton.setToolTipText("BE WAIT THE RESULT ");
                }
                else
                    btnNewButton.setEnabled(false);
            }
        });
        textField.setBounds(23, 336, 468, 102);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        frame.getContentPane().setLayout(null);
        DefaultStyledDocument document = new DefaultStyledDocument();
        JTextPane textPane = new JTextPane(document);
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
        textPane.setEditable(false);

        JScrollPane scrollableTextPane = new JScrollPane(textPane);
        scrollableTextPane.setBounds(23, 5, 468, 321);

        scrollableTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setBackground(Color.BLACK);

        frame.getContentPane().add(scrollableTextPane);


        btnNewButton.setEnabled(false);
        btnNewButton.setToolTipText("YOU SHOULD INSERT QUERY");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Boolean q=false;
                String query=textField.getText();
                try {
                    q = k.Object(query).validation(query);
                }catch(Exception e){ }

                if(q) {
                    try {
                        try {
                            StyleConstants.setForeground(style, Color.YELLOW);
                            document.insertString(document.getLength(), ">SqL :  " + query + "\n", style);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        textField.setText("");
                        btnNewButton.setEnabled(false);
                        Object res=k.Object(query).calls(query);
                        if (res instanceof Boolean){
                            if((Boolean)res){
                                StyleConstants.setForeground(style, Color.BLUE);
                                document.insertString(document.getLength(), "    Succeeded "+"\n", style);
                            }
                            else{
                                StyleConstants.setForeground(style, Color.RED);
                                document.insertString(document.getLength(), "    FAILED"+"\n", style);
                            }
                        }
                        else if(res instanceof String){
                            StyleConstants.setForeground(style, Color.BLUE);
                            document.insertString(document.getLength(), "   DataBase is created in "+res +"\n", style);
                        }
                        else if(res instanceof Integer ) {
                            if ((Integer) res > 0) {
                                StyleConstants.setForeground(style, Color.BLUE);
                                document.insertString(document.getLength(), "    Done updated in " + res + "Rows" + "\n", style);
                            } else {
                                StyleConstants.setForeground(style, Color.RED);
                                document.insertString(document.getLength(), "    Failed to update" + "\n", style);
                            }
                        }
                        else if(res instanceof Object[][]){
                            StyleConstants.setForeground(style, Color.BLUE);
                            Object[][] result= (Object[][]) res;
                            for(int i=0;i<result.length;i++){
                                for(int j=0;j<result[0].length;j++){
                                    document.insertString(document.getLength(), "    "+result[i][j], style);
                                }
                                document.insertString(document.getLength(), "\n", style);
                            }
                        }
                    } catch (SQLException | BadLocationException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"The query is wrong");
                }
            }
        });
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
        frame.getContentPane().add(btnNewButton);
        frame.setResizable(false);
        frame.setTitle("SQL COMMAND LINE");
        frame.setVisible(true);
    }
}

