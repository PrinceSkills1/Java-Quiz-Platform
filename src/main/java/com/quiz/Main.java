package com.quiz;

import javax.swing.SwingUtilities;

// Main entry point for the Java Quiz Platform Swing application
public class Main {

    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures that the GUI is created
        // and updated on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the initial login window
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
