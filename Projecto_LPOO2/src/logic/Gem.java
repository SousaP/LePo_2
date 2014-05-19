package logic;

import java.util.Random;

public class Gem {
	char symbol;
	int col, lin;
	char symbols[] = { 'B', 'G', 'O', 'P', 'R', 'W', 'Y' };

	Gem(char s, int c, int l) {
		symbol = s;
		col = c;
		lin = l;
	}

	public Gem(int c, int l) {
		col = c;
		lin = l;

		Random r = new Random();
		int pos = r.nextInt(symbols.length - 1);

		symbol = symbols[pos];
	}
}