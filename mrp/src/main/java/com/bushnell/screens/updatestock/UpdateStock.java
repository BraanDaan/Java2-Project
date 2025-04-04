package com.bushnell.screens.updatestock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bushnell.GUI;

public class UpdateStock {
    public static JPanel MakeGUI(JPanel cardPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE); // Bushnell Blue

        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Title Box (kept as is)
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        titleBox.add(GUI.text("Update Stock", 400, 30, 26, Color.BLACK, "center"));
        titleBox.add(Box.createRigidArea(new Dimension(0,75)));
        panel.add(titleBox);
        panel.add(Box.createRigidArea(new Dimension(0,25)));

                // create advice type combo box
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box adviceCategoryBox = Box.createHorizontalBox();
        adviceCategoryBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        adviceCategoryBox.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel adviceCategoryLabel = GUI.text("sku", 10, 30, 20, Color.BLACK, "left");
        adviceCategoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GUI.setDimension(adviceCategoryLabel, 350,30);
        adviceCategoryBox.add(adviceCategoryLabel);
        // adviceCategoryBox.add(Box.createRigidArea(new Dimension(20,0)));
        String[] adviceCategories = {"Anger", "Stress", "Relationship", "Depression"};
        JComboBox<String> adviceCategory = new JComboBox<>(adviceCategories); 
        GUI.setDimension(adviceCategory, 150, 30);            
        adviceCategory.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        // adviceCategory.setAlignmentX(Component.CENTER_ALIGNMENT);     
        adviceCategoryBox.add(adviceCategory);
        panel.add(adviceCategoryBox);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        return panel;
    }
}
