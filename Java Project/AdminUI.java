
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminUI
{
	public static void main(String args[])
	{
		JFrame frame = new JFrame("Admin Dashboard");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		JLabel label1 = new JLabel("Welcome Admin!");
        	label1.setBounds(0, 0, 240, 50);

        	JButton button1 = new JButton("Add Shoe");
        	button1.setBounds(40, 60, 200, 30);

       	 	JButton button2 = new JButton("View Shoes");
        	button2.setBounds(40, 100, 200, 30);

	        JButton button3 = new JButton("Update Shoe");
        	button3.setBounds(40, 140, 200, 30);

        	JButton button4 = new JButton("Delete Shoe");
        	button4.setBounds(40, 180, 200, 30);
	
        	JButton button5 = new JButton("Exit");
        	button5.setBounds(40, 220, 200, 30);

		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addShoe();
			}
		});
		
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				viewShoe();
			}
		});
		
		button3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateShoe();
			}
		});

		button4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				deleteShoe();
			}
		});
	
		button5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
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

	public static void addShoe() 
	{
		String url = "jdbc:mysql://localhost:3306/ecommerce";
		String username = "root";
		String password = "parth";

        	try 
		{
			
            		Connection con = DriverManager.getConnection(url, username, password);
			int num = Integer.parseInt(JOptionPane.showInputDialog("Enter number of shoes to add:"));
            		PreparedStatement ps = con.prepareStatement("INSERT INTO shoes (shoeID, name, type, color, size, price) VALUES (?, ?, ?, ?, ?, ?)");

            		for (int i = 0; i < num; i++) 
			{
				String shoeID = JOptionPane.showInputDialog("Enter shoe ID:");
                		String name = JOptionPane.showInputDialog("Enter shoe name:");
                		String type = JOptionPane.showInputDialog("Enter shoe type:");
                		String color = JOptionPane.showInputDialog("Enter shoe color:");
                		String size = JOptionPane.showInputDialog("Enter shoe size:");
                		String price = JOptionPane.showInputDialog("Enter shoe price:");
				
				ps.setString(1, shoeID);
                		ps.setString(2, name);
                		ps.setString(3, type);
               			ps.setString(4, color);
                		ps.setString(5, size);
                		ps.setString(6, price);

                		ps.executeUpdate();
            		}

            		JOptionPane.showMessageDialog(null, "Shoe(s) added successfully!");
			con.close();
        	} 
		catch (Exception ex) 
		{
            		JOptionPane.showMessageDialog(null, "Error adding shoe: " + ex.getMessage());
        	}
    	}
	
	public static void viewShoe() 
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
            		JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Shoe Inventory", JOptionPane.INFORMATION_MESSAGE);
			con.close();

        	} 
		catch (Exception ex) 
		{
            		JOptionPane.showMessageDialog(null, "Error viewing shoes: " + ex.getMessage());
        	}
    	}

	public static void updateShoe() 
	{
    		String url = "jdbc:mysql://localhost:3306/ecommerce";
    		String username = "root";
    		String password = "parth";

    		try 
		{
        		Connection con = DriverManager.getConnection(url, username, password);
        
        		String shoeID = JOptionPane.showInputDialog("Enter the Shoe ID to update:");
        
        		String newName = JOptionPane.showInputDialog("Enter new name:");
        		String newType = JOptionPane.showInputDialog("Enter new type:");
        		String newColor = JOptionPane.showInputDialog("Enter new color:");
        		String newSize = JOptionPane.showInputDialog("Enter new size:");
        		String newPrice = JOptionPane.showInputDialog("Enter new price:");

        		PreparedStatement ps = con.prepareStatement(
    			"UPDATE shoes SET name = ?, type = ?, color = ?, size = ?, price = ? WHERE shoeID = ?");
        
        		ps.setString(1, newName);
        		ps.setString(2, newType);
        		ps.setString(3, newColor);
        		ps.setString(4, newSize);
        		ps.setString(5, newPrice);
        		ps.setString(6, shoeID);

        		int rowsUpdated = ps.executeUpdate();
        
        		if (rowsUpdated > 0) 
			{
            			JOptionPane.showMessageDialog(null, "Shoe updated successfully!");
        		} 
			else 
			{
            			JOptionPane.showMessageDialog(null, "Shoe ID not found.");
        		}
			con.close();
        	} 
		catch (Exception ex) 
		{
        		JOptionPane.showMessageDialog(null, "Error updating shoe: " + ex.getMessage());
    		}
	}
		
	public static void deleteShoe() 
	{
    		String url = "jdbc:mysql://localhost:3306/ecommerce";
    		String username = "root";
    		String password = "parth";

    		try 
		{
        		Connection con = DriverManager.getConnection(url, username, password);

        		String shoeID = JOptionPane.showInputDialog("Enter the Shoe ID to delete:");
        
        		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this shoe?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        		if (confirm == JOptionPane.YES_OPTION) 
			{
            			PreparedStatement ps = con.prepareStatement("DELETE FROM shoes WHERE shoeID = ?");
            			ps.setString(1, shoeID);

            			int rowsDeleted = ps.executeUpdate();

            			if (rowsDeleted > 0) 
				{
                			JOptionPane.showMessageDialog(null, "Shoe deleted successfully!");
            			} 
				else 
				{
                			JOptionPane.showMessageDialog(null, "Shoe ID not found.");
            			}
       			}
			con.close();
		} 
		catch (Exception ex) 
		{
        		JOptionPane.showMessageDialog(null, "Error deleting shoe: " + ex.getMessage());
    		}
	}	
}




