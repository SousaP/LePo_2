package logic;

import java.util.Random;

import logic.Gem.Direction;

public class Board {
	Gem tab[][];
	boolean end;

	public Board() {
		creatTable();
	}

	public Gem[][] getTab(){
		return tab;
	}
	
	
	void creatTable() {
		tab = new Gem[8][8];
		char symbols[] = { 'B', 'G', 'O', 'P', 'R', 'W', 'Y' };
		int pos = 0;
		Random r;
		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++) {
				r = new Random();

				pos = r.nextInt(symbols.length - 1);

				tab[i][a] = new Gem(symbols[pos], i, a);
			}
	}

	public boolean FreeSpace(){
		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++)
				if (tab[i][a] == null)
					return true;
		return false;
	}
	
	
	boolean checkMoves() {

		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++) {
				if (checkPlays(tab[i][a]) != Direction.None)
					return true;
			}

		return false;
	}

	Direction checkPlays(Gem g) {
		int col = g.pos.getCol();
		int lin = g.pos.getLine();
		char comp = g.getSymbol();
		// check combo a esquerda
		if ((tab[col--][lin]).getSymbol() == (tab[col - 2][lin]).getSymbol()
				&& (tab[col--][lin]).getSymbol() == comp)
			return Direction.Left;
		// check direita
		else if ((tab[col++][lin]).getSymbol() == comp
				&& (tab[col++][lin]).getSymbol() == comp)
			return Direction.Right;
		// check cima
		else if ((tab[col][lin--]).getSymbol() == comp
				&& (tab[col][lin--]).getSymbol() == comp)
			return Direction.Top;
		// check baixo
		else if ((tab[col][lin++]).getSymbol() == comp
				&& (tab[col][lin++]).getSymbol() == comp)
			return Direction.Bottom;
		// check meio vertical
		else if ((tab[col][lin++]).getSymbol() == comp
				&& (tab[col][lin--]).getSymbol() == comp)
			return Direction.Vertical;
		// check meio horizontal
		else if ((tab[col++][lin]).getSymbol() == comp
				&& (tab[col--][lin]).getSymbol() == comp)
			return Direction.Horizontal;
		else
			return Direction.None;
	}

	boolean MakePlay(int col, int lin, Direction d) {
		

		return end;
	}

	void fillTab() {
		boolean allfill = false;

		while (!allfill) {
			allfill = true;
			for (int i = 0; i < tab.length; i++)
				for (int a = 0; a < tab.length; a++)
					if (tab[i][a].symbol == ' ')
						if (i > 0) {
							tab[i][a] = tab[i - 1][a];
							tab[i - 1][a] = new Gem(' ', i, a);
							allfill = false;
						} else
							tab[i][a] = new Gem(i, a);

		}
	}
}