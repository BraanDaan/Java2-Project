package com.bushnell.screens.stockreport;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import static com.bushnell.Database.DBDir;
import static com.bushnell.Database.DBName;
import com.bushnell.GUI;

public class StockReport {
    public static JPanel MakeGUI(JPanel cardPanel) {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Title Box (kept as is)
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        titleBox.add(GUI.text("Stock Report", 400, 30, 26, Color.BLACK, "center"));
        titleBox.add(Box.createRigidArea(new Dimension(0,25)));
        panel.add(titleBox);

        Box columnBox = Box.createHorizontalBox();
        String temp = String.format("%-70s\t%-15s\t%-30s\t%-10s", "     SKU", "Price", "Stock", "Description");
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
        GUI.setDimension(scrollPane, 850,450);

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

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        JPanel updateBox = new JPanel();
        updateBox.setPreferredSize(new Dimension(200, 60));
        updateBox.setMaximumSize(new Dimension(200, 60));
        updateBox.setBackground(GUI.vrGreen);
        updateBox.setForeground(GUI.vrGreen);
        JButton updateButton = GUI.button("Save to PDF", 200, 50, 20);
        updateButton.setBorder(BorderFactory.createLineBorder(GUI.vrGreen));
        updateButton.setForeground(Color.WHITE);
        updateBox.add(updateButton);
        panel.add(updateBox);

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box statusBox = Box.createHorizontalBox();
        statusBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel statusText = new JLabel("");
        statusText.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBox.add(statusText);
        panel.add(statusBox);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd-HH.mm");
                    String formattedDateTime = formatter.format(now);
                    String filenamePDF = "VR-StockReport-" + formattedDateTime + ".pdf";
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
                        ResultSet rs = statement.executeQuery("select * from part order by sku;");
                        
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
                                contentStream.showText("Visual Robotics Stock Report");
                                contentStream.endText();
                                contentStream.beginText();
                                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12);
                                contentStream.newLineAtOffset(220, 730);
                                contentStream.showText(formattedDateTime + " page " + Integer.toString(pageNum+1));
                                contentStream.endText();

                                contentStream.beginText();
                                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 10);
                                contentStream.newLineAtOffset(0, 700);
                                String columnTitle = String.format("%35s %8s  %4s  %s", "SKU", "Price", "Stock", "Description");
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
                            
                            String newPart = String.format("%35s %8s %4d    %s", rs.getString("sku"), String.format("$%.2f", rs.getFloat("price")), rs.getInt("stock"), rs.getString("description"));
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
