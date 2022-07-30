
package atm.simulator.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;

public class Withdrawl extends JFrame implements ActionListener{

    JTextField amount;
    JButton withdraw, back;
    String cardNumber;
    
    Withdrawl(String cardNumber)
    {
        
        this.cardNumber = cardNumber;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);
        
        JLabel text = new JLabel("Enter the amount you want to withdraw:");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(170, 300, 400, 20);
        image.add(text);
        
        amount = new JTextField();
        amount.setFont(new Font("Raleway", Font.BOLD, 22));
        amount.setBounds(170, 350, 320, 25);
        image.add(amount);
        
        withdraw = new JButton("Withdrawal");
        withdraw.setBounds(355, 485, 150, 30);
        withdraw.addActionListener(this);
        image.add(withdraw);
        
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
        if(ae.getSource() == withdraw)
        {
            String number = amount.getText();
            Date date = new Date();
            if(number.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to withdraw");
            }
            else
            {
                try
                {
                    Conn conn = new Conn();
                    ResultSet rs = conn.s.executeQuery("select * from balance where cardNumber = '"+cardNumber+"'");
                    int cashAmount = 0;
                    while(rs.next())
                    {
                        cashAmount = Integer.parseInt(rs.getString("amount"));
                    }
                    if(cashAmount < Integer.parseInt(number))
                    {
                        JOptionPane.showMessageDialog(null, "Low Balance!!!");
                        setVisible(false);
                        new Transactions(cardNumber);
                    }
                    else
                    {  
                        cashAmount -= Integer.parseInt(number);
                        conn.s.executeUpdate("update balance set amount = '"+cashAmount+"' where cardnumber = '"+cardNumber+"'");
                        String query = "insert into bank values('"+cardNumber+"', '"+ date +"', 'Withdrawl', '"+number+"')";
                        conn.s.executeUpdate(query);
                        JOptionPane.showMessageDialog(null, "Rs "+ number + " Withdraw Successfully");
                        setVisible(false);
                        new Transactions(cardNumber);
                    }
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
        new Withdrawl("");
    }
}
