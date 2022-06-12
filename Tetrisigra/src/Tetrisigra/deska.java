package Tetrisigra;

import java.awt.Dimension;

import javax.swing.JFrame;

public class deska {
	//public static final int sirina = 700, visina = 900;
	private static polje polje;
	
	public static void main(String[] args) {
		final int sirina = 900;
		final int visina = 840;
		JFrame	frame	= new JFrame("Tetris");
		frame.setSize(new Dimension(sirina, visina));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		polje = new polje();
		frame.add(polje);
		frame.addKeyListener(polje);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
