package logic;

import java.awt.Image;
import java.util.Random;
import java.util.Vector;

import logic.Gem.Direction;

public class Board {
	Gem tab[][];
	boolean end;

	public Board() {
		creatTable();
	}

	public Gem[][] getTab() {
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

	public boolean FreeSpace() {
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
		int lin = g.pos.getLine();
		int col = g.pos.getCol();
		char comp = g.getSymbol();
		// check combo a esquerda
		if (lin - 2 >= 0 && (tab[lin - 1][col]).getSymbol() == comp
				&& (tab[lin - 2][col]).getSymbol() == comp)
			return Direction.Left;
		// check direita
		else if (lin + 2 < tab.length
				&& (tab[lin + 1][col]).getSymbol() == comp
				&& (tab[lin + 2][col]).getSymbol() == comp)
			return Direction.Right;
		// check cima
		else if (col - 2 >= 0 && (tab[lin][col - 1]).getSymbol() == comp
				&& (tab[lin][col - 2]).getSymbol() == comp)
			return Direction.Top;
		// check baixo
		else if (col + 2 < tab.length
				&& (tab[lin][col + 1]).getSymbol() == comp
				&& (tab[lin][col + 2]).getSymbol() == comp)
			return Direction.Bottom;
		// check meio vertical
		else if (col + 1 < tab.length && col - 1 >= 0
				&& (tab[lin][col + 1]).getSymbol() == comp
				&& (tab[lin][col - 1]).getSymbol() == comp)
			return Direction.Vertical;
		// check meio horizontal
		else if (lin + 1 < tab.length && lin - 1 >= 0
				&& (tab[lin + 1][col]).getSymbol() == comp
				&& (tab[lin - 1][col]).getSymbol() == comp)
			return Direction.Horizontal;
		else
			return Direction.None;
	}

	int deleteSequencesLine(int lin) {
		int t = 0;
		Vector<Cell> toBeDeleted = new Vector<Cell>();

		for (int i = 0; i < (tab.length - 3); i++) {
			if (tab[lin][i].getSymbol() == tab[lin][i + 1].getSymbol()
					&& tab[lin][i].getSymbol() == tab[lin][i + 2].getSymbol()) {
				toBeDeleted.add(new Cell(lin, i));
				toBeDeleted.add(new Cell(lin, i++));
				toBeDeleted.add(new Cell(lin, i++));
				if (!(i + 3 < tab.length)) {
					break;
				}
				char s = tab[lin][i].getSymbol();
				i++;
				while (tab[lin][i].getSymbol() == s) {
					toBeDeleted.add(new Cell(lin, i));
					i++;
					if (!(i < tab.length)) {
						break;
					}
				}
			}
		}

		for (int i = 0; i < toBeDeleted.size(); i++) {
			tab[(toBeDeleted.get(i)).getLine()][(toBeDeleted.get(i)).getCol()] = null;
		}
		t += toBeDeleted.size();
		toBeDeleted.clear();
		return t;
	}

	int deleteSequencesCol(int col) {
		int t = 0;
		Vector<Cell> toBeDeleted = new Vector<Cell>();

		for (int i = 0; i < (tab.length - 3); i++) {
			if (tab[i][col].getSymbol() == tab[i + 1][col].getSymbol()
					&& tab[i][col].getSymbol() == tab[i + 2][col].getSymbol()) {
				toBeDeleted.add(new Cell(i, col));
				toBeDeleted.add(new Cell(i++, col));
				toBeDeleted.add(new Cell(i++, col));
				if (!(i + 3 < tab.length)) {
					break;
				}
				char s = tab[i][col].getSymbol();
				i++;
				while (tab[i][col].getSymbol() == s) {
					toBeDeleted.add(new Cell(i, col));
					i++;
					if (!(i < tab.length)) {
						break;
					}
				}
			}
		}

		for (int i = 0; i < toBeDeleted.size(); i++) {
			tab[(toBeDeleted.get(i)).getLine()][(toBeDeleted.get(i)).getCol()] = null;
		}
		t = toBeDeleted.size();
		toBeDeleted.clear();
		return t;
	}

	public int MakePlay(Gem temp, Gem g2) {
		int col = temp.getPos().getCol();
		int lin = temp.getPos().getLine();
		Direction d = null, d1, d2;
		int ret = 0;

		if (g2.getPos().getLine() == (lin - 1))
			d = Direction.Top;
		else if (g2.getPos().getLine() == (lin + 1))
			d = Direction.Bottom;
		else if (g2.getPos().getCol() == (col - 1))
			d = Direction.Left;
		else if (g2.getPos().getCol() == (col + 1))
			d = Direction.Right;

		if (d == Direction.Bottom) {
			tab[lin][col] = tab[lin + 1][col];
			tab[lin + 1][col] = temp;

			d1 = checkPlays(tab[lin][col]);
			d2 = checkPlays(tab[lin + 1][col]);

			if (d1 == Direction.Horizontal || d1 == Direction.Left
					|| d1 == Direction.Right) {
				ret += deleteSequencesLine(lin);
			} else if (d1 != Direction.None) {
				ret += deleteSequencesCol(col);
			}

			if (d2 == Direction.Horizontal || d2 == Direction.Left
					|| d2 == Direction.Right) {
				ret += deleteSequencesLine(lin + 1);
			} else if (d2 != Direction.None) {
				ret += deleteSequencesCol(col);
			}
			if (d2 == Direction.None && d1 == Direction.None) {
				tab[lin + 1][col] = tab[lin][col];
				tab[lin][col] = temp;
				return 0;
			}

		}

		else if (d == Direction.Top) {
			tab[lin][col] = tab[lin - 1][col];
			tab[lin - 1][col] = temp;

			d1 = checkPlays(tab[lin][col]);
			d2 = checkPlays(tab[lin - 1][col]);

			if (d1 == Direction.Horizontal || d1 == Direction.Left
					|| d1 == Direction.Right) {
				ret += deleteSequencesLine(lin);
			} else if (d1 != Direction.None) {
				ret += deleteSequencesCol(col);
			}

			if (d2 == Direction.Horizontal || d2 == Direction.Left
					|| d2 == Direction.Right) {
				ret += deleteSequencesLine(lin - 1);
			} else if (d2 != Direction.None) {
				ret += deleteSequencesCol(col);
			}
			if (d2 == Direction.None && d1 == Direction.None) {
				tab[lin - 1][col] = tab[lin][col];
				tab[lin][col] = temp;
				return 0;
			}

		}

		else if (d == Direction.Right) {
			tab[lin][col] = tab[lin][col + 1];
			tab[lin][col + 1] = temp;

			d1 = checkPlays(tab[lin][col]);
			d2 = checkPlays(tab[lin][col + 1]);

			if (d1 == Direction.Horizontal || d1 == Direction.Left
					|| d1 == Direction.Right) {
				ret += deleteSequencesLine(lin);
			} else if (d1 != Direction.None) {
				ret += deleteSequencesCol(col);
			}

			if (d2 == Direction.Horizontal || d2 == Direction.Left
					|| d2 == Direction.Right) {
				ret += deleteSequencesLine(lin);
			} else if (d2 != Direction.None) {
				ret += deleteSequencesCol(col + 1);
			}
			if (d2 == Direction.None && d1 == Direction.None) {
				tab[lin][col + 1] = tab[lin][col];
				tab[lin][col] = temp;
				return 0;
			}

		}

		else if (d == Direction.Left) {
			tab[lin][col] = tab[lin][col - 1];
			tab[lin][col - 1] = temp;

			d1 = checkPlays(tab[lin][col]);
			d2 = checkPlays(tab[lin][col - 1]);

			if (d1 == Direction.Horizontal || d1 == Direction.Left
					|| d1 == Direction.Right) {
				ret += deleteSequencesLine(lin);
			} else if (d1 != Direction.None) {
				ret += deleteSequencesCol(col);
			}

			if (d2 == Direction.Horizontal || d2 == Direction.Left
					|| d2 == Direction.Right) {
				ret += deleteSequencesLine(lin);
			} else if (d2 != Direction.None) {
				ret += deleteSequencesCol(col - 1);
			}
			if (d2 == Direction.None && d1 == Direction.None) {
				tab[lin][col - 1] = tab[lin][col];
				tab[lin][col] = temp;
				return 0;
			}

		}

		return ret;
	}

	int sweepTab() {
		int t = 0;
		for (int l = 0; l < tab.length; l++) {
			t += deleteSequencesLine(l);
		}
		for (int c = 0; c < tab.length; c++) {
			t += deleteSequencesCol(c);
		}
		return t;
	}

	void fillTab() {
		boolean allfill = false;

		while (!allfill) {
			allfill = true;
			for (int i = 0; i < tab.length; i++)
				for (int a = 0; a < tab.length; a++)
					if (tab[i][a] == null)
						if (i > 0) {
							tab[i][a] = tab[i - 1][a];
							tab[i - 1][a] = new Gem(' ', i, a);
							allfill = false;
						} else
							tab[i][a] = new Gem(i, a);

		}
	}

	public void swap(Gem s1, Gem s2) {
		char c1 = s1.symbol , c2 = s2.symbol;
		Image i1 = s1.GImage, i2 = s2.GImage;
		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++)
				if (tab[i][a] == s1) {
					tab[i][a].symbol = c2;
					tab[i][a].GImage = i2;
				} else if (tab[i][a] == s2)
				{
					tab[i][a].symbol = c1;
					tab[i][a].GImage = i1;
				}

	}
	
	public void SetPos(int col, int lin, Gem g){
		tab[col][lin] = g;
	}
}