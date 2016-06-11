package view;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class Driver {
	public static void main(String... args){
		new SnakeGui();
	}
	
//	 public static void main(String[] args) {
//	        // Determine if the GraphicsDevice supports translucency.
//	        GraphicsEnvironment ge = 
//	            GraphicsEnvironment.getLocalGraphicsEnvironment();
//	        GraphicsDevice gd = ge.getDefaultScreenDevice();
//
//	        //If translucent windows aren't supported, exit.
//	        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
//	            System.err.println(
//	                "Translucency is not supported");
//	                System.exit(0);
//	        }
//	        
//	        JFrame.setDefaultLookAndFeelDecorated(true);
//
//	        // Create the GUI on the event-dispatching thread
//	        SwingUtilities.invokeLater(new Runnable() {
//	            @Override
//	            public void run() {
//	            	SnakeGui tw = new SnakeGui();
//
//	                // Set the window to 55% opaque (45% translucent).
//	                tw.setOpacity(0.1f);
//
//	                // Display the window.
//	                tw.setVisible(true);
//	            }
//	        });
//	    }
}
