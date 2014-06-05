package logic;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Gem {
	Image GImage;
	char symbol;
	Cell pos;
	char symbols[] = { 'B', 'G', 'O', 'P', 'R', 'W', 'Y' };

	Gem(char s, int c, int l) {
		symbol = s;
		pos = new Cell(l, c);
		GImage = Image();
	}
	public Image getImage(){
		return GImage;
	}
	
	void resetGem(){
		this.symbol = ' ';
	}

	public Gem(int c, int l) {
		pos = new Cell(l, c);

		Random r = new Random();
		int pos = r.nextInt(symbols.length - 1);

		symbol = symbols[pos];
		GImage = Image();
	}

	public enum Direction {
		Left, Right, Top, Bottom, Vertical, Horizontal, None
	}
	
	char getSymbol() {
		return symbol;
	}

	private Image Image() {
		
		ImageIcon temp = null;
		if (symbol == 'B')
			temp = new ImageIcon(this.getClass().getResource(
					"/gui/resources/gemBlue.png"));
		else if (symbol == 'G')
			temp = new ImageIcon(this.getClass().getResource(
					"/gui/resources/gemGreen.png"));
		else if (symbol == 'O')
			temp = new ImageIcon(this.getClass().getResource(
					"/gui/resources/gemOrange.png"));
		else if (symbol == 'P')
			temp = new ImageIcon(this.getClass().getResource(
					"/gui/resources/gemPurple.png"));
		else if (symbol == 'R')
			temp = new ImageIcon(this.getClass().getResource(
					"/gui/resources/gemRed.png"));
		else if (symbol == 'W')
			temp = new ImageIcon(this.getClass().getResource(
					"/gui/resources/gemWhite.png"));
		else if (symbol == 'Y')
			temp = new ImageIcon(this.getClass().getResource(
					"/gui/resources/gemYellow.png"));

		return temp.getImage();
	}

}