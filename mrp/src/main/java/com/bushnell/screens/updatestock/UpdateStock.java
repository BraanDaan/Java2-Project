package com.bushnell.screens.updatestock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static com.bushnell.Database.DBName;
import com.bushnell.GUI;

public class UpdateStock {
    public static JPanel MakeGUI(JPanel cardPanel) {
        
        // You will need to change this address so that java.sql can locate the database.
        String databaseLocation = DBName;

        // Register the JDBC Driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
        }

        // Attempt to create a SQL Query to list all strings under the sku column and store them into a dynamic list
        ArrayList<String> skuTemp = new ArrayList<String>();
        try
        (
            Connection connection = DriverManager.getConnection(databaseLocation);
            Statement statement = connection.createStatement();
        )
        {
            ResultSet rs = statement.executeQuery("select * from part order by sku;");
            while(rs.next())
            {
                skuTemp.add(rs.getString("sku"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace(System.err);
        }

        // Take the list and convert it to a standard array to use for the Combo Box
        String[] skuOptions = new String[skuTemp.size()];
        try {
            for (int i = 0; i < skuTemp.size(); i++) {
                skuOptions[i] = skuTemp.get(i);
            } 
        } catch (Exception e) {
        }

        // Initalize panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Title Box (kept as is)
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        titleBox.add(GUI.text("Update Stock", 400, 30, 26, Color.BLACK, "center"));
        titleBox.add(Box.createRigidArea(new Dimension(0,75)));
        panel.add(titleBox);

        // create sku ComboBox from the newly declared array
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box skuOptionBox = Box.createHorizontalBox();
        skuOptionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        skuOptionBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel skuOptionLabel = GUI.text("sku", 10, 30, 20, Color.BLACK, "left");
        skuOptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(skuOptionLabel, 350,30);
        skuOptionBox.add(skuOptionLabel);
        JComboBox<String> skuOption = new JComboBox<>(skuOptions); 
        GUI.setDimension(skuOption, 350, 30);            
        skuOption.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        skuOption.setAlignmentX(Component.CENTER_ALIGNMENT);     
        skuOptionBox.add(skuOption);
        panel.add(skuOptionBox);
        
        // The discription of the part given the sku
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box descriptionBox = Box.createHorizontalBox();
        descriptionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel descriptionLabel = GUI.text("description", 10, 30, 20, Color.BLACK, "left");
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(descriptionLabel, 350,30);
        descriptionBox.add(descriptionLabel);
        JLabel partDescription = new JLabel("");
        partDescription.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        partDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(partDescription, 350, 30);
        descriptionBox.add(partDescription);
        panel.add(descriptionBox);

        // The price of the part given the sku
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box priceBox = Box.createHorizontalBox();
        priceBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel priceLabel = GUI.text("price", 10, 30, 20, Color.BLACK, "left");
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(priceLabel, 350,30);
        priceBox.add(priceLabel);        
        JTextField partPrice = new JTextField("");
        GUI.setDimension(partPrice, 350,30);
        priceBox.add(partPrice);
        panel.add(priceBox);

        // The stock of the part given the sku
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box stockBox = Box.createHorizontalBox();
        stockBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        stockBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel stockLabel = GUI.text("stock", 10, 30, 20, Color.BLACK, "left");
        stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(stockLabel, 350,30);
        stockBox.add(stockLabel);
        JTextField partStock = new JTextField("");
        GUI.setDimension(partStock, 350,30);
        stockBox.add(partStock);
        panel.add(stockBox);

        // An update button that will execute a UPDATE command when pressed
        panel.add(Box.createRigidArea(new Dimension(0,40)));
        JPanel updateBox = new JPanel();
        updateBox.setPreferredSize(new Dimension(200, 60));
        updateBox.setMaximumSize(new Dimension(200, 60));
        updateBox.setBackground(GUI.vrGreen);
        updateBox.setForeground(GUI.vrGreen);
        JButton updateButton = GUI.button("Update", 200, 50, 20);
        updateButton.setBorder(BorderFactory.createLineBorder(GUI.vrGreen));
        updateButton.setForeground(Color.WHITE);
        updateBox.add(updateButton);
        panel.add(updateBox);

        // This is status text that is hidden by default, but will change based on whether or not the UPDATE was successful or not.
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box statusBox = Box.createHorizontalBox();
        statusBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel statusText = new JLabel("");
        statusText.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBox.add(statusText);
        panel.add(statusBox);

        // Change the discription JLabel, the price JTextField, and the stock JTextField to the values given by the SELECT query executed.
        skuOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusText.setText("");
                statusText.setForeground(Color.BLACK);
                try
                (
                    Connection connection = DriverManager.getConnection(databaseLocation);
                    Statement statement = connection.createStatement();
                )
                {
                    String query = ("SELECT * FROM part WHERE sku='" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                    ResultSet rs = statement.executeQuery(query);
                    while(rs.next())
                    {
                        partDescription.setText(rs.getString("description"));
                        partPrice.setText(rs.getString("price"));
                        partStock.setText(rs.getString("stock"));
                    }
                }
                catch(SQLException f)
                {
                    f.printStackTrace(System.err);
                }
            }
        });
        
        // Execute an UPDATE to change the price and stock of a part given its sku and the values left on the respective JTextFields.
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                (
                    Connection connection = DriverManager.getConnection(databaseLocation);
                    Statement statement = connection.createStatement();
                )
                {
                    String query = ("UPDATE part SET price='" + partPrice.getText() + "', stock='" + partStock.getText() + "' WHERE sku='" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                    System.out.println(query);
                    statement.executeUpdate(query);
                    statusText.setForeground(Color.GREEN);
                    statusText.setText("Update Successful!");
                }
                catch(SQLException f)
                {
                    f.printStackTrace(System.err);
                    statusText.setForeground(Color.RED);
                    statusText.setText("Update failed.");
                }
            }
        });

        return panel;
    }
}
