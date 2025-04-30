import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class PaymentUI 
{
    	public static void main(String[] args) 
	{
        	if (args.length == 0) 
		{
            		JOptionPane.showMessageDialog(null, "Total amount not provided!");
            		return;
        	}

        	double totalAmount = Double.parseDouble(args[0]);

        	JFrame frame = new JFrame("Payment Window");
        	frame.setSize(400, 300);
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	frame.setLayout(null);

        	JLabel label = new JLabel("Total Amount: " + totalAmount);
        	label.setBounds(50, 30, 200, 25);
        	frame.add(label);

        	JTextField amountField = new JTextField(String.valueOf(totalAmount));
        	amountField.setBounds(50, 60, 200, 25);
        	amountField.setEditable(false);
        	frame.add(amountField);

        	JButton payButton = new JButton("Pay Now");
        	payButton.setBounds(50, 100, 200, 30);
        	frame.add(payButton);

        	payButton.addActionListener(new ActionListener() 
		{
            	public void actionPerformed(ActionEvent e) 
		{
                	try 
			{
                    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "parth");

                   
                    		PreparedStatement insertPayment = con.prepareStatement("INSERT INTO payments (amount) VALUES (?)");
                    		insertPayment.setDouble(1, totalAmount);
                    		insertPayment.executeUpdate();
                    		insertPayment.close();

               
                    		PreparedStatement clearCart = con.prepareStatement("DELETE FROM cart");
                    		clearCart.executeUpdate();
                    		clearCart.close();

                    
                    		JOptionPane.showMessageDialog(frame, "Payment of ₹" + totalAmount + " successful!\nThank you for your purchase!");

                    		con.close();
                    		frame.dispose();

                		} 
				catch (SQLException ex) 
				{
                    			JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                		}
            		}
        	});

        	frame.setVisible(true);
    	}
}

