package atm.simulator.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MiniStatement extends JFrame{
    
    MiniStatement(String cardNumber)
    {
        setLayout(null);
        setTitle("Mini Statement");
        
        JLabel mini = new JLabel();
        mini.setBounds(20, 140, 400, 200);
        add(mini);
        
        JLabel bank = new JLabel("Indian Bank");
        bank.setBounds(150, 20, 100, 20);
        add(bank);
        
        JLabel card = new JLabel();
        card.setBounds(20, 80, 300, 20);
        add(card);
        
        JLabel balance = new JLabel();
        balance.setBounds(20, 280, 250, 100); // changes to be made here.
        add(balance);
        
        try
        {
            int amount = 0;
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from balance where cardnumber = '"+cardNumber+"'");
            while(rs.next())
            {
                card.setText("Card Number: "+rs.getString("cardNumber").substring(0,4) + "XXXXXXXX" + rs.getString("cardNumber").substring(12));
                amount = Integer.parseInt(rs.getString("amount"));
            }
            balance.setText("Your Current account balance is Rs. "+ amount);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        try
        {
            Conn con = new Conn();
            ResultSet rs = con.s.executeQuery("Select * from bank where cardnumber = '"+cardNumber+"'");
            while(rs.next())
            {
                mini.setText(mini.getText() + "<html>" +rs.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount")+"<br><br><html>");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        setSize(400, 400);
        setLocation(300, 300);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
    
    public static void main(String args[])
    {
        new MiniStatement("");
    }
}
