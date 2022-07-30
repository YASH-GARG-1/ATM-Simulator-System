package atm.simulator.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// We extended the JFrame class to create the frame.
public class Login extends JFrame implements ActionListener{

    JButton login, signup, clear;
    JTextField cardTextField;
    JPasswordField pinTextField; // so that no one can see the password.

    Login()
    {
        // add(new JLabel(new ImageIcon("logo.jpg"))); // another method of adding the image.

        setLayout(null); // we use this command as we want to define our own layout. as
        // java by default has border layout which has the center property. so when we add a image
        // in out layout it will always be present in the centre if we set the bounds.

        setTitle("Automatic Teller Machine"); // Setting the title of the Frame.
        
        ImageIcon img1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        // For using the logo.jpg image in our frame.

        Image img2 = img1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        // For scalling our logo.png image.
        ImageIcon img3 = new ImageIcon(img2); // Image cannot be directly used with label so we used.
        // Another ImageIcon
        
        JLabel label = new JLabel(img3); // we have to tke help of label to insert our image in frame.
        label.setBounds(70,10,100,100); // for creating our custom layout.
        add(label); // finally adding our image to the frame.
        
        JLabel text = new JLabel("Welcome To ATM");

        text.setFont(new Font("Osward", Font.BOLD, 38));// for changing the font of the text.

        text.setBounds(200,40,400,40); //as we set the default layout as null,
        // we have set the location of the label where we want to display it. If we do not add the
        // location then even if we add the label to the frame, it will not be displayd on the frame.
        add(text);

        // Creating the card Number label.
        JLabel cardNum = new JLabel("Card No : ");
        cardNum.setFont(new Font("Raleway", Font.BOLD, 28));
        cardNum.setBounds(120,150,150,30);
        add(cardNum);

        // Setting up the text field for taking in the card no.
        cardTextField = new JTextField();
        cardTextField.setBounds(300, 150, 230, 30);
        cardTextField.setFont(new Font("Arial", Font.BOLD, 14));
        add(cardTextField);

        // Creating the pin Label.
        JLabel pin = new JLabel("PIN : ");
        pin.setFont(new Font("Raleway", Font.BOLD, 28));
        pin.setBounds(120,220,150,30);
        add(pin);

        // Setting up the text field for taking in the pin no.
        pinTextField = new JPasswordField();
        pinTextField.setBounds(300, 220, 230, 30);
        pinTextField.setFont(new Font("Arial", Font.BOLD, 14));
        add(pinTextField);

        // Creating the login Button.
        login = new JButton("SIGN IN");
        login.setBounds(300,300,100,30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this); // when we click the login button, an action is performed.
        add(login);

        // Clear the form button.
        clear = new JButton("CLEAR");
        clear.setBounds(430,300,100,30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);

        // Creating the sigUp button.
        signup = new JButton("SIGN UP");
        signup.setBounds(300,350,230,30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);

        // setting the backgroung color of frame as white.
        getContentPane().setBackground(Color.WHITE);

        setSize(800, 480); // defining the dimensions of the frame.
        setVisible(true); // making the frame visible to the user.
        setLocation(300,100); // setting the location of the frame as 300 pixels from the 
        // left and 100 pixels from the top.
    }

    // this is an abstract method that is needed to be overridden.
    // when we click an button, what action is to be performed is defined in this function.
    public void actionPerformed(ActionEvent obj)
    {
        if(obj.getSource() == clear)
        {
            // if the button pressed is clear. simply set the test of card no. and pin as "";
            cardTextField.setText("");
            pinTextField.setText("");
        }
        else if(obj.getSource() == login)
        {
            Conn conn = new Conn();
            String cardNumber = cardTextField.getText();
            String pinNumber = pinTextField.getText();
            String query = "select * from login where cardnumber = '" + cardNumber + "' and pin = '" + pinNumber+"'";
            try
            {
                ResultSet rs = conn.s.executeQuery(query);
                if(rs.next())
                {
                    setVisible(false);
                    new Transactions(cardNumber);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Incorrect Card No. or Pin");
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else if(obj.getSource() == signup)
        {
            setVisible(false);
//            new SignupOne().setVisible(true);
            new SignupOne();
        }
    }
    public static void main(String args[])
    {  
        new Login();
    }
}
