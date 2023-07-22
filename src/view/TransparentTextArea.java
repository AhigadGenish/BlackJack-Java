package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextArea;

public class TransparentTextArea extends JTextArea {
	
    private static final long serialVersionUID = 1L;
    
    public TransparentTextArea() {
        setOpaque(false); // Make the text area transparent
        
        // Set the desired font size
        Font font = getFont();
        setFont(font.deriveFont(font.getSize() + 4f)); // Increase the font size by 4 points
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Set the background color with alpha value
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);
    }
}