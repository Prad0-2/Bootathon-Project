/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bootathon;

import Bootathon.database.DBOperations;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 *
 * @author rahul
 */
public class DeleteRewind {
    DeleteRewind(int id)
    {
        // frame creating
        JFrame f = new JFrame();           
        f.setBounds(500,200,500,700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("View Form");
        f.setResizable(true); 
        
        //container creating
        Container c = f.getContentPane();
        c.setLayout(new GridLayout(5,1));
        
        //title panel
        JPanel summa1 = new JPanel();
        summa1.setLayout(new FlowLayout(FlowLayout.CENTER,20,30));
        JLabel ll= new JLabel("Delete details");
        ll.setFont(new Font("Comic sans MS",Font.BOLD,35));  
        summa1.add(ll);
        c.add(summa1);
        
        //panel1 creating
        JPanel panel1 = new JPanel();
        c.add(panel1);
        JComboBox cb = new JComboBox();          
        cb.addItem("Select HP");
            //adding values to combobox
            try{
                    Connection conn = DBOperations.getConn();
                    Statement st=conn.createStatement();
                    ResultSet rs=st.executeQuery("Select hp from rewinddet where emprid="+String.valueOf(id));
                    while(rs.next())
                    {
                        cb.addItem(rs.getString("hp"));
                    }
                    conn.close();
            }
            catch(Exception ee)
            {
            }
        cb.setFont(new Font("arial",Font.PLAIN,20));        
        JLabel l1 = new JLabel("Select HP  : ");
        panel1.add(l1);
        panel1.add(cb);
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        l1.setFont(new Font("Comic sans MS",Font.BOLD,25));
        
        //panel2 creating
        JPanel panel2 = new JPanel();
        c.add(panel2);
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        JLabel l2 = new JLabel("     Horse Power :");        
        panel2.add(l2);
        l2.setFont(new Font("Comic sans MS",Font.BOLD,25));
        JTextField t = new JTextField(10);
        t.setEditable(false);
        t.setFont(new Font("arial",Font.BOLD,20));
        panel2.add(t);
        
        //panel3 creating
        
        JPanel panel3 = new JPanel();
        c.add(panel3);
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        JLabel l3 = new JLabel("Rewinding Details"); 
        l3.setFont(new Font("Comic sans MS",Font.BOLD,25));
        
        JTextArea ta = new JTextArea(4,20);
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        
        panel3.add(sp);
        ta.setFont(new Font("arial",Font.PLAIN,20));
        
        JPanel panel5 = new JPanel();
        c.add(panel5);
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER,30,30));
        JButton back = new JButton("\u2190"+"  BACK");
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new MainFrame();
            }
        });
        back.setFont(new Font("",Font.BOLD,20));
        JButton delete = new JButton("Delete");
        delete.setFont(new Font("",Font.BOLD,20));
        panel5.add(delete);
        panel5.add(back);
        
         cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item=(String)cb.getSelectedItem();
                try{
                    Connection conn = DBOperations.getConn();
                    PreparedStatement st=conn.prepareStatement("Select hp, details from rewinddet where hp=? and emprid=?");
                    st.setString(1, item);
                    st.setInt(2, id);
                    ResultSet rs=st.executeQuery();
                    rs.next();
                    t.setText(rs.getString("hp"));
                    ta.setText(rs.getString("details"));
                    conn.close();
                }
                catch(Exception ee)
                {
                    
                }
 
            }
        });
         
         delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(t.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(f, "Please select which detail to delete first!");
                }
                else
                {
                    int a=JOptionPane.showConfirmDialog(f,"Are you sure to Delete?");  
                if(a==JOptionPane.YES_OPTION){  
                try
                {
                    Connection conn = DBOperations.getConn();
                    PreparedStatement st=conn.prepareStatement("delete from rewinddet where emprid=? and hp=?");
                    st.setInt(1, id);
                    st.setString(2, t.getText());
                    st.executeUpdate();
                    conn.close();
                }
                catch(Exception ee)
                {
                    System.out.println(ee);
                }
                JOptionPane.showMessageDialog(f,"Successfully Deleted.","Alert",JOptionPane.WARNING_MESSAGE);
                f.dispose();
                new DeleteRewind(id);
            }}}
        });
        
        f.setVisible(true);
    }
    public static void main(String[] args) {
        new LoginPage();
    }
}