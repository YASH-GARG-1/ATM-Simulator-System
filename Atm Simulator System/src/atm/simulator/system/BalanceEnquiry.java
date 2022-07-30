package atm.simulator.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
//import java.util.*;

public class BalanceEnquiry extends JFrame implements ActionListener{
    
    JButton back;
    String cardNumber;
    
    BalanceEnquiry(String cardNumber)
    {
        this.cardNumber = cardNumber;
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        back = new JButton("Back");
        back.setBounds(355, 520, 150, 30);
        back.addActionListener(this);
        image.add(back);
        
        Conn c = new Conn();
        int balance  = 0;
        try
        {
            ResultSet rs = c.s.executeQuery("select * from balance where cardnumber = '"+cardNumber+"'");
            while(rs.next())
            {
                balance = Integer.parseInt(rs.getString("amount"));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        JLabel text = new JLabel("Your Curent Account Balance is Rs. " + balance);
        text.setForeground(Color.WHITE);
        text.setBounds(170, 300, 400, 30);
        image.add(text);
        
        setSize(900,900);
        setLocation(300, 0);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        setVisible(false);
        new Transactions(cardNumber);
    }
    
    public static void main(String args[])
    {
        new BalanceEnquiry("");
    }
}
