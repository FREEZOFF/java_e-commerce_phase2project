import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginUI
{
	public static void main(String args[])
	{
		JFrame frame = new JFrame("Login");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		JLabel label1 = new JLabel("Email: ");
		label1.setBounds(20, 20, 100, 30);
		
		JLabel label2 = new JLabel("Password: ");
		label2.setBounds(20, 60, 100, 30);
		
		JTextField textField1 = new JTextField();
		textField1.setBounds(140, 20, 100, 30);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(140, 60, 100, 30);

		JButton button1 = new JButton("Login");
		button1.setBounds(20, 100, 100, 30);
		
		JButton button2 = new JButton("Signup");
		button2.setBounds(140, 100, 100, 30);

		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String email = textField1.getText();
				String password = new String(passwordField.getPassword());
				

				if(validateLogin(email, password))
				{
					JOptionPane.showMessageDialog(frame, "Login successful");
					
					if(email.equals("uar@gmail.com") && password.equals("admin_uar"))
					{
						JOptionPane.showMessageDialog(frame, "Welcome Admin!");
						frame.dispose();
						AdminUI.main(null);
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Welcome Customer!");
						frame.dispose();
						CustomerUI.main(null);
					}		
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Invalid inputs");
				}
		
				

			}
		});

		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
				SignupUI.main(null);
			}
		});

		frame.add(label1);
		frame.add(label2);
		frame.add(textField1);
		frame.add(passwordField);
		frame.add(button1);
		frame.add(button2);
		
		frame.setVisible(true);
	}

	
	public static boolean validateLogin(String email, String password)
	{
		String url = "jdbc:mysql://localhost:3306/ecommerce";
		String username = "root";
		String dbPassword = "parth";
		String query = "SELECT * FROM user_info WHERE email =? AND password=?";		
	
		try
		{
			Connection c = DriverManager.getConnection(url, username, dbPassword);
			PreparedStatement stmt = c.prepareStatement(query);
				
			stmt.setString(1, email);
			stmt.setString(2, password);
				
			ResultSet rs = stmt.executeQuery();	
			
			if(rs.next())
			{
				return true;
			}
			c.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
		
	}
}
