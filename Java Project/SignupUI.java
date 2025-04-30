import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupUI
{
	public static void main(String args[])
	{
		JFrame frame = new JFrame("SignUp");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		JLabel label1 = new JLabel("Name: ");
		label1.setBounds(20, 20, 100, 30);
		
		JLabel label2 = new JLabel("Email: ");
		label2.setBounds(20, 60, 100, 30);
		
		JLabel label3 = new JLabel("Password: ");
		label3.setBounds(20, 100, 100, 30);
		
		JLabel label4 = new JLabel("Confirm Password: ");
		label4.setBounds(20, 140, 120, 30);
		
		JTextField textField1 = new JTextField();
		textField1.setBounds(160, 20, 100, 30);
		
		JTextField textField2 = new JTextField();
		textField2.setBounds(160, 60, 100, 30);
		
		JPasswordField passwordField1 = new JPasswordField();
		passwordField1.setBounds(160, 100, 100, 30);
		
		JPasswordField passwordField2 = new JPasswordField();
		passwordField2.setBounds(160, 140, 100, 30);

		JButton button1 = new JButton("Signup");
		button1.setBounds(20, 200, 100, 30);
		
		JButton button2 = new JButton("Back To Login");
		button2.setBounds(140, 200, 150, 30);
	
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String name = textField1.getText();
				String email = textField2.getText();
				String password = new String(passwordField1.getPassword());
				String confirmPassword = new String(passwordField2.getPassword());
			
				if(password.equals(confirmPassword))
				{
					if(registerUser(name, email,password))
					{
						JOptionPane.showMessageDialog(frame, "Registration Successful");
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Error");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Incorrect Password");
				}
			}
		});
		
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
				LoginUI.main(null);
			}
		});

		frame.add(label1);
		frame.add(label2);
		frame.add(label3);
		frame.add(label4);
		frame.add(textField1);
		frame.add(textField2);
		frame.add(passwordField1);
		frame.add(passwordField2);
		frame.add(button1);
		frame.add(button2);		

		frame.setVisible(true);
	}

	public static boolean registerUser(String name, String email, String password)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/ecommerce";
			String username = "root";
			String dbPassword = "parth";
			String query = "INSERT INTO user_info (name, email, password) VALUES (?, ?, ?)";
		
			
			Connection c = DriverManager.getConnection(url, username, dbPassword);
			PreparedStatement stmt = c.prepareStatement(query);
			
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, password);
				
			int rowsAffected = stmt.executeUpdate();
			c.close();		
			return rowsAffected > 0;
		
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}		
}
		
		 