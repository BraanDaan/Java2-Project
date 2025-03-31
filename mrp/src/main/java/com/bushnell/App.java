package com.bushnell;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        final int width = 1280;
        final int height = 720;
        // Create a new frame
        JFrame frame = new JFrame("Java Term Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleBox.setAlignmentY(Component.TOP_ALIGNMENT);
        ImageIcon logo = new ImageIcon("src/rescources/VisualRoboticsLogo.png");
        titleBox.add(new JLabel(logo));
        titleBox.add(Box.createRigidArea(new Dimension(0,50)));
        panel.add(titleBox);

        // Create buttons
        JButton button1 = new JButton("Update Stock");
        JButton button2 = new JButton("Stock Report");
        JButton button3 = new JButton("Bundle");
        JButton button4 = new JButton("Demand Analysis");

        // Add action listeners to buttons
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button 1 clicked!");
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button 2 clicked!");
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button 3 clicked!");
            }
        });

        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button 4 clicked!");
            }
        });

        // Add buttons to the panel
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);

        // Add panel to the frame
        frame.add(panel);

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}
