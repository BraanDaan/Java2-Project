package com.bushnell;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bushnell.screens.home.HomeScreen;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("TermProject");
        HomeScreen home = new HomeScreen();

        String jarPath = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File jarFile = new File(jarPath);
        String jarDirectoryPath = jarFile.getParent();

        JPanel homePanel = home.MakeGUI(jarDirectoryPath);
        frame.add(homePanel);
        frame.pack();   
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
