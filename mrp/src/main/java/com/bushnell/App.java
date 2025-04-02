package com.bushnell;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bushnell.screens.home.HomeScreen;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("TermProject");
        HomeScreen home = new HomeScreen();
        JPanel homePanel = home.MakeGUI();
        frame.add(homePanel);
        frame.pack();   
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
