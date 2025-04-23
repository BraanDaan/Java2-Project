package com.bushnell.screens.demandanalysis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import static com.bushnell.Database.DBName;
import com.bushnell.GUI;

public class DemandAnalysis {
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
        titleBox.add(GUI.text("Demand Analysis", 400, 30, 26, Color.BLACK, "center"));
        titleBox.add(Box.createRigidArea(new Dimension(0,25)));
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

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box columnBox = Box.createHorizontalBox();
        String temp = String.format("%-70s\t%-15s\t%-30s", "     SKU", "Need", "Description");
        JLabel columnText = GUI.text(temp, 850, 14, 12, Color.BLACK, "left");
        columnBox.add(columnText);
        panel.add(columnBox);

        Box reportBox = Box.createVerticalBox();
        reportBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JTextPane reportText = new JTextPane();
        reportText.setEditable(false);
        StyledDocument doc = reportText.getStyledDocument();

        JScrollPane scrollPane = new JScrollPane(reportText);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        GUI.setDimension(scrollPane, 850,300);

        try
        (
            Connection connection = DriverManager.getConnection(DBName);
            Statement statement = connection.createStatement();
        )
        {
            ResultSet rs = statement.executeQuery("select * from part order by sku;");
            while(rs.next())
            {
                try {
                    Style style = doc.addStyle("Style", null);
                    StyleConstants.setFontFamily(style, "Monospaced");
                    StyleConstants.setFontSize(style, 12);
                    doc.insertString(doc.getLength(), String.format("%-30s\t%-10s\t%-5s\t%s\n", rs.getString("sku"), String.format("$%.2f", rs.getFloat("price")), rs.getString("stock"), rs.getString("description")), style);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace(System.err);
        }

        reportBox.add(scrollPane);
        panel.add(reportBox);

        return panel;
    }
}
