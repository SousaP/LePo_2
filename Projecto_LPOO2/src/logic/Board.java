package logic;

import java.awt.Image;
import java.util.Random;
import java.util.Vector;

import logic.Gem.Direction;

/**
 * Classe Board, engloba todos os elementos do jogo
 */
public class Board {
	Gem tab[][];
	boolean end;

	/**
	 * Construtor da classe
	 */
	public Board() {
		creatTable();
	}
	
	public Board(char c[][]){
		tab = new Gem[8][8];
		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++) {
				tab[i][a] = new Gem(c[i][a], i, a);
			}
	}
	
	

	/**
	 * Fun��o que retorna o tabuleiro de jogo
	 */
	public Gem[][] getTab() {
		return tab;
	}

	/**
	 * Inicializa o tabuleiro de jogo, inserindo no mesmo gemas criadas de uma
	 * forma aleat�ria
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

	/**
	 * Fun��o que procura a exist�ncia de algum espa�o vazio no tabuleiro
	 */
	public boolean FreeSpace() {
		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++)
				if (tab[i][a] == null)
					return true;
		return false;
	}

	/**
	 * Verifica se existe alguma jogada possivel no jogo atual
	 */
	public boolean checkMoves() {

		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++) {
				if (checkPlays(tab[i][a]) != Direction.None)
					return true;
			}

		return false;
	}

	public Gem Tip() {

		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++) {
				if (checkPlays(tab[i][a]) != Direction.None)
					return tab[i][a];
			}

		return null;
	}

	/**
	 * Verifica se a Gem g faz alguma combina��o e retorna a sua dire��o
	 * 
	 * @param Gem
	 *            g: pe�a a analisar
	 * 
	 * @return Top, Bottom, Left, Right para combina��es para cima, para baixo,
	 *         para a esquerda e para a direita respetivamente. Horizontal e
	 *         Vertical para combina��es nos sentidos referidos e coma pe�a g no
	 *         centro
	 * 
	 */
	Direction checkPlays(Gem g) {
		int lin = g.getPos().getLine();
		int col = g.getPos().getCol();
		char comp = g.getSymbol();

		// check combo para cima
		if (lin - 3 >= 0 && (tab[lin - 2][col]).getSymbol() == comp
				&& (tab[lin - 3][col]).getSymbol() == comp)
			return Direction.Top;
		// check baixo
		else if (lin + 3 < tab.length
				&& (tab[lin + 2][col]).getSymbol() == comp
				&& (tab[lin + 3][col]).getSymbol() == comp)
			return Direction.Bottom;
		// check esquerda
		else if (col - 3 >= 0 && (tab[lin][col - 2]).getSymbol() == comp
				&& (tab[lin][col - 3]).getSymbol() == comp)
			return Direction.Left;
		// check direita
		else if (col + 3 < tab.length
				&& (tab[lin][col + 2]).getSymbol() == comp
				&& (tab[lin][col + 3]).getSymbol() == comp)
			return Direction.Right;
		
		// check quadrado
		else if (col + 1 < tab.length
				&& col - 1 >= 0
				&& lin + 1 < tab.length
				&& lin - 1 >= 0
				&& ((tab[lin + 1][col + 1]).getSymbol() == comp
						&& (tab[lin + 1][col - 1]).getSymbol() == comp || (tab[lin - 1][col + 1])
						.getSymbol() == comp
						&& (tab[lin - 1][col - 1]).getSymbol() == comp

				))

			return Direction.Horizontal;
		
		else if (lin + 1 < tab.length && lin - 1 >= 0
				&& (tab[lin + 1][col]).getSymbol() == comp
				&& (tab[lin - 1][col]).getSymbol() == comp)
			return Direction.Vertical;
		// esquerda baixo
		else if (col - 1 >= 0 && lin + 2 < tab.length) {
			if ((tab[lin + 1][col - 1]).getSymbol() == comp
					&& (tab[lin + 2][col - 1]).getSymbol() == comp)
				return Direction.Left;
		}
		// esquerda cima
		else if (col - 1 >= 0 && lin - 2 >= 0) {
			if ((tab[lin - 1][col - 1]).getSymbol() == comp
					&& (tab[lin - 2][col - 1]).getSymbol() == comp)
				return Direction.Left;
		}
		// direita baixo
		else if (col + 1 < tab.length && lin + 2 < tab.length) {
			if ((tab[lin + 1][col + 1]).getSymbol() == comp
					&& (tab[lin + 2][col + 1]).getSymbol() == comp)
				return Direction.Right;
		}
		// direita cima
		else if (col + 1 < tab.length && lin - 2 >= 0) {
			if ((tab[lin - 1][col + 1]).getSymbol() == comp
					&& (tab[lin - 2][col + 1]).getSymbol() == comp)
				return Direction.Right;
		}
		// cima direita
		else if (lin - 1 >= 0 && col + 2 < tab.length
				&& (tab[lin - 1][col + 1]).getSymbol() == comp
				&& (tab[lin - 1][col + 2]).getSymbol() == comp)
			return Direction.Vertical;
		// cima esquerda
		else if (lin - 1 >= 0 && col - 2 >= 0) {
			if ((tab[lin - 1][col - 1]).getSymbol() == comp
					&& (tab[lin - 2][col - 2]).getSymbol() == comp)
				return Direction.Vertical;
		}
		// baixo direita
		else if (lin + 1 < tab.length && col + 2 < tab.length
				&& (tab[lin + 1][col + 1]).getSymbol() == comp
				&& (tab[lin + 1][col + 2]).getSymbol() == comp)
			return Direction.Vertical;
		// baixo esquerda
		else if (lin + 1 < tab.length && col - 2 >= 0) {
			if ((tab[lin + 1][col - 1]).getSymbol() == comp
					&& (tab[lin + 1][col - 2]).getSymbol() == comp)
				return Direction.Vertical;
		}

		return Direction.None;
	}

	/**
	 * Processa a tentativa de troca entre duas pe�as
	 * 
	 * @param Gem
	 *            g1, Gem g2: pe�as a trocar
	 * 
	 * @return 0 se a combina��o n�o � possivel, outro n�mero correspondendo ao
	 *         montante de pe�as combinadas se a jogada for valida
	 * 
	 */
	public int MakePlay(Gem g1, Gem g2) {
		if (g1 == null || g2 == null)
			return 0;
		int points = 0;
		swap(g1, g2);
		Vector<Cell> delete = new Vector<Cell>();

		points += CheckLine(g1.getPos().line, g1.getPos().colune, delete);

		points += CheckCol(g1.getPos().line, g1.getPos().colune, delete);

		points += CheckLine(g2.getPos().line, g2.getPos().colune, delete);

		points += CheckCol(g2.getPos().line, g2.getPos().colune, delete);

		Remove(delete);

		return points;
	}

	/**
	 * "Limpa" uma linha apagando todas as combina��es na mesma
	 * 
	 * @param int lin: indice da linha a analisar
	 * 
	 * @return n�mero de pe�as da combina��o apagada
	 */
	int deleteSequencesLine(int lin) {

		char symbol = ' ';
		int p = 0;
		Vector<Cell> delete = new Vector<Cell>();

		for (int i = 0; i < tab.length; i++) {
			if (tab[lin][i] == null) {
				symbol = ' ';
				if (delete.size() > 2) {
					p += points(delete.size());
					Remove(delete);
					delete.removeAllElements();
				} else {
					delete.removeAllElements();
				}

			}

			else if (tab[lin][i].getSymbol() != symbol) {
				if (delete.size() > 2) {
					p += points(delete.size());
					Remove(delete);
					delete.removeAllElements();
				} else {
					delete.removeAllElements();
				}
				delete.add(tab[lin][i].pos);
				symbol = tab[lin][i].symbol;
			} else if (tab[lin][i].symbol == symbol) {
				delete.add(tab[lin][i].pos);
			}
		}

		if (delete.size() > 2) {
			p += points(delete.size());
			Remove(delete);
			return p;
		}

		return p;
	}

	/**
	 * "Limpa" uma coluna apagando todas as combina��es na mesma
	 * 
	 * @param int col: indice da coluna a analisar
	 * 
	 * @return n�mero de pe�as da combina��o apagada
	 */
	int deleteSequencesCol(int col) {
		char symbol = ' ';
		int p = 0;
		Vector<Cell> delete = new Vector<Cell>();

		for (int i = 0; i < tab.length; i++) {
			if (tab[i][col] == null) {
				symbol = ' ';
				if (delete.size() > 2) {
					p += points(delete.size());
					Remove(delete);
					delete.removeAllElements();
				} else {
					delete.removeAllElements();
				}
			}

			else if (tab[i][col].getSymbol() != symbol) {
				if (delete.size() > 2) {
					p += points(delete.size());
					Remove(delete);
					delete.removeAllElements();
				} else {
					delete.removeAllElements();
				}
				delete.add(tab[i][col].pos);
				symbol = tab[i][col].symbol;
			} else if (tab[i][col].symbol == symbol) {
				delete.add(tab[i][col].pos);

			}
		}

		if (delete.size() > 2) {
			p += points(delete.size());
			Remove(delete);
			return p;
		}

		return p;
	}

	/**
	 * Verifica se uma certa pe�a est� envolvida em alguma combina��o numa linha
	 * 
	 * @param int col, int line: coordenadas da pe�a a anlisar
	 * @param Vector
	 *            <Cell> d: contentor para retornar as pe�as a destruir
	 * 
	 * @return 0 se a combina��o n�o � possivel, outro n�mero correspondendo ao
	 *         montante de pe�as combinadas se existir combina��o
	 */
	private int CheckLine(int line, int col, Vector<Cell> d) {
		char symbol = tab[line][col].symbol;

		Vector<Cell> delete = new Vector<Cell>();
		int cont = 0;
		for (int i = 0; i < tab.length; i++) {

			if (tab[line][i] == null)
				break;
			else if (tab[line][i].symbol == symbol) {
				cont++;
				delete.add(tab[line][i].pos);
			}

			else if (cont > 2) {
				d.addAll(delete);
				return points(cont);
			} else if (cont > 0) {
				delete.removeAllElements();
				cont = 0;
				i--;
			}
		}

		if (delete.size() > 2) {
			d.addAll(delete);
			return points(cont);
		}

		return 0;
	}

	/**
	 * Calcula pontos de uma jogada
	 * 
	 * @param int combo: n�mero de pe�as destruidas pela jogada
	 * 
	 * @return numero de pontos de uma jogada
	 */
	private int points(int combo) {
		if (combo == 3)
			return 10;
		else if (combo == 4)
			return 15;
		else if (combo > 4)
			return 20;

		return 0;
	}

	/**
	 * Verifica se uma certa pe�a est� envolvida em alguma combina��o numa
	 * coluna
	 * 
	 * @param int col, int line: coordenadas da pe�a a anlisar
	 * @param Vector
	 *            <Cell> d: contentor para retornar as pe�as a destruir
	 * 
	 * @return 0 se a combina��o n�o � possivel, outro n�mero correspondendo ao
	 *         montante de pe�as combinadas se existir combina��o
	 */
	private int CheckCol(int line, int col, Vector<Cell> d) {
		char symbol = tab[line][col].symbol;
		int cont = 0;
		Vector<Cell> delete = new Vector<Cell>();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i][col] == null)
				break;
			if (tab[i][col].symbol == symbol) {
				cont++;
				delete.add(tab[i][col].pos);

			} else if (cont > 2) {
				d.addAll(delete);
				return points(cont);
			} else if (cont > 0) {
				delete.removeAllElements();
				cont = 0;
				i--;
			}
		}

		if (delete.size() > 2) {
			d.addAll(delete);
			return points(cont);
		}

		return 0;
	}

	/**
	 * Destroi as pe�as passadas como parametro
	 * 
	 * @param Vector
	 *            <Cell> d: pe�as a destruir
	 * 
	 */
	private void Remove(Vector<Cell> d) {

		for (int i = 0; i < d.size();) {
			tab[d.elementAt(i).line][d.elementAt(i).colune] = null;
			d.remove(i);
		}

	}


	/**
	 * Varre o tabuleiro limpando todas as combina��es
	 * 
	 * @return n�mero de pe�as destruidas
	 * 
	 */
	public int sweepTab() {
		int t = 0;
		for (int l = 0; l < tab.length; l++) {
			t += deleteSequencesLine(l);
		}
		for (int c = 0; c < tab.length; c++) {
			t += deleteSequencesCol(c);
		}
		return t;
	}

	/**
	 * Preenche todas as casas vazias do tabuleiro
	 * 
	 */
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

	/**
	 * Troca todos os elementos de duas pe�as
	 * 
	 * @param pe�as
	 *            a trocar de elementos
	 * 
	 */
	public void swap(Gem s1, Gem s2) {
		char c1 = s1.symbol, c2 = s2.symbol;
		Image i1 = s1.GImage, i2 = s2.GImage;
		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++)
				if (tab[i][a] == s1) {
					tab[i][a].symbol = c2;
					tab[i][a].GImage = i2;
				} else if (tab[i][a] == s2) {
					tab[i][a].symbol = c1;
					tab[i][a].GImage = i1;
				}

	}



	/**
	 * Coloca uma pe�a numa posi��o do tabuleiro
	 * 
	 * @param int col, int lin: coordenadas do tabuleiro
	 * 
	 * @param Gem
	 *            g: pe�a a colocar nas coordenadas passadas
	 * 
	 */
	public void SetPos(int lin, int col, Gem g) {
		if (g == null)
			tab[lin][col] = g;
		else {
			tab[lin][col] = new Gem(g.symbol, lin, col);

		}
	}
}