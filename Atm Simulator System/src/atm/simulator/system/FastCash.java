
package atm.simulator.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener{
    
    JButton deposit, withdrawl, miniStatement, pinChange, fastCash, balanceEnquiry, exit;
    String cardNumber;
    
    FastCash(String cardNumber)
    {
        this.cardNumber = cardNumber;
        setLayout(null);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        JLabel text = new JLabel("SELECT WITHDRAWL AMOUNT");
        text.setBounds(210, 300, 700, 35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Raleway", Font.BOLD, 16));
        image.add(text);
        
        deposit = new JButton("Rs 100");
        deposit.setBounds(170, 415, 150, 30);
        deposit.addActionListener(this);
        image.add(deposit);
        
        withdrawl = new JButton("Rs 500");
        withdrawl.setBounds(355, 415, 150, 30);
        withdrawl.addActionListener(this);
        image.add(withdrawl);
        
        fastCash = new JButton("Rs 1000");
        fastCash.setBounds(170, 450, 150, 30);
        fastCash.addActionListener(this);
        image.add(fastCash);
        
        miniStatement = new JButton("Rs 2000");
        miniStatement.setBounds(355, 450, 150, 30);
        miniStatement.addActionListener(this);
        image.add(miniStatement);
        
        pinChange = new JButton("Rs 5000");
        pinChange.setBounds(170, 485, 150, 30);
        pinChange.addActionListener(this);
        image.add(pinChange);
        
        balanceEnquiry = new JButton("Rs 10000");
        balanceEnquiry.setBounds(355, 485, 150, 30);
        balanceEnquiry.addActionListener(this);
        image.add(balanceEnquiry);
        
        exit = new JButton("BACK");
        exit.setBounds(355, 520, 150, 30);
        exit.addActionListener(this);
        image.add(exit);
        
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == exit)
        {
            setVisible(false);            
            new Transactions(cardNumber);
        }
        else
        {
            String amount = ((JButton)ae.getSource()).getText().substring(3);    
            try
            {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from balance where cardnumber = '"+cardNumber+"'");
                int balance  = 0;
                while(rs.next())
                {
                    balance = Integer.parseInt(rs.getString("amount"));
                }
                
                if(ae.getSource() != exit && balance < Integer.parseInt(amount))
                {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }
                
                Date date = new Date();
//                String query = "insert into bank values('"+pinNumber +"', '"+date+"', 'Withdrawl', '"+amount+"')";
//                c.s.executeQuery(query);
//                JOptionPane.showMessageDialog(null, "Rs "+amount+" Debited Successfully");
                Conn conn = new Conn();
                String query = "insert into bank values('"+cardNumber+"', '"+ date +"', 'Withdrawl', '"+amount+"')";
                conn.s.executeUpdate(query);
                balance -= Integer.parseInt(amount);
                query = "update balance set amount = '"+balance+"' where cardnumber = '"+cardNumber+"'";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs "+ amount + " Withdraw Successfully");
                
                setVisible(false);
                new Transactions(cardNumber);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
    
    public static void main(String args[])
    {
        new FastCash("");
    }
}
