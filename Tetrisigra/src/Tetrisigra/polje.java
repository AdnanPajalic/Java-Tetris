package Tetrisigra;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class polje extends JPanel implements KeyListener{
	
	
	public static final int sirinaPolja = 10;
	public static final int visinaPolja = 20;
	public static final int kvadrat = 40;
	
	private static int FPS = 60;
	private static int delay = FPS / 1000;
	
	public static int igra = 0;
	public static int pauza = 1;
	public static int konec_igre = 2;
	
	// stanje igre
	private int trenutno = pauza;
	
	private Timer zanka;
	private Color[][] polje = new Color[visinaPolja][sirinaPolja];
	
	//izberemo barve za nase figure
	private Color[] barva = {Color.GREEN, Color.ORANGE, Color.PINK, Color.YELLOW, Color.GRAY, Color.RED, Color.MAGENTA};

	private figure[] liki = new figure[7];

	private figure trenutniLik;
	
	public Color[][] getpolje() {
		return polje;
	}
	
	private Random random;
	
	public polje() {
		random = new Random();
		
		liki[0] = new figure(new int[][] {
			{0,1,0},
			{1,1,1},
		},this, barva[0]);
		
		liki[1] = new figure(new int[][] {
			{1,1,1},
			{1,0,0},
		},this, barva[1]);
		
		liki[2] = new figure(new int[][] {
			{1,1,1,1},
		},this, barva[2]);
		
		liki[3] = new figure(new int[][] {
			{1,1,1},
			{0,0,1},
		},this, barva[3]);
		
		liki[4] = new figure(new int[][] {
			{1,1,0},
			{0,1,1},
		},this, barva[4]);
		
		liki[5] = new figure(new int[][] {
			{0,1,1},
			{1,1,0},
		},this, barva[5]);
		
		liki[6] = new figure(new int[][] {
			{1,1},
			{1,1},
		},this, barva[6]);
		
		trenutniLik = liki[2];
		
		zanka = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				funkcionalnost();
				repaint();
			}

		});
		zanka.start();
	}
	private void funkcionalnost() {
		if(trenutno == igra) {
			trenutniLik.funkcionalnost();

		}
	}

	private void konecIgre() {
		int[][] koordinate = trenutniLik.getkoordinate();
		for(int vrstica = 0; vrstica < koordinate.length; vrstica++) {
			for(int stolpec = 0; stolpec < koordinate[0].length; stolpec++) {
				if(koordinate[vrstica][stolpec] != 0) {
					if(polje[vrstica + trenutniLik.getY()][stolpec + trenutniLik.getX()] != null) {
						trenutno = konec_igre;
					}
				}
			}
		}
	}
	
	private void ozadje(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 900, 840);
		g.setColor(Color.BLACK);
		g.fillRect(400, 0, 900, 840);
	}
	
	private void igralaPovrsina(Graphics g) {
		g.setColor(Color.BLACK);
		for (int row = 0; row <= visinaPolja; row++) {
			g.drawLine(0, kvadrat * row, kvadrat * sirinaPolja ,kvadrat * row);      
	    	}
	
		for (int column = 0; column <= sirinaPolja; column++) {
			g.drawLine(column * kvadrat, 0, column * kvadrat, kvadrat * visinaPolja);      
	    	}
	}
	
	public void nastaviTrenutniLik() {
		trenutniLik = liki[random.nextInt(7)];
		trenutniLik.reset();
		konecIgre(); 
	}
	private void Lik(Graphics g) {
		for(int vrstica = 0; vrstica < polje.length; vrstica++) {
			for(int stolpec = 0; stolpec < polje[0].length; stolpec++) {
				if(polje[vrstica][stolpec] != null) {
					g.setColor(polje[vrstica][stolpec]);
					g.fillRect(stolpec * kvadrat,vrstica * kvadrat, kvadrat, kvadrat);
				}
			}
		}
	}
	private void pauza(Graphics g) {
		
		if(trenutno == pauza){
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString("PAUZA", 80, 400);
		}
	}
	
	private void konec(Graphics g) {
		if(trenutno == konec_igre){
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString("KONEC IGRE", 50, 400);
		}
	}
	
	private void kontrole(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		g.drawString("-Za START pritisni 'SPACE' ", 420, 100);
		g.drawString("-Za pauzo pritisni 'SPACE'", 420, 200);
		g.drawString("-ENTER za ponovno igro", 420, 300);
		g.drawString("-CONTROLS:", 500, 640);
		g.drawRect(550, 700, 50, 50);
		g.drawRect(600, 700, 50, 50);
		g.drawRect(650, 700, 50, 50);
		g.drawRect(600, 650, 50, 50);
		g.drawString("A", 555, 735);
		g.drawString("S", 610, 735);
		g.drawString("D", 660, 735);
		g.drawString("W", 605, 690);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D)g;
		
		ozadje(graphics);
		trenutniLik.abc(graphics);
		Lik(graphics);
		kontrole(graphics);
		igralaPovrsina(graphics);
		pauza(graphics);
		konec(graphics);
		
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_S) {
			trenutniLik.boljHitro();
		}else if(e.getKeyChar() == KeyEvent.VK_D) {
			trenutniLik.Desno();
		}else if(e.getKeyChar() == KeyEvent.VK_A) {
			trenutniLik.Levo();
		}else if(e.getKeyChar() == KeyEvent.VK_W) {
			trenutniLik.rotacijaLika();
		}
		if(trenutno == konec_igre) {
			if(e.getKeyChar() == KeyEvent.VK_ENTER) {
				//ocisti igralno povrsino
				for(int vrstica = 0; vrstica < polje.length; vrstica++) {
					for(int stolpec = 0; stolpec < polje[0].length; stolpec++) {
						polje[vrstica][stolpec] = null;
						
					}
				}
				nastaviTrenutniLik();
				trenutno = igra;
			}
		}
		if(e.getKeyChar() == KeyEvent.VK_SPACE) {
			if(trenutno == igra) {
				trenutno = pauza;
			}else if(trenutno == pauza) {
				trenutno = igra;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_S) {
			trenutniLik.upocasni();
		}	
	}
}
