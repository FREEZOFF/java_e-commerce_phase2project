import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerUI
{
    	public static void main(String args[])
    	{
        	JFrame frame = new JFrame("Customer Dashboard");
        	frame.setSize(400, 300);
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	frame.setLayout(null);

        	JLabel label1 = new JLabel("Welcome Customer!");
        	label1.setBounds(0, 0, 240, 50);

        	JButton button1 = new JButton("Browse Shoe");
        	button1.setBounds(40, 60, 250, 30);

        	JButton button2 = new JButton("Add To Cart");
        	button2.setBounds(40, 100, 240, 30);

        	JButton button3 = new JButton("Delete From Cart");
        	button3.setBounds(40, 140, 240, 30);

        	JButton button4 = new JButton("Exit Without Purchasing");
        	button4.setBounds(40, 180, 240, 30);

        	JButton button5 = new JButton("Payment Window");
        	button5.setBounds(40, 220, 240, 30);

        	button1.addActionListener(new ActionListener()
        	{
            		public void actionPerformed(ActionEvent e)
            		{
                		browseShoes();
            		}
        	});

        	button2.addActionListener(new ActionListener()
        	{
            		public void actionPerformed(ActionEvent e)
            		{
                		addToCart();
            		}
        	});

        	button3.addActionListener(new ActionListener()
        	{
            		public void actionPerformed(ActionEvent e)
            		{
                		deleteFromCart();
            		}
        	});

        	button4.addActionListener(new ActionListener()
        	{
            		public void actionPerformed(ActionEvent e)
            		{
                		System.exit(0);
            		}
        	});

        	button5.addActionListener(new ActionListener()
        	{
            		public void actionPerformed(ActionEvent e)
            		{
                		double totalAmount = getCartTotal(); 
                		frame.dispose();
                		PaymentUI.main(new String[]{String.valueOf(totalAmount)}); 
            		}
        	});

        	frame.add(label1);
        	frame.add(button1);
        	frame.add(button2);
        	frame.add(button3);
        	frame.add(button4);
        	frame.add(button5);

        	frame.setVisible(true);
    	}

  
    	public static double getCartTotal()
    	{
        	double total = 0;
        	String url = "jdbc:mysql://localhost:3306/ecommerce";
        	String username = "root";
        	String password = "parth";

        	try
        	{
            		Connection con = DriverManager.getConnection(url, username, password);
            		String query = "SELECT SUM(shoes.price) FROM shoes JOIN cart ON shoes.shoeID = cart.shoeID";
            		Statement stmt = con.createStatement();
            		ResultSet rs = stmt.executeQuery(query);

            		if (rs.next())
            		{
                		total = rs.getDouble(1);
            		}
			con.close();
        	}
        	catch (SQLException ex)
        	{
            		JOptionPane.showMessageDialog(null, "Error calculating total: " + ex.getMessage());
        	}
        	return total;
    	}

    	public static void browseShoes()
    	{
        	String url = "jdbc:mysql://localhost:3306/ecommerce";
        	String username = "root";
        	String password = "parth";

        	try
        	{
            		Connection con = DriverManager.getConnection(url, username, password);
            		Statement st = con.createStatement();
            		ResultSet rs = st.executeQuery("SELECT * FROM shoes");

            		StringBuilder result = new StringBuilder("shoeID\tName\tType\tColor\tSize\tPrice\n");
            		while (rs.next())
            		{
                		result.append(rs.getString("shoeID")).append("\t")
                      		.append(rs.getString("name")).append("\t")
                      		.append(rs.getString("type")).append("\t")
                      		.append(rs.getString("color")).append("\t")
                      		.append(rs.getString("size")).append("\t")
                      		.append(rs.getString("price")).append("\n");
            		}

      	      		JTextArea textArea = new JTextArea(result.toString());
            		textArea.setEditable(false);
            		JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Available Shoes", JOptionPane.INFORMATION_MESSAGE);
			con.close();
        	}
        	catch (Exception ex)
        	{
            		JOptionPane.showMessageDialog(null, "Error browsing shoes: " + ex.getMessage());
        	}
    	}

    	public static void addToCart()
    	{
        	String url = "jdbc:mysql://localhost:3306/ecommerce";
        	String username = "root";
        	String password = "parth";

        	try
        	{
            		Connection con = DriverManager.getConnection(url, username, password);
            		String shoeID = JOptionPane.showInputDialog("Enter Shoe ID to add to cart:");

            		PreparedStatement ps = con.prepareStatement("INSERT INTO cart (shoeID) VALUES (?)");
            		ps.setString(1, shoeID);
            		ps.executeUpdate();

            		JOptionPane.showMessageDialog(null, "Shoe added to cart successfully!");
			con.close();
        	}
        	catch (Exception ex)
        	{
            		JOptionPane.showMessageDialog(null, "Error adding to cart: " + ex.getMessage());
        	}
    	}

    	public static void deleteFromCart()
    	{
        	String url = "jdbc:mysql://localhost:3306/ecommerce";
        	String username = "root";
        	String password = "parth";

        	try
        	{
            		Connection con = DriverManager.getConnection(url, username, password);
            		String shoeID = JOptionPane.showInputDialog("Enter Shoe ID to remove from cart:");

            		PreparedStatement ps = con.prepareStatement("DELETE FROM cart WHERE shoeID = ?");
            		ps.setString(1, shoeID);

            		int rowsDeleted = ps.executeUpdate();
            		if (rowsDeleted > 0)
            		{
                		JOptionPane.showMessageDialog(null, "Shoe removed from cart successfully!");
            		}
            		else
            		{
                		JOptionPane.showMessageDialog(null, "No such item found in cart.");
            		}
			con.close();
        	}
        	catch (Exception ex)
        	{
            		JOptionPane.showMessageDialog(null, "Error removing from cart: " + ex.getMessage());
        	}
    	}
}

