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
	private AudioClip song;
	int distancia = 0;
	// Largura Gem
	int nx;
	int ny;

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

	private void BoardAction() {
		ActionListener myTimerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Type = AnimationType.None;

				GPanel.repaint();
				distancia++;
				if (distancia == 60) {

					if (Type == AnimationType.SwapBack && distancia == 60) {

						Type = AnimationType.None;
						GBoard.swap(g2, g1);
						g1 = null;
						g2 = null;
					}

					distancia = 0;

					myTimer.stop();

					if (Type == AnimationType.Swap)
						if ((GPanel.Score += GBoard.MakePlay(g1, g2)) == 0) {
							// GBoard.swap(g2,g1);
							update(g2, g1, AnimationType.SwapBack);
						} else {
							playSound("resources/match.wav");
							g1 = null;
							g2 = null;
						}

					GPanel.repaint();
				}

			}
		};

		myTimer = new Timer(1, myTimerListener);
		myTimer.start();

	}

	private void drawExplosion() {

	}

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

	public enum AnimationType {
		Swap, SwapBack, Fill, Explosion, None
	}

}