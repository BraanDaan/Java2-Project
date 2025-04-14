package com.bushnell.screens.stockreport;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
        panel.setBackground(Color.WHITE); // Bushnell Blue

        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Title Box (kept as is)
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        titleBox.add(GUI.text("Stock Report", 400, 30, 26, Color.BLACK, "center"));
        titleBox.add(Box.createRigidArea(new Dimension(0,25)));
        panel.add(titleBox);

        Box reportBox = Box.createVerticalBox();
        reportBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportBox.setAlignmentY(Component.TOP_ALIGNMENT);
        // reportBox.add(GUI.text("FlavorText", 400, 30, 26, Color.BLACK, "center"));
        JTextArea reportText = new JTextArea("");
        reportText.setEditable(false);
        reportText.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                reportText.setText(reportText.getText() + "\n" + "\t" + rs.getString("sku") + "\t" + rs.getString("price") + "\t" + rs.getInt("stock") + "\t" + rs.getString("description"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace(System.err);
        }

        reportBox.add(scrollPane);
        panel.add(reportBox);

        panel.add(Box.createRigidArea(new Dimension(0,40)));
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

        return panel;
    }
}
