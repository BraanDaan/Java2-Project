package com.bushnell.screens.demandanalysis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import static com.bushnell.Database.DBDir;
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
        JLabel priceLabel = GUI.text("Quantity", 10, 30, 20, Color.BLACK, "left");
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(priceLabel, 350,30);
        priceBox.add(priceLabel);
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999999, 1);
        JSpinner quantity = new JSpinner(model);
        GUI.setDimension(quantity, 350,30);
        priceBox.add(quantity);
        panel.add(priceBox);

        Box reportBox = Box.createVerticalBox();
        reportBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportBox.setAlignmentY(Component.TOP_ALIGNMENT);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[] {"SKU", "Need", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        JTable demandTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(demandTable);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        GUI.setDimension(scrollPane, 850,300);

        reportBox.add(scrollPane);
        panel.add(reportBox);

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box buttonBox = Box.createVerticalBox();
        buttonBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JButton pdfButton = new JButton("Save to PDF");
        buttonBox.add(pdfButton);
        panel.add(buttonBox);


        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box statusBox = Box.createHorizontalBox();
        statusBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel statusText = new JLabel("");
        statusText.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBox.add(statusText);
        panel.add(statusBox);
        
        quantity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
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
                    while(rs.next()){partDescription.setText(rs.getString("description"));}
                    // // get the stock from the child parts
                    tableModel.setNumRows(0);
                    query = ("WITH RECURSIVE bom_hierarchy AS (SELECT b.*, 1 AS level FROM bom b WHERE b.parent_sku = '" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "' UNION ALL SELECT b.*, bh.level + 1 FROM bom b JOIN bom_hierarchy bh ON b.parent_sku = bh.sku) SELECT * FROM bom_hierarchy bh LEFT JOIN bom b2 ON bh.sku = b2.parent_sku WHERE b2.parent_sku IS NULL;");
                    System.out.println(query);
                    rs = statement.executeQuery(query);
                    Integer temp = 0;
                    while(rs.next()) {temp++;}
                    String[] partList = new String[temp];
                    temp = 0;
                    query = ("WITH RECURSIVE bom_hierarchy AS (SELECT b.*, 1 AS level FROM bom b WHERE b.parent_sku = '" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "' UNION ALL SELECT b.*, bh.level + 1 FROM bom b JOIN bom_hierarchy bh ON b.parent_sku = bh.sku) SELECT * FROM bom_hierarchy bh LEFT JOIN bom b2 ON bh.sku = b2.parent_sku WHERE b2.parent_sku IS NULL;");
                    rs = statement.executeQuery(query);
                    while(rs.next()) {
                        Object[] row = {rs.getString("sku"), String.valueOf(rs.getInt("quantity") * ((Integer) quantity.getValue())), "None"};
                        tableModel.addRow(row);
                        partList[temp] = rs.getString("sku");
                        temp++;
                    }
                    for (Integer i = 0; i < partList.length; i++) {
                        query = ("SELECT * FROM part WHERE sku='" + partList[i] + "';");
                        System.out.println(query);
                        rs = statement.executeQuery(query);
                        while (rs.next()) {tableModel.setValueAt(rs.getString("description"), i, 2);}
                    }
                }
                catch(SQLException f)
                {
                    f.printStackTrace(System.err);
                }
            }
        });
        
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
                    while(rs.next()){partDescription.setText(rs.getString("description"));}
                    // // get the stock from the child parts
                    tableModel.setNumRows(0);
                    query = ("WITH RECURSIVE bom_hierarchy AS (SELECT b.*, 1 AS level FROM bom b WHERE b.parent_sku = '" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "' UNION ALL SELECT b.*, bh.level + 1 FROM bom b JOIN bom_hierarchy bh ON b.parent_sku = bh.sku) SELECT * FROM bom_hierarchy bh LEFT JOIN bom b2 ON bh.sku = b2.parent_sku WHERE b2.parent_sku IS NULL;");
                    System.out.println(query);
                    rs = statement.executeQuery(query);
                    Integer temp = 0;
                    while(rs.next()) {temp++;}
                    String[] partList = new String[temp];
                    temp = 0;
                    query = ("WITH RECURSIVE bom_hierarchy AS (SELECT b.*, 1 AS level FROM bom b WHERE b.parent_sku = '" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "' UNION ALL SELECT b.*, bh.level + 1 FROM bom b JOIN bom_hierarchy bh ON b.parent_sku = bh.sku) SELECT * FROM bom_hierarchy bh LEFT JOIN bom b2 ON bh.sku = b2.parent_sku WHERE b2.parent_sku IS NULL;");
                    rs = statement.executeQuery(query);
                    while(rs.next()) {
                        Object[] row = {rs.getString("sku"), String.valueOf(rs.getInt("quantity") * ((Integer) quantity.getValue())), "None"};
                        tableModel.addRow(row);
                        partList[temp] = rs.getString("sku");
                        temp++;
                    }
                    for (Integer i = 0; i < partList.length; i++) {
                        query = ("SELECT * FROM part WHERE sku='" + partList[i] + "';");
                        System.out.println(query);
                        rs = statement.executeQuery(query);
                        while (rs.next()) {tableModel.setValueAt(rs.getString("description"), i, 2);}
                    }
                }
                catch(SQLException f)
                {
                    f.printStackTrace(System.err);
                }
            }
        });

        pdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd-HH.mm");
                    String formattedDateTime = formatter.format(now);
                    String filenamePDF = "DemandAnalysis-" + formattedDateTime + ".pdf";
                    PDDocument reportPdf = new PDDocument();
                    PDPage pdfPage = null;
                    int skuPerPage = 45;
                    Boolean newPage = true;
                    Integer pageNum = 0;
                    Integer skuCounter = 0;

                    PDPageContentStream contentStream = null;

                    try
                    (
                        Connection connection = DriverManager.getConnection(DBName);
                        Statement statement = connection.createStatement();
                    )
                    {
                        String query = ("WITH RECURSIVE bom_hierarchy AS (SELECT b.*, 1 AS level FROM bom b WHERE b.parent_sku = '" + skuOption.getItemAt(skuOption.getSelectedIndex()) + "' UNION ALL SELECT b.*, bh.level + 1 FROM bom b JOIN bom_hierarchy bh ON b.parent_sku = bh.sku) SELECT * FROM bom_hierarchy bh LEFT JOIN bom b2 ON bh.sku = b2.parent_sku WHERE b2.parent_sku IS NULL;");
                        ResultSet rs = statement.executeQuery(query);
                        
                        while(rs.next())
                        {
                            if (newPage) {
                                pdfPage = new PDPage();
                                reportPdf.addPage(pdfPage);
                                pdfPage = reportPdf.getPage(pageNum);
                                contentStream = new PDPageContentStream(reportPdf, pdfPage);
                                contentStream.beginText();
                                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 20);
                                contentStream.newLineAtOffset(150, 750);
                                contentStream.showText("Visual Robotics Demand Analysis");
                                contentStream.endText();
                                contentStream.beginText();
                                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12);
                                contentStream.newLineAtOffset(220, 730);
                                contentStream.showText(formattedDateTime + " page " + Integer.toString(pageNum+1));
                                contentStream.endText();

                                contentStream.beginText();
                                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 10);
                                contentStream.newLineAtOffset(0, 700);
                                String columnTitle = String.format("%35s %8s", "SKU", "Need");
                                contentStream.showText(columnTitle);
                                contentStream.endText();
                                contentStream.setLineWidth(1f);
                                contentStream.moveTo(10, 695);
                                contentStream.lineTo(602, 695);
                                contentStream.stroke();
                                contentStream.beginText();
                                contentStream.setLeading(14.5f);
                                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
                                contentStream.newLineAtOffset(0, 685);
                                newPage = false;
                            }
                            
                            String newPart = String.format("%35s %8s", rs.getString("sku"), String.valueOf(rs.getInt("quantity") * ((Integer) quantity.getValue())));
                            contentStream.showText(newPart);
                            contentStream.newLine();
                            skuCounter++;
                            if (skuCounter % skuPerPage == 0) {
                                contentStream.endText();
                                contentStream.close();
                                pageNum++;
                                newPage = true;
                            }
                        }
                        if (!newPage) {
                            contentStream.endText();
                            contentStream.close();
                        }
                        String fullPath = Paths.get(DBDir, filenamePDF).toString();
                        try {
                            File existingFile = new File(fullPath);
                            if (existingFile.exists()) {
                                existingFile.delete();
                            }
                            reportPdf.save(fullPath);
                        } catch (IOException s) {
                            s.printStackTrace(System.err);
                            System.out.println("Failed when creating and saving file.");
                            statusText.setForeground(Color.RED);
                            statusText.setText("PDF Creation Failed.");
                        }
                        try {
                            reportPdf.close();
                            statusText.setForeground(Color.GREEN);
                            statusText.setText("PDF Saved Successfully at " + fullPath);
                        } catch (IOException g) {
                            g.printStackTrace(System.err);
                            System.out.println("Failed when closing the file.");
                            statusText.setForeground(Color.RED);
                            statusText.setText("PDF Creation Failed.");
                        }
                    }
                    catch(SQLException h)
                    {
                        h.printStackTrace(System.err);
                        System.out.println("Failed During the File Creation Process.");
                        statusText.setForeground(Color.RED);
                        statusText.setText("PDF Creation Failed.");
                    }
                } catch (IOException i) {
                    i.printStackTrace(System.err);
                    System.out.println("Failed During initialization.");
                    statusText.setForeground(Color.RED);
                    statusText.setText("PDF Creation Failed.");
                }

            }
        });

        return panel;
    }
}
