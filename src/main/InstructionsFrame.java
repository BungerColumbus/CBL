package main;

import javax.swing.JFrame;

public class InstructionsFrame extends JFrame {
    
    InstructionsPanel instructionsJPanel;
    
    public InstructionsFrame(int width, int height) {
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("How to play?");

        instructionsJPanel = new InstructionsPanel();
        this.add(instructionsJPanel);
        this.setVisible(true);
        //this.setLayout(new BoxLayout(instructionsJPanel, BoxLayout.Y_AXIS));

    }

}
