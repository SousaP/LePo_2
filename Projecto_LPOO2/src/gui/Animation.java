package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import logic.Board;
import logic.Cell;
import logic.Gem;

public class Animation {
	AnimationType Type;
	GamePanel GPanel;
	Board GBoard;
	Gem g1;
	Gem g2;
	Graphics2D graphics2d;
	Image Boardpng;
	Image Focuspng;
	Timer myTimer;
	int distancia = 0;
	// Largura Gem
	int nx;
	int ny;

	public Animation(GamePanel GP) {

		GPanel = GP;
		Type = AnimationType.None;
		ImageIcon temp2 = new ImageIcon(this.getClass().getResource(
				"resources/board.png"));
		Boardpng = temp2.getImage();
		temp2 = new ImageIcon(this.getClass()
				.getResource("resources/focus.png"));
		Focuspng = temp2.getImage();
	}

	void update(Gem g1, Gem g2, AnimationType Type) {
		this.g1 = g1;
		this.g2 = g2;
		this.Type = Type;

		if (Type != AnimationType.None)
			BoardAction();
		else
			GPanel.repaint();

	}

	public void DrawBoard(Graphics2D g2d) {

		GBoard = GPanel.GBoard;
		GPanel.limx0 = GPanel.getWidth() * 240 / 800;
		GPanel.limy0 = GPanel.getHeight() * 40 / 600;

		nx = (GPanel.getWidth() * 66 / 800);
		ny = (GPanel.getHeight() * 66 / 600);

		Gem[][] tab = GBoard.getTab();
		int i;
		int a;

		g2d.drawImage(Boardpng, 0, 0, GPanel.getWidth(), GPanel.getHeight(),
				null);

		if (Type == AnimationType.Fill) {
			GemFalling(g2d);
			return;
		}

		for (i = 0; i < tab.length; i++)
			for (a = 0; a < tab.length; a++)
				if (tab[a][i] != g1 && tab[a][i] != g2 && tab[a][i] != null)
					g2d.drawImage(tab[a][i].getImage(), GPanel.limx0 + a * nx,
							GPanel.limy0 + i * ny, nx, ny, null);

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

	private void GemFalling(Graphics2D g2d) {
		int col;
		int lin;
		Gem[][] tab = GBoard.getTab();

		int[] vazios = new int[tab.length];
		for (int i = 0; i < vazios.length; i++)
			vazios[i] = 0;

		for (lin = tab.length - 1; lin >= 0; lin--)
			for (col = tab.length - 1; col >= 0; col--)
				if (distancia == 59) {
					if (vazios[col] > 0) {

						if (col == 0)
							GBoard.SetPos(col, lin, new Gem(col, lin));

						else {
							g2d.drawImage(g2.getImage(), GPanel.limx0 + col
									* nx, GPanel.limy0 + lin * ny + distancia,
									nx, ny, null);

							GBoard.SetPos(col + 1, lin, tab[col][lin]);
							GBoard.SetPos(col, lin, null);
						}
					}
				} else if (vazios[col] == 0)
					g2d.drawImage(tab[lin][col].getImage(), GPanel.limx0 + col
							* nx, GPanel.limy0 + lin * ny, nx, ny, null);
				else if (vazios[col] > 0)
					g2d.drawImage(g2.getImage(), GPanel.limx0 + col * nx,
							GPanel.limy0 + lin * ny + distancia, nx, ny, null);

		if (distancia == 59 && GBoard.FreeSpace())
			distancia = 0;
	}

	private void GemSwap(Graphics2D g2d) {
		if (g1 == null || g2 == null)
			return;

		Cell p1 = g1.getPos();
		Cell p2 = g2.getPos();

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

	private void BoardAction() {
		ActionListener myTimerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Type = AnimationType.None;

				GPanel.repaint();
				distancia++;
				if (distancia == 60) {

					distancia = 0;

					myTimer.stop();
					// GBoard.MakePlay(g1,g2);
					GBoard.swap(g1, g2);

					// if jogada mal feita:
					if (Type != AnimationType.SwapBack)
						update(g2, g1, AnimationType.SwapBack);
					// g1 = null;
					// g2 = null;
					// Type = AnimationType.None;
					// GPanel.repaint();
				}

			}
		};

		myTimer = new Timer(1, myTimerListener);
		myTimer.start();

	}

	private void drawFill() {
		GPanel.limx0 = GPanel.getWidth() * 240 / 800;
		GPanel.limy0 = GPanel.getHeight() * 40 / 600;

		nx = (GPanel.getWidth() * 66 / 800);
		ny = (GPanel.getHeight() * 66 / 600);

		Image temp = null;
		Gem[][] tab = GBoard.getTab();
		int i;
		int a;

		int time = 0;

		while (time < 60) {

		}

	}

	private void drawExplosion() {

	}

	private void drawSymbol(Graphics2D g2d, int col, int lin) {
		int x0 = GPanel.getWidth() * 240 / 800;
		int y0 = GPanel.getHeight() * 40 / 600;

		int nx = (GPanel.getWidth() * 66 / 800);
		int ny = (GPanel.getHeight() * 66 / 600);

		Image temp = GBoard.getTab()[col][lin].getImage();

		g2d.drawImage(temp, x0 + lin * nx, y0 + col * ny, nx, ny, null);

	}

	public enum AnimationType {
		Swap, SwapBack, Fill, Explosion, None
	}

}