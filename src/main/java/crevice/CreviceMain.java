package crevice;

import javax.swing.SwingUtilities;

/**
 *
 * @author Loïc Vital
 *
 */

public class CreviceMain {

	public static void main(String[] args) {
		System.out.println("*** Crevice Software ***");
		
		Runnable app = new CreviceApp();
		SwingUtilities.invokeLater(app);
	}

}
