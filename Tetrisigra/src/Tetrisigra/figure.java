package Tetrisigra;

import java.awt.Color;
import java.awt.Graphics;
import static Tetrisigra.polje.sirinaPolja;
import static Tetrisigra.polje.visinaPolja;
import static Tetrisigra.polje.kvadrat;

public class figure {

	//pozicija
	private int x = 4, y = 0;
	//kako hitro pada lik
	private int normalno = 600;
	private int hitro = 50;
	private int delaytime = normalno;
	private long cas;
	
	//polozaj s katerim bomo nadzarovali kam se bo lik premikal
	private int levo_desno = 0;
		
	// nastavimo na false(da se block ustavi na koncu)
	private boolean trkY = false;
	
	private int[][] koordinate;
	private polje polje;
	private Color color;
	
	public figure(int[][] koordinate, polje polje ,Color color) {
		this.koordinate = koordinate;
		this.polje = polje;
		this.color = color;
	}
	
	public void nastaviX(int x) {
		this.x = x;
	}

	public void nastaviY(int y) {
		this.y = y;
	}
	
	public void reset() {
		this.x = 4;
		this.y = 0;
		trkY = false;
	}
	
	public void funkcionalnost() {
		if (trkY) {
			for(int vrstica = 0; vrstica < koordinate.length; vrstica++) {
				for(int stolpec = 0; stolpec < koordinate[0].length; stolpec++) {
					if(koordinate[vrstica][stolpec] != 0) {
						polje.getpolje()[y + vrstica][x + stolpec] = color;
					}
				}
			}
			crta();
			polje.nastaviTrenutniLik();
			return;
		}
		boolean moveX = true;
		if(!(x + levo_desno + koordinate[0].length > 10) && !(x + levo_desno < 0)) {
            
			for (int row = 0; row < koordinate.length; row++) {
                for (int col = 0; col < koordinate[row].length; col++) {
                    if (koordinate[row][col] != 0) {
                        if (polje.getpolje()[y + row][x + levo_desno + col] != null) {
                            moveX = false;
                        }

                    }
                }
            }

            if (moveX) {
                x += levo_desno;
            }
		}
	
		levo_desno = 0;
		if(System.currentTimeMillis() - cas > delaytime) {
			if(!(y + koordinate.length >= visinaPolja)) {
				for(int vrstica = 0; vrstica < koordinate.length; vrstica++) {
					for (int stolpec = 0; stolpec < koordinate[0].length; stolpec++) {
						if(koordinate[vrstica][stolpec] != 0) {
							if(polje.getpolje()[y + 1 + vrstica][x + levo_desno + stolpec] != null) {
								trkY = true;
							}
						}
					}
				}
				if(!trkY) {
					y++;
				}
			} else {
				trkY = true;
			}
			cas = System.currentTimeMillis();	
		}
	}
	
	//unici ce je crta zapolnjena
	private void crta() {
		int spodnjaCrta = polje.getpolje().length - 1;
		for(int zgornjaCrta = polje.getpolje().length - 1; zgornjaCrta > 0; zgornjaCrta--) {
			int stevec = 0;
			for(int stolpec = 0; stolpec < polje.getpolje()[0].length; stolpec++) {
				if(polje.getpolje()[zgornjaCrta][stolpec] != null) {
					stevec++;
				}
				polje.getpolje()[spodnjaCrta][stolpec] = polje.getpolje()[zgornjaCrta][stolpec];
			}
			if(stevec < polje.getpolje()[0].length) {
				spodnjaCrta--;
			}
		}
	}
	
	public void rotacijaLika() {
		int[][] rotiranLik = Tmatrike(koordinate);
		obrnjeneVrstice(rotiranLik);
		//ali se dotakne roba:
		if ((x + rotiranLik[0].length > 10) || (y + rotiranLik.length > 20)) {
			return;
		}
		for (int vrstica = 0; vrstica < rotiranLik.length; vrstica++) {
	           for (int stolpec = 0; stolpec < rotiranLik[vrstica].length; stolpec++) {
	               if (rotiranLik[vrstica][stolpec] != 0) {
	                   if (polje.getpolje()[y + vrstica][x + stolpec] != null) {
	                       return;
	                   }
	               }
	           }
	     }
		koordinate = rotiranLik;
	}
	
	
	//transponirana matrika
	private int[][] Tmatrike(int[][] matrika) {
		int[][] zacasna = new int[matrika[0].length][matrika.length];
		for(int vrstica = 0; vrstica < matrika.length; vrstica ++) {
			for(int stolpec = 0; stolpec < matrika[0].length; stolpec++) {
				zacasna[stolpec][vrstica] = matrika[vrstica][stolpec];
			}
		}
		return zacasna;
	}
	
	private void obrnjeneVrstice(int[][] matrika) {
		int sredina = matrika.length / 2;
		for(int vrstica = 0; vrstica < sredina; vrstica++) {
			int[] zacasna = matrika[vrstica];
			matrika[vrstica] = matrika[matrika.length - vrstica - 1];
			matrika[matrika.length - vrstica -1] = zacasna;
		}
	}
	
	private void oblikaLika(Graphics g) {
		for(int i = 0; i < koordinate.length; i++) {
			for(int j = 0; j < koordinate[0].length; j++) {
				if(koordinate[i][j] != 0) {
					g.setColor(color);
					g.fillRect(j * kvadrat + x * kvadrat,i * kvadrat + y * kvadrat, kvadrat, kvadrat);
				}
		    }
		}
	}
	
	public void abc(Graphics g) {
		oblikaLika(g);
	}
	
	// metoda, ki nam dolocajo kako se premikajo nase figure(liki)
	public void upocasni() {
		delaytime = normalno;
	}
    public void boljHitro() {
    	delaytime = hitro;
    }
    public void Desno() {
    	levo_desno += 1;
    }
    public void Levo() {
    	levo_desno -= 1;
    }
    public Color getColor() {
        return color;
    }
    public int[][] getkoordinate() {
        return koordinate;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
