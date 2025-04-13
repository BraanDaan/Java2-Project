package com.bushnell.screens.stockreport;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bushnell.GUI;

public class StockReport {
    public static JPanel MakeGUI(JPanel cardPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE); // Bushnell Blue

        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Title Box (kept as is)
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        titleBox.add(GUI.text("Stock Report", 400, 30, 26, Color.BLACK, "center"));
        titleBox.add(Box.createRigidArea(new Dimension(0,75)));
        panel.add(titleBox);

        panel.add(Box.createRigidArea(new Dimension(0,25)));
        Box reportBox = Box.createVerticalBox();
        reportBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportBox.setAlignmentY(Component.TOP_ALIGNMENT);
        // reportBox.add(GUI.text("FlavorText", 400, 30, 26, Color.BLACK, "center"));
        JTextArea reportText = new JTextArea("Sample");
        JScrollPane scrollPane = new JScrollPane(reportText);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        GUI.setDimension(scrollPane, 500,500);;
        reportBox.add(scrollPane);
        panel.add(reportBox);

        return panel;
    }
}
