package gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import logic.Board;
import logic.Cell;
import logic.Gem;

/**
 * Class que trata das animaçoes, sons e draw do jogo
 * 
 */
public class Animation {
	private static final long serialVersionUID = 1L;
	AnimationType Type;
	GamePanel GPanel;
	Board GBoard;
	Gem g1;
	Gem g2;
	Graphics2D graphics2d;
	Image Boardpng;
	Image Focuspng;
	Timer myTimer;
	private AudioClip song;
	int distancia = 0;
	int newpoints = 0;
	// Largura Gem
	int nx;
	int ny;

	/**
	 * @param GP
	 *            - Recebe o GamePanel onde desenha
	 */
	public Animation(GamePanel GP) {

		GPanel = GP;
		Type = AnimationType.None;
		ImageIcon temp2 = new ImageIcon(this.getClass().getResource(
				"resources/board.jpg"));
		Boardpng = temp2.getImage();
		temp2 = new ImageIcon(this.getClass()
				.getResource("resources/focus.png"));
		Focuspng = temp2.getImage();
	}

	/**
	 * Altera a forma de desenhar com o Typo de animaçao
	 * 
	 * @param g1
	 *            null se nao for swap/irrelevante
	 * @param g2
	 *            null se nao for swap/irrelevante
	 * @param Type
	 */
	void update(Gem g1, Gem g2, AnimationType Type) {
		this.g1 = g1;
		this.g2 = g2;
		this.Type = Type;

		if (Type != AnimationType.None)
			BoardAction();
		else
			GPanel.repaint();

	}

	/**
	 * @param g2d
	 *            - recebido pelo Panel desenha o board
	 */
	public void DrawBoard(Graphics2D g2d) {
		GBoard = GPanel.GBoard;

		Gem[][] tab = GBoard.getTab();

		GPanel.limx0 = GPanel.getWidth() * 240 / 800;
		GPanel.limy0 = GPanel.getHeight() * 40 / 600;

		nx = (GPanel.getWidth() * 66 / 800);
		ny = (GPanel.getHeight() * 66 / 600);

		int i;
		int a;

		g2d.drawImage(Boardpng, 0, 0, GPanel.getWidth(), GPanel.getHeight(),
				null);

		if (Type == AnimationType.Fill) {
			GemFalling(g2d);
			return;
		}

		if (Type == AnimationType.None)
			for (i = 0; i < tab.length; i++)
				for (a = 0; a < tab.length; a++)
					if (tab[i][a] != null)
						g2d.drawImage(tab[i][a].getImage(), GPanel.limx0 + a
								* nx, GPanel.limy0 + i * ny, nx, ny, null);

		if (Type != AnimationType.None)
			for (i = 0; i < tab.length; i++)
				for (a = 0; a < tab.length; a++)
					if (tab[i][a] != g1 && tab[i][a] != g2 && tab[i][a] != null)
						g2d.drawImage(tab[i][a].getImage(), GPanel.limx0 + a
								* nx, GPanel.limy0 + i * ny, nx, ny, null);

		if (GPanel.Focus != null)
			g2d.drawImage(Focuspng, GPanel.limx0
					+ GPanel.Focus.getPos().getCol() * nx, GPanel.limy0
					+ GPanel.Focus.getPos().getLine() * ny, nx, ny, null);

		if (Type == AnimationType.None)
			return;

		else if (Type == AnimationType.Swap || Type == AnimationType.SwapBack) {
			GemSwap(g2d);
			return;
		}

		// this.Type = AnimationType.None;
	}

	/**
	 * 
	 * @param g2d
	 *            - Recebe do DrawBoard em caso de a animaçao ser de
	 *            preenchimento do board
	 */
	private void GemFalling(Graphics2D g2d) {
		if (!GBoard.FreeSpace())
			return;
		int col;
		int lin;
		Gem[][] tab = GBoard.getTab();
		int[] vazios = new int[tab.length];
		for (int i = 0; i < vazios.length; i++)
			vazios[i] = 0;

		for (lin = tab.length - 1; lin >= 0; lin--)
			for (col = tab.length - 1; col >= 0; col--) {
				if (lin == 0 && tab[lin][col] == null)
					GBoard.SetPos(lin, col, new Gem(lin, col));

				else if (distancia != 59 && vazios[col] > 0
						&& tab[lin][col] != null)
					g2d.drawImage(tab[lin][col].getImage(), GPanel.limx0 + col
							* nx, GPanel.limy0 + lin * ny + distancia, nx, ny,
							null);

				else if (tab[lin][col] != null && vazios[col] == 0)
					g2d.drawImage(tab[lin][col].getImage(), GPanel.limx0 + col
							* nx, GPanel.limy0 + lin * ny, nx, ny, null);

				else if (distancia == 59 && vazios[col] > 0
						&& tab[lin][col] == null) {

					GBoard.SetPos(lin, col, tab[lin - 1][col]);
					GBoard.SetPos(lin - 1, col, null);
				}

				else if (distancia == 59 && tab[lin][col] == null) {
					GBoard.SetPos(lin, col, tab[lin - 1][col]);
					GBoard.SetPos(lin - 1, col, null);

				}

				else if (tab[lin][col] == null)
					vazios[col]++;

				if (lin == 0 && col == 0)
					if (distancia == 59 && GBoard.FreeSpace())
						distancia = 0;

			}

	}

	/**
	 * 
	 * Animaçao de troca de peças
	 * 
	 * @param g2d
	 *            - recebido pelo DrawBoard
	 */
	private void GemSwap(Graphics2D g2d) {

		if (g1 == null || g2 == null)
			return;

		Cell p1 = g1.getPos();
		Cell p2 = g2.getPos();

		// System.out.printf("p1 col%s\n", p1.getCol());
		// System.out.printf("p2 col%s\n", p2.getCol());

		if (p1.getCol() == p2.getCol()) {// mesma coluna

			if (p1.getLine() > p2.getLine()) {
				g2d.drawImage(g2.getImage(), GPanel.limx0 + p2.getCol() * nx,
						GPanel.limy0 + p2.getLine() * ny + distancia, nx, ny,
						null);

				g2d.drawImage(g1.getImage(), GPanel.limx0 + p1.getCol() * nx,
						GPanel.limy0 + p1.getLine() * ny - distancia, nx, ny,
						null);
			} else {

				g2d.drawImage(g2.getImage(), GPanel.limx0 + p2.getCol() * nx,
						GPanel.limy0 + p2.getLine() * ny - distancia, nx, ny,
						null);

				g2d.drawImage(g1.getImage(), GPanel.limx0 + p1.getCol() * nx,
						GPanel.limy0 + p1.getLine() * ny + distancia, nx, ny,
						null);

			}

		} else {

			if (p1.getCol() > p2.getCol()) {
				g2d.drawImage(g2.getImage(), GPanel.limx0 + p2.getCol() * nx
						+ distancia, GPanel.limy0 + p2.getLine() * ny, nx, ny,
						null);

				g2d.drawImage(g1.getImage(), GPanel.limx0 + p1.getCol() * nx
						- distancia, GPanel.limy0 + p1.getLine() * ny, nx, ny,
						null);

			} else {
				g2d.drawImage(g2.getImage(), GPanel.limx0 + p2.getCol() * nx
						- distancia, GPanel.limy0 + p2.getLine() * ny, nx, ny,
						null);

				g2d.drawImage(g1.getImage(), GPanel.limx0 + p1.getCol() * nx
						+ distancia, GPanel.limy0 + p1.getLine() * ny, nx, ny,
						null);

			}

		}

	}

	/**
	 * 
	 * Funçao que cria as threads para o desenho de animaçoes
	 * 
	 */
	private void BoardAction() {
		ActionListener myTimerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Type = AnimationType.None;

				newpoints = 0;
				GPanel.repaint();

				distancia++;

				if (distancia == 60) {
					myTimer.stop();
					GPanel.Focus = null;
					if (Type == AnimationType.Fill && distancia == 60) {

						playSound("resources/fall.wav");
						newpoints = GBoard.sweepTab();
						GPanel.Score += newpoints;
						if (newpoints == 0) {
							Type = AnimationType.None;
							if (GBoard.Tip() == null) {
								GPanel.GTimer.stop();
								GPanel.GFrame.Rank10.setVisible(true);
							}
						} else {
							distancia = 0;
							update(null, null, AnimationType.Fill);
						}
						g1 = null;
						g2 = null;

					}

					else if (Type == AnimationType.SwapBack && distancia == 60) {

						Type = AnimationType.None;
						GBoard.swap(g2, g1);
						g1 = null;
						g2 = null;
					}

					distancia = 0;
					if (Type == AnimationType.Swap) {

						if ((newpoints = GBoard.MakePlay(g1, g2)) == 0) {

							update(g2, g1, AnimationType.SwapBack);
						} else {
							GPanel.Score += newpoints;

							update(g2, g1, AnimationType.Fill);
							playSound("resources/match.wav");
							g1 = null;
							g2 = null;

						}
					}
					//
					GPanel.repaint();
				}

			}
		};

		myTimer = new Timer(0, myTimerListener);
		myTimer.start();

	}

	/**
	 * Reproduz um som ou musica
	 * 
	 * @param file
	 *            - nome e pasta do ficheiro
	 */
	public void playSound(final String file) {
		// "resources/musica.wav"
		if (!GPanel.SoundOn)
			return;

		try {
			song = Applet.newAudioClip(this.getClass().getResource(file));
		} catch (Exception e) {
		}
		song.play();
	}

	/**
	 * Tipos de Animaçao
	 */
	public enum AnimationType {
		Swap, SwapBack, Fill, None
	}

}