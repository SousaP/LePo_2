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

	/**
	 * Função que retorna o tabuleiro de jogo
	 */
	public Gem[][] getTab() {
		return tab;
	}

	/**
	 * Inicializa o tabuleiro de jogo, inserindo no mesmo gemas criadas de uma
	 * forma aleatória
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
	 * Função que procura a existência de algum espaço vazio no tabuleiro
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

	/**
	 * Verifica se a Gem g faz alguma combinação e retorna a sua direção
	 * 
	 * @param Gem
	 *            g: peça a analisar
	 * 
	 * @return Top, Bottom, Left, Right para combinações para cima, para baixo,
	 *         para a esquerda e para a direita respetivamente. Horizontal e
	 *         Vertical para combinações nos sentidos referidos e coma peça g no
	 *         centro
	 * 
	 */
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

	/**
	 * Processa a tentativa de troca entre duas peças
	 * 
	 * @param Gem
	 *            g1, Gem g2: peças a trocar
	 * 
	 * @return 0 se a combinação não é possivel, outro número correspondendo ao
	 *         montante de peças combinadas se a jogada for valida
	 * 
	 */
	public int MakePlay(Gem g1, Gem g2) {
		if (g1 == null || g2 == null)
			return 0;
		int points = 0;
		swap(g1, g2);
		char s1 = g1.symbol;

		char s2 = g2.symbol;
		Vector<Cell> delete = new Vector<Cell>();

		points += CheckLine(g1.getPos().line, g1.getPos().colune, delete);

		points += CheckCol(g1.getPos().line, g1.getPos().colune, delete);

		points += CheckLine(g2.getPos().line, g2.getPos().colune, delete);

		points += CheckCol(g2.getPos().line, g2.getPos().colune, delete);

		Remove(delete);
		
		pushNullUp();
		fillTab2();

		return points;
	}

	/**
	 * "Limpa" uma linha apagando todas as combinações na mesma
	 * 
	 * @param int lin: indice da linha a analisar
	 * 
	 * @return número de peças da combinação apagada
	 */
	int deleteSequencesLine(int lin) {
		char symbol = ' ';

		Vector<Cell> delete = new Vector<Cell>();
		Vector<Cell> d = new Vector<Cell>();
		int cont = 0, total = 0;
		for (int i = 0; i < tab.length; i++) {
			if (tab[lin][i] == null){
				if (cont > 2) {
					d.addAll(delete);
					delete.removeAllElements();
					total += cont;
					cont = 0;
					symbol = ' ';
				} else if (cont >= 0) {
					delete.removeAllElements();
					cont = 0;
					symbol = ' ';
				}
				break;
			}
			if (symbol == ' ')
				symbol = tab[lin][i].symbol;

			if (tab[lin][i].symbol == symbol) {
				cont++;
				delete.add(tab[lin][i].pos);
			}

			else if (cont > 2) {
				d.addAll(delete);
				delete.removeAllElements();
				total += cont;
				cont = 0;
				symbol = ' ';
				i--;
			} else if (cont > 0) {
				delete.removeAllElements();
				cont = 0;
				symbol = ' ';
				i--;
			} else if (cont > 0) {
				delete.removeAllElements();
				cont = 0;
			}
		}

		if (delete.size() > 2) {
			total += cont;
			d.addAll(delete);
			Remove(d);
			return points(total);
		}else{
			Remove(d);
			return points(total);
		}
	}

	/**
	 * "Limpa" uma coluna apagando todas as combinações na mesma
	 * 
	 * @param int col: indice da coluna a analisar
	 * 
	 * @return número de peças da combinação apagada
	 */
	int deleteSequencesCol(int col) {
		char symbol = ' ';
		Vector<Cell> delete = new Vector<Cell>();
		Vector<Cell> d = new Vector<Cell>();
		int cont = 0, total = 0;
		for (int i = 0; i < tab.length; i++) {
			if (tab[i][col] == null){
				if (cont > 2) {
					d.addAll(delete);
					delete.removeAllElements();
					total += cont;
					cont = 0;
					symbol = ' ';
				} else if (cont >= 0) {
					delete.removeAllElements();
					cont = 0;
					symbol = ' ';
				}
				break;
			}
			if (symbol == ' ')
				symbol = tab[i][col].symbol;

			if (tab[i][col].symbol == symbol) {
				cont++;
				delete.add(tab[i][col].pos);
			}

			else if (cont > 2) {
				d.addAll(delete);
				delete.removeAllElements();
				total += cont;
				cont = 0;
				symbol = ' ';
				i--;
			} else if (cont > 0) {
				delete.removeAllElements();
				cont = 0;
				symbol = ' ';
				i--;
			}
		}

		if (delete.size() > 2) {
			d.addAll(delete);
			Remove(d);
			return points(total);
		}
		else {
			Remove(d);
			return points(total);
		}
	}

	/**
	 * Verifica se uma certa peça está envolvida em alguma combinação numa linha
	 * 
	 * @param int col, int line: coordenadas da peça a anlisar
	 * @param Vector
	 *            <Cell> d: contentor para retornar as peças a destruir
	 * 
	 * @return 0 se a combinação não é possivel, outro número correspondendo ao
	 *         montante de peças combinadas se existir combinação
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
	 * @param int combo: número de peças destruidas pela jogada
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
	 * Verifica se uma certa peça está envolvida em alguma combinação numa
	 * coluna
	 * 
	 * @param int col, int line: coordenadas da peça a anlisar
	 * @param Vector
	 *            <Cell> d: contentor para retornar as peças a destruir
	 * 
	 * @return 0 se a combinação não é possivel, outro número correspondendo ao
	 *         montante de peças combinadas se existir combinação
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
	 * Destroi as peças passadas como parametro
	 * 
	 * @param Vector
	 *            <Cell> d: peças a destruir
	 * 
	 */
	private void Remove(Vector<Cell> d) {

		for (int i = 0; i < d.size();) {
			tab[d.elementAt(i).line][d.elementAt(i).colune] = null;
			d.remove(i);
		}

	}

	/**
	 * Compara se duas peças têm o mesmo simbolo
	 * 
	 * @param peças
	 *            a comparar
	 * 
	 */
	private boolean checkSameSymbol(Gem g1, Gem g2) {
		return g1.symbol == g2.symbol;
	}

	/**
	 * Varre o tabuleiro limpando todas as combinações
	 * 
	 * @return número de peças destruidas
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
	 * Troca todos os elementos de duas peças
	 * 
	 * @param peças
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
	 * Troca a posição de duas peças
	 * 
	 * @param peças
	 *            a trocar de posição
	 * 
	 */
	public void swapD(Gem g1, Gem g2) {
		Cell c = g1.getPos();

		g1.pos = g2.getPos();
		g2.pos = c;

		SetPos(g1.getPos().getCol(), g1.getPos().getLine(), g1);
		SetPos(g2.getPos().getCol(), g2.getPos().getLine(), g2);
	}

	Gem nullLine(int l, int col) {
		Gem ret = null;
		for (; col >= 0; col--) {
			if (tab[l][col] != null)
				ret = tab[l][col];
		}
		return ret;
	}

	void pushNullUp() {
		for (int i = (tab.length - 1); i > 0; i--) {
			for (int j = 0; j < tab.length; i++) {
				if (tab[i][j] == null) {
					if (nullLine(i, (j - 1)) != null) {
						tab[i][j] = nullLine(i, (j - 1));
						tab[i][j].pos = new Cell(i, j);
					}
				}
			}
		}
	}

	void fillTab2() {
		for (int i = (tab.length - 1); i >= 1; i--) {
			for (int j = 0; j < tab.length; i++) {
				if (tab[i][j] == null) {
					tab[i][j] = new Gem(i, j);
				}
			}
		}
	}

	/**
	 * Coloca uma peça numa posição do tabuleiro
	 * 
	 * @param int col, int lin: coordenadas do tabuleiro
	 * 
	 * @param Gem
	 *            g: peça a colocar nas coordenadas passadas
	 * 
	 */
	public void SetPos(int lin, int col, Gem g) {
		tab[lin][col] = g;
	}
}