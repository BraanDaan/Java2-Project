package com.bushnell.screens.bundle;

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
import javax.swing.JTextArea;

import static com.bushnell.Database.DBName;
import com.bushnell.GUI;

public class Bundle {
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
            ResultSet rs = statement.executeQuery("select * from part where sku like '%SUB-%'");
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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE); // Bushnell Blue

        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Title Box (kept as is)
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        titleBox.add(GUI.text("Bundle", 400, 30, 26, Color.BLACK, "center"));
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

        // The stock of the part given the sku
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box stockBox = Box.createHorizontalBox();
        stockBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        stockBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel stockLabel = GUI.text("stock", 10, 30, 20, Color.BLACK, "left");
        stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(stockLabel, 350,30);
        stockBox.add(stockLabel);
        JLabel partStock = new JLabel("");
        partStock.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        partStock.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(partStock, 350, 30);
        stockBox.add(partStock);
        panel.add(stockBox);

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        JPanel bundleBox = new JPanel();
        bundleBox.setPreferredSize(new Dimension(200, 60));
        bundleBox.setMaximumSize(new Dimension(200, 60));
        bundleBox.setBackground(GUI.vrGreen);
        bundleBox.setForeground(GUI.vrGreen);
        JButton bundleButton = GUI.button("Bundle", 200, 50, 20);
        bundleButton.setBorder(BorderFactory.createLineBorder(GUI.vrGreen));
        bundleButton.setForeground(Color.WHITE);
        bundleBox.add(bundleButton);
        panel.add(bundleBox);

        panel.add(Box.createRigidArea(new Dimension(0,45)));
        Box subTitleBox = Box.createVerticalBox();
        subTitleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        subTitleBox.add(GUI.text("Subcomponents", 400, 30, 20, Color.BLACK, "center"));
        panel.add(subTitleBox);

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box subcomponentBox = Box.createVerticalBox();
        subcomponentBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        subcomponentBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JTextArea subcomponentText = new JTextArea();
        subcomponentText.setEditable(false);
        subcomponentBox.add(subcomponentText);
        panel.add(subcomponentBox);
        GUI.setDimension(subcomponentText, 850,450);

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
                // statusText.setText("");
                // statusText.setForeground(Color.BLACK);
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
                        partStock.setText(rs.getString("stock"));
                    }
                    // query = ("SELECT * FROM bom WHERE parent_sku LIKE '" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                    // rs = statement.executeQuery(query);
                    // while(rs.next())
                    // {
                    //     subcomponentText.setText(rs.getString(""));
                    // }
                }
                catch(SQLException f)
                {
                    f.printStackTrace(System.err);
                }
            }
        });

        bundleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                (
                    Connection connection = DriverManager.getConnection(databaseLocation);
                    Statement statement = connection.createStatement();
                )
                {
                }
                catch(SQLException f)
                {
                }
            }
        });

        return panel;
    }
}
