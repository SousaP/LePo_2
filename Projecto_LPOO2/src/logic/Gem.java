package logic;

import java.util.Random;

public class Gem {
	char symbol;
	Cell pos;
	char symbols[] = { 'B', 'G', 'O', 'P', 'R', 'W', 'Y' };

	Gem(char s, int c, int l) {
		symbol = s;
		pos = new Cell(l,c);
	}

	public Gem(int c, int l) {
		pos = new Cell(l,c);
		
		Random r = new Random();
		int pos = r.nextInt(symbols.length - 1);

		symbol = symbols[pos];
	}
	
	public enum Direction {
		Left, Right, Top, Bot
	}
}