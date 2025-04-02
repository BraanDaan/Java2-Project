package com.bushnell.screens.home;

// import com.bushnell.termproject.appointment.Appointment;
// import com.bushnell.termproject.clientinfo.ClientInformation;
// import com.bushnell.termproject.onlineadvice.OnlineAdvice;
// import com.bushnell.termproject.login.Login;
// import com.bushnell.termproject.faqs.FAQs;
// import com.bushnell.termproject.welcome.Welcome;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bushnell.GUI;

// Class for loading images from the resources directory
class GetImage {
    public JLabel getImage(String filename) {
        // Returns a JLabel containing the specified image from the resources folder
        return new JLabel(new ImageIcon(getClass().getResource("/resources/" + filename)));
    }
}

public class HomeScreen {

    public JPanel MakeGUI() {

        // Create the main panel with a horizontal BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(new Dimension(1550, 800)); // Set preferred size
        mainPanel.setMaximumSize(new Dimension(1550, 800)); // Set maximum size
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        mainPanel.setAlignmentY(Component.TOP_ALIGNMENT); // Align to the top vertically

        // Set the background color using Bushnell blue
        String bushnellBlueHashCode = "#011E40";
        Color bushnellBlue = Color.decode(bushnellBlueHashCode);
        mainPanel.setBackground(bushnellBlue);

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
        subMenuBox.setPreferredSize(new Dimension(1200, 720));
        subMenuBox.setMaximumSize(new Dimension(1200, 720));
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

        // Create a horizontal box for login and FAQ buttons
        Box login = Box.createHorizontalBox();
        login.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

        // Create and style the login button
        JButton loginButton = GUI.button("Login", 110, 27, 15);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // White border
        loginButton.setForeground(Color.WHITE); // White text

        // Create and style the FAQ button
        JButton faqButton = GUI.button("FAQs", 110, 27, 15);
        faqButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        faqButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // White border
        faqButton.setForeground(Color.WHITE); // White text

        // Add buttons with spacing to the login box
        login.add(Box.createRigidArea(new Dimension(70, 40)));
        login.add(loginButton);
        login.add(Box.createRigidArea(new Dimension(10, 0)));
        login.add(faqButton);
        login.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add login box and spacing to the menu box
        menuBox.add(login);
        menuBox.add(Box.createRigidArea(new Dimension(0, 20)));

        // Load and add Bushnell logo to the menu
        try {
            ImageIcon bushnellLogo = new ImageIcon(getClass().getResource("/resources/VisualRoboticsLogo.png"));
            Image scaledLogo = bushnellLogo.getImage().getScaledInstance(300, -1, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuBox.add(Box.createRigidArea(new Dimension(0, 10)));
            menuBox.add(logoLabel);
            menuBox.add(Box.createRigidArea(new Dimension(0, 50)));    
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Could not find image.");
            errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuBox.add(Box.createRigidArea(new Dimension(0, 10)));
            menuBox.add(errorLabel);
            menuBox.add(Box.createRigidArea(new Dimension(0, 50)));  
        }
        

        // Create a title box with information and add it to the menu
        Box titleBox = Box.createVerticalBox();
        titleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleBox.add(GUI.text("COUNSELING CENTER", 300, 30, 26, Color.WHITE, "left")); // Title
        titleBox.add(GUI.text("755 East 11th Avenue, Eugene, OR 97401 ", 300, 30, 13, Color.WHITE, "left")); // Address
        titleBox.add(Box.createRigidArea(new Dimension(0, 50)));
        menuBox.add(titleBox);

        // Create navigation buttons for sections
        Box buttons = Box.createVerticalBox();
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton appointmentButton = GUI.button("Appointment", 300, 50, 20);
        appointmentButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        appointmentButton.setForeground(Color.WHITE);

        JButton clientInformationButton = GUI.button("Client Information", 300, 50, 20);
        clientInformationButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        clientInformationButton.setForeground(Color.WHITE);

        JButton onlineAdviceButton = GUI.button("Online Advice", 300, 50, 20);
        onlineAdviceButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        onlineAdviceButton.setForeground(Color.WHITE);

        // Add buttons with spacing
        buttons.add(appointmentButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));
        buttons.add(clientInformationButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));
        buttons.add(onlineAdviceButton);
        menuBox.add(buttons);

        // Create and initialize various sub-panels
        // JPanel faqPanel = FAQs.MakeGUI();
        // JPanel appointmentPanel = Appointment.MakeGUI();
        // JPanel clientInformationPanel = ClientInformation.MakeGUI();
        // JPanel onlineAdvicePanel = OnlineAdvice.MakeGUI();
        // JPanel welcomePanel = Welcome.MakeGUI();

        // Create a card panel for dynamic switching between views
        JPanel cardPanel = new JPanel(new CardLayout());
        // JPanel loginPanel = Login.MakeGUI(cardPanel);

        // Add panels to the card panel
        // cardPanel.add(faqPanel, "FAQs");
        // cardPanel.add(appointmentPanel, "appointment");
        // cardPanel.add(clientInformationPanel, "clientInformation");
        // cardPanel.add(onlineAdvicePanel, "onlineAdvice");
        // cardPanel.add(welcomePanel, "Welcome");
        // cardPanel.add(loginPanel, "Login");

        subMenuBox.add(cardPanel);

        // Set initial view to "Login"
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "Login");

        // Add listeners to change the displayed panel on button click
        appointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "appointment");
            }
        });

        clientInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "clientInformation");
            }
        });

        onlineAdviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "onlineAdvice");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Login");
            }
        });

        faqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "FAQs");
            }
        });

        mainPanel.setVisible(true); // Make panel visible
        return mainPanel; // Return main panel
    }
}

