
package atm.simulator.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;

public class Deposit extends JFrame implements ActionListener{

    JTextField amount;
    JButton deposit, back;
    String cardNumber;
    
    Deposit(String cardNumber)
    {
        
        this.cardNumber = cardNumber;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);
        
        JLabel text = new JLabel("Enter the amount you want to deposit:");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(170, 300, 400, 20);
        image.add(text);
        
        amount = new JTextField();
        amount.setFont(new Font("Raleway", Font.BOLD, 22));
        amount.setBounds(170, 350, 320, 25);
        image.add(amount);
        
        deposit = new JButton("Deposit");
        deposit.setBounds(355, 485, 150, 30);
        deposit.addActionListener(this);
        image.add(deposit);
        
        back = new JButton("Back");
        back.setBounds(355, 520, 150, 30);
        back.addActionListener(this);
        image.add(back);
        
        setSize(900,900);
        setLocation(300, 0);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == deposit)
        {
            String number = amount.getText();
            Date date = new Date();
            if(number.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to deposit");
            }
            else
            {
                try
                {
                    Conn conn = new Conn();
                    String query = "insert into bank values('"+cardNumber+"', '"+ date +"', 'Deposit', '"+number+"')";
                    conn.s.executeUpdate(query);
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery("select * from balance where cardnumber = '"+cardNumber+"'");
                    int cashAmount = 0;
                    while(rs.next())
                    {
                        cashAmount = Integer.parseInt(rs.getString("amount"));
                    }
                    cashAmount += Integer.parseInt(number);
                    String query2 = "update balance set amount = '"+cashAmount+"' where cardnumber = '"+cardNumber+"'";
                    c.s.executeUpdate(query2);
                    JOptionPane.showMessageDialog(null, "Rs "+ number + " Deposited Successfully");
                    setVisible(false);
                    new Transactions(cardNumber);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
        else if(ae.getSource() == back)
        {
            setVisible(false);
            new Transactions(cardNumber);
        }
    }
    
    public static void main(String args[])
    {
        new Deposit("");
    }
}
