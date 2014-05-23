package logic;

import java.util.Random;

import logic.Gem.Direction;

public class Board {
	Gem tab[][];
	boolean end;

	public Board(boolean s) {
		creatTable();
	}

	/*
	 * 
	 * Maquina de estados:
	 * Creat -> Check (se nao houver moves -> CreatTable again)
	 * se houver, MakePlay -> (se for uma jogada decente) Filltab
	 * sempre asiim
	 */
	
	
	
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

	boolean checkMoves() {

		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++) {
				if (checkPlays(tab[i][a], 0, 1) || checkPlays(tab[i][a], 0, -1)
						|| checkPlays(tab[i][a], 1, 0)
						|| checkPlays(tab[i][a], -1, 0))
					return true;
			}

		return false;
	}

	boolean checkPlays(Gem g, int col, int lin) {

		return false;
	}
	
	boolean MakePlay(int col, int lin, Direction d){
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