package blackJackApp;


import java.util.*;

import javax.swing.SwingUtilities;

import view.*;

// Group Serial Number: 1

// Ahigad Genish  316228022
// Omer Sela      316539535	
// Shir Cohen     314624040
// Almog Sharoni  208611764 
// Yakov Avitan   205517089

public class MainClass {
	
	   // Main Class, running application
	   public static void main(String[] args) {

		   SwingUtilities.invokeLater(new Runnable() {
			   public void run() {
				   new MainWindow();
			   }
		   });

		}
}
