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
        int lin = g.getPos().getLine();
        int col = g.getPos().getCol();
        char comp = g.getSymbol();
       
        // check combo para cima
        if (lin - 2 >= 0 && (tab[lin - 1][col]).getSymbol() == comp
                        && (tab[lin - 2][col]).getSymbol() == comp)
                return Direction.Top;
        // check baixo
        else if (lin + 2 < tab.length
                        && (tab[lin + 1][col]).getSymbol() == comp
                        && (tab[lin + 2][col]).getSymbol() == comp)
                return Direction.Bottom;
        // check esquerda
        else if (col - 2 >= 0 && (tab[lin][col - 1]).getSymbol() == comp
                        && (tab[lin][col - 2]).getSymbol() == comp)
                return Direction.Left;
        // check direita
        else if (col + 2 < tab.length
                        && (tab[lin][col + 1]).getSymbol() == comp
                        && (tab[lin][col + 2]).getSymbol() == comp)
                return Direction.Right;
        // check meio horizontal
        else if (col + 1 < tab.length && col - 1 >= 0
                        && (tab[lin][col + 1]).getSymbol() == comp
                        && (tab[lin][col - 1]).getSymbol() == comp)
                return Direction.Horizontal;
        // check meio vertical
        else if (lin + 1 < tab.length && lin - 1 >= 0
                        && (tab[lin + 1][col]).getSymbol() == comp
                        && (tab[lin - 1][col]).getSymbol() == comp)
                return Direction.Vertical;
        else
                return Direction.None;
}

int deleteSequencesLine(int lin) {
        int t = 0;
        Vector<Cell> toBeDeleted = new Vector<Cell>();
        char s;

        for (int i = 0; i <= (tab.length - 3);) {
                s = tab[lin][i].getSymbol();
                if (tab[lin][i].getSymbol() == tab[lin][i + 1].getSymbol()
                                && tab[lin][i].getSymbol() == tab[lin][i + 2].getSymbol()) {
                        toBeDeleted.add(new Cell(lin, i));
                        toBeDeleted.add(new Cell(lin, i + 1));
                        toBeDeleted.add(new Cell(lin, i + 2));
                        if (!(i + 3 < tab.length)) {
                                break;
                        }
                        i += 3;
                        while (tab[lin][i].getSymbol() == s) {
                                toBeDeleted.add(new Cell(lin, i));
                                i++;
                                if (!(i < tab.length)) {
                                        break;
                                }
                        }
                } else
                        i++;
        }

        for (int j = 0; j < toBeDeleted.size(); j++) {
                tab[(toBeDeleted.get(j)).getLine()][(toBeDeleted.get(j)).getCol()] = null;
        }
        t += toBeDeleted.size();
        toBeDeleted.clear();
        return t;
}

int deleteSequencesCol(int col) {
        int t = 0;
        Vector<Cell> toBeDeleted = new Vector<Cell>();
        char s;

        for (int i = 0; i <= (tab.length - 3);) {
                s = tab[i][col].getSymbol();
                if (tab[i][col].getSymbol() == tab[i + 1][col].getSymbol()
                                && tab[i][col].getSymbol() == tab[i + 2][col].getSymbol()) {
                        toBeDeleted.add(new Cell(i, col));
                        toBeDeleted.add(new Cell(i + 1, col));
                        toBeDeleted.add(new Cell(i + 2, col));
                        if (!(i + 3 < tab.length)) {
                                break;
                        }

                        i += 3;

                        while (tab[i][col].getSymbol() == s) {
                                toBeDeleted.add(new Cell(i, col));
                                i++;
                                if (!(i < tab.length)) {
                                        break;
                                }
                        }
                } else
                        i++;
        }

        for (int i = 0; i < toBeDeleted.size(); i++) {
                tab[(toBeDeleted.get(i)).getLine()][(toBeDeleted.get(i)).getCol()] = null;
        }
        t = toBeDeleted.size();
        toBeDeleted.clear();
        return t;
}

public int MakePlay(Gem g1, Gem g2) {
        swapD(g1, g2);
        Direction d1, d2;
        int ret = 0;

        d1 = checkPlays(tab[g1.getPos().line][g1.getPos().colune]);
        d2 = checkPlays(tab[g2.getPos().line][g2.getPos().colune]);

        if (d1 == Direction.Horizontal || d1 == Direction.Left
                        || d1 == Direction.Right) {
                ret += deleteSequencesLine(g1.getPos().getLine());
        } else if (d1 != Direction.None) {
                ret += deleteSequencesCol(g1.getPos().getCol());
        }

        if (d2 == Direction.Horizontal || d2 == Direction.Left
                        || d2 == Direction.Right) {
                ret += deleteSequencesLine(g2.getPos().getLine());
        } else if (d2 != Direction.None) {
                ret += deleteSequencesCol(g2.getPos().getCol());
        }

        if (d2 == Direction.None && d1 == Direction.None) {
                swapD(g1, g2);
                return 0;
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
	public void swapD(Gem g1, Gem g2) {
		Cell c = g1.getPos();
		
		g1.pos = g2.getPos();
		g2.pos = c;
	}
	
	public void SetPos(int col, int lin, Gem g){
		tab[col][lin] = g;
	}
}