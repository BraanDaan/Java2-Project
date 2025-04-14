package com.bushnell.screens.home;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bushnell.App;
import com.bushnell.Database;
import com.bushnell.GUI;
import com.bushnell.screens.bundle.Bundle;
import com.bushnell.screens.demandanalysis.DemandAnalysis;
import com.bushnell.screens.stockreport.StockReport;
import com.bushnell.screens.updatestock.UpdateStock;

public class HomeScreen {

    public JPanel MakeGUI(String appDir) throws IOException {

        Path jarPath = Paths.get(appDir);
        String dbPath = jarPath.getParent().getParent().toString();
        Database.setDBDirectory(dbPath);

        // Create the main panel with a horizontal BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(new Dimension(1280, 720)); // Set preferred size
        mainPanel.setMaximumSize(new Dimension(1920, 1080)); // Set maximum size
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        mainPanel.setAlignmentY(Component.TOP_ALIGNMENT); // Align to the top vertically

        // Set the background color to black
        mainPanel.setBackground(Color.BLACK);

        // Create a horizontal box to hold the layout
        Box homeBox = Box.createHorizontalBox();
        homeBox.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        homeBox.setAlignmentY(Component.TOP_ALIGNMENT); // Align to the top

        // Create a vertical menu box for navigation
        Box menuBox = Box.createVerticalBox();
        menuBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
        menuBox.setAlignmentY(Component.TOP_ALIGNMENT); // Align to the top

        // Create a sub-menu box with specified dimensions and a white border
        Box subMenuBox = Box.createHorizontalBox();
        subMenuBox.setPreferredSize(new Dimension(1200, 685));
        subMenuBox.setMaximumSize(new Dimension(1200, 685));
        subMenuBox.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        subMenuBox.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        subMenuBox.setAlignmentY(Component.TOP_ALIGNMENT); // Align to the top

        // Add spacing and components to the homeBox
        homeBox.add(Box.createRigidArea(new Dimension(20, 0)));
        homeBox.add(menuBox);
        homeBox.add(Box.createRigidArea(new Dimension(20, 0)));
        homeBox.add(subMenuBox);
        homeBox.add(Box.createRigidArea(new Dimension(20, 0)));

        // Add the homeBox to the main panel with spacing
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(homeBox);

        // Load VRRobotics logo and make it transparent
        ImageIcon logo = new ImageIcon(App.class.getResource("resources/VisualRoboticsLogo.png"));
        ImageFilter filter = new RGBImageFilter() {
            int transparentColor = Color.white.getRGB() | 0xFF000000;
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == transparentColor) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer filteredImgProd = new FilteredImageSource(logo.getImage().getSource(), filter);
        Image transparentLogo = Toolkit.getDefaultToolkit().createImage(filteredImgProd);

        // Scale the Logo and add it to the menu
        Image scaledLogo = transparentLogo.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuBox.add(Box.createRigidArea(new Dimension(0, 5)));
        menuBox.add(logoLabel);
        menuBox.add(Box.createRigidArea(new Dimension(0, 5)));  

        // Create a title box with information and add it to the menu
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleBox.add(GUI.text("MRP System", 200, 30, 20, Color.WHITE, "left")); // Title
        titleBox.add(Box.createRigidArea(new Dimension(0, 25)));
        menuBox.add(titleBox);

        // Create navigation buttons for sections
        Box buttons = Box.createVerticalBox();
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension buttonSize = new Dimension(200, 60);

        JPanel updateStockBox = new JPanel();
        updateStockBox.setPreferredSize(buttonSize);
        updateStockBox.setMaximumSize(buttonSize);
        updateStockBox.setBackground(GUI.vrGreen);
        updateStockBox.setForeground(GUI.vrGreen);
        JButton updateStockButton = GUI.button("Update Stock", 200, 50, 20);
        updateStockButton.setBorder(BorderFactory.createLineBorder(GUI.vrGreen));
        updateStockButton.setForeground(Color.WHITE);
        updateStockBox.add(updateStockButton);

        JPanel stockReportBox = new JPanel();
        stockReportBox.setPreferredSize(buttonSize);
        stockReportBox.setMaximumSize(buttonSize);
        stockReportBox.setBackground(GUI.vrGreen);
        stockReportBox.setForeground(GUI.vrGreen);
        JButton stockReportButton = GUI.button("Stock Report", 200, 50, 20);
        stockReportButton.setBorder(BorderFactory.createLineBorder(GUI.vrGreen));
        stockReportButton.setForeground(Color.WHITE);
        stockReportBox.add(stockReportButton);

        JPanel bundleBox = new JPanel();
        bundleBox.setPreferredSize(buttonSize);
        bundleBox.setMaximumSize(buttonSize);
        bundleBox.setBackground(GUI.vrGreen);
        bundleBox.setForeground(GUI.vrGreen);
        JButton bundleButton = GUI.button("Bundle", 200, 50, 20);
        bundleButton.setBorder(BorderFactory.createLineBorder(GUI.vrGreen));
        bundleButton.setForeground(Color.WHITE);
        bundleBox.add(bundleButton);

        JPanel demandAnalysisBox = new JPanel();
        demandAnalysisBox.setPreferredSize(buttonSize);
        demandAnalysisBox.setMaximumSize(buttonSize);
        demandAnalysisBox.setBackground(GUI.vrGreen);
        demandAnalysisBox.setForeground(GUI.vrGreen);
        JButton demandAnalysisButton = GUI.button("Demand Analysis", 200, 50, 20);
        demandAnalysisButton.setBorder(BorderFactory.createLineBorder(GUI.vrGreen));
        demandAnalysisButton.setForeground(Color.WHITE);
        demandAnalysisBox.add(demandAnalysisButton);

        // Add buttons with spacing
        buttons.add(updateStockBox);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));
        buttons.add(stockReportBox);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));
        buttons.add(bundleBox);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));
        buttons.add(demandAnalysisBox);
        menuBox.add(buttons);

        // Create and initialize various sub-panels
        JPanel updateStockPanel = UpdateStock.MakeGUI(mainPanel);
        JPanel stockReportPanel = StockReport.MakeGUI(mainPanel);
        JPanel bundlePanel = Bundle.MakeGUI(mainPanel);
        JPanel demandAnalysisPanel = DemandAnalysis.MakeGUI(mainPanel);

        // Create a card panel for dynamic switching between views
        JPanel cardPanel = new JPanel(new CardLayout());
        // JPanel loginPanel = Login.MakeGUI(cardPanel);

        // Add panels to the card panel
        cardPanel.add(updateStockPanel, "updateStock");
        cardPanel.add(stockReportPanel, "stockReport");
        cardPanel.add(bundlePanel, "bundle");
        cardPanel.add(demandAnalysisPanel, "demandAnalysis");

        subMenuBox.add(cardPanel);

        // Set initial view to "Login"
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        // Add listeners to change the displayed panel on button click
        updateStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "updateStock");
            }
        });

        stockReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "stockReport");
            }
        });

        bundleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "bundle");
            }
        });

        demandAnalysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "demandAnalysis");
            }
        });

        mainPanel.setVisible(true); // Make panel visible
        return mainPanel; // Return main panel
    }
}

