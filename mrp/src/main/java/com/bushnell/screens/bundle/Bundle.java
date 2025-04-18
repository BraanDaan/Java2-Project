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
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
        
        Box columnBox = Box.createHorizontalBox();
        String temp = String.format("%-20s\t%-20s\t%-70s\t%-20s", "Stock", "QTY", "Part", "Description");
        JLabel columnText = GUI.text(temp, 650, 14, 12, Color.BLACK, "left");
        columnBox.add(columnText);
        panel.add(columnBox);
        
        Box subcomponentBox = Box.createVerticalBox();
        subcomponentBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        subcomponentBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JTextPane subcomponentText = new JTextPane();
        subcomponentText.setEditable(false);
        StyledDocument doc = subcomponentText.getStyledDocument();

        JScrollPane scrollPane = new JScrollPane(subcomponentText);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        GUI.setDimension(scrollPane, 650,150);

        subcomponentBox.add(scrollPane);
        panel.add(subcomponentBox);

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
                        partStock.setText(rs.getString("stock"));
                    }
                    // // get the stock from the child parts
                    try {
                        doc.remove(0, doc.getLength());
                    } catch (BadLocationException g) {
                        g.printStackTrace();
                    }
                    query = ("SELECT * FROM bom WHERE parent_sku='" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                    System.out.println(query);
                    rs = statement.executeQuery(query);
                    Integer temp = 0;
                    while(rs.next()){temp++;}
                    String[] partList = new String[temp];
                    Integer[] quantityList = new Integer[temp];
                    temp = 0;
                    rs = statement.executeQuery(query);
                    while(rs.next())
                    {
                        partList[temp] = rs.getString("sku");
                        quantityList[temp] = rs.getInt("quantity");
                        temp++;
                    }
                    for (int idx = 0; idx < partList.length; idx++) {
                        query = ("SELECT * FROM part WHERE sku='" + partList[idx] + "';");
                        System.out.println(query);
                        rs = statement.executeQuery(query);
                        while(rs.next())
                        {
                            try {
                                Style style = doc.addStyle("Style", null);
                                StyleConstants.setFontFamily(style, "Monospaced");
                                StyleConstants.setFontSize(style, 12);
                                doc.insertString(doc.getLength(), String.format("%-5s\t%-5s\t%-30s\t%s\n", rs.getString("stock"), String.valueOf(quantityList[idx]), partList[idx], rs.getString("description")), style);
                            } catch (BadLocationException f) {
                                f.printStackTrace();
                            }
                        }
                    }
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
                    String query = ("SELECT * FROM bom WHERE parent_sku='" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                    System.out.println(query);
                    ResultSet rs = statement.executeQuery(query);
                    Integer temp = 0;
                    while(rs.next()){temp++;}
                    String[] partList = new String[temp];
                    Integer[] quantityList = new Integer[temp];
                    Integer[] stockList = new Integer[temp];
                    Boolean stop = false;
                    temp = 0;
                    rs = statement.executeQuery(query);
                    while(rs.next())
                    {
                        partList[temp] = rs.getString("sku");
                        quantityList[temp] = rs.getInt("quantity");
                        temp++;
                    }
                    for (int idx = 0; idx < partList.length; idx++) {
                        query = ("SELECT * FROM part WHERE sku='" + partList[idx] + "';");
                        System.out.println(query);
                        rs = statement.executeQuery(query);
                        while(rs.next())
                        {
                            stockList[idx] = rs.getInt("stock");
                        }
                    }
                    for (int idx = 0; idx < partList.length; idx++) {
                        if (stockList[idx] < quantityList[idx]) {
                            stop = true;
                            statusText.setForeground(Color.RED);
                            statusText.setText("Bundle Failed. One or more parts does not meet the neccessary requirements.");
                            break;
                        }
                    }
                    if (stop == false) {
                        
                        for (int idx = 0; idx < partList.length; idx++) {
                            query = ("UPDATE part SET stock=" + String.valueOf(stockList[idx] - quantityList[idx]) + " WHERE sku='" + partList[idx] + "';");
                            System.out.println(query);
                            statement.executeUpdate(query);
                        }
                        query = ("UPDATE part SET stock = stock + 1 WHERE sku='" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                        System.out.println(query);
                        statement.executeUpdate(query);

                        // This is the same action as when you select an option from the drop-down menu.
                        // Will need to delegate this to a function.
                        query = ("SELECT * FROM part WHERE sku='" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                        rs = statement.executeQuery(query);
                        while(rs.next())
                        {
                            partDescription.setText(rs.getString("description"));
                            partStock.setText(rs.getString("stock"));
                        }
                        try {
                            doc.remove(0, doc.getLength());
                        } catch (BadLocationException g) {
                            g.printStackTrace();
                        }
                        query = ("SELECT * FROM bom WHERE parent_sku='" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "';");
                        System.out.println(query);
                        rs = statement.executeQuery(query);
                        temp = 0;
                        while(rs.next()){temp++;}
                        partList = new String[temp];
                        quantityList = new Integer[temp];
                        temp = 0;
                        rs = statement.executeQuery(query);
                        while(rs.next())
                        {
                            partList[temp] = rs.getString("sku");
                            quantityList[temp] = rs.getInt("quantity");
                            temp++;
                        }
                        for (int idx = 0; idx < partList.length; idx++) {
                            query = ("SELECT * FROM part WHERE sku='" + partList[idx] + "';");
                            System.out.println(query);
                            rs = statement.executeQuery(query);
                            while(rs.next())
                            {
                                try {
                                    Style style = doc.addStyle("Style", null);
                                    StyleConstants.setFontFamily(style, "Monospaced");
                                    StyleConstants.setFontSize(style, 12);
                                    doc.insertString(doc.getLength(), String.format("%-5s\t%-5s\t%-30s\t%s\n", rs.getString("stock"), String.valueOf(quantityList[idx]), partList[idx], rs.getString("description")), style);
                                } catch (BadLocationException f) {
                                    f.printStackTrace();
                                }
                            }
                        }
                        statusText.setForeground(Color.GREEN);
                        statusText.setText("Bundle Successful!");
                    }
                }
                catch(SQLException f)
                {
                    f.printStackTrace();
                }
            }
        });

        return panel;
    }
}
