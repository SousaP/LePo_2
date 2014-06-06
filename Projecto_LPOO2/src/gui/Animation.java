package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import logic.Board;
import logic.Gem;

public class Animation {
	AnimationType Type;
	GamePanel GPanel;
	Board GBoard;
	Gem g1;
	Gem g2;
	Graphics2D g2d;
	Image Boardpng;
	Image Focuspng;
	
	//Largura Gem
	int nx;
	int ny;
	

	public Animation(GamePanel GP) {
		GPanel = GP;
		Type= AnimationType.None;
		 ImageIcon temp2 = new ImageIcon(this.getClass().getResource(
					"resources/board.png"));
		 Boardpng = temp2.getImage();
		 temp2 = new ImageIcon(this.getClass().getResource(
					"resources/focus.png"));
		 Focuspng = temp2.getImage();
	}

	void update(Gem g1, Gem g2, AnimationType Type) {
		this.g1 = g1;
		this.g2 = g2;
		this.Type = Type;

	}

	public void drawBoard(Graphics2D g2d) throws InterruptedException {
		GBoard = GPanel.GBoard;
		
		if (Type == AnimationType.Swap) {
			drawSwap(g2d);
			return;
		}else if (Type == AnimationType.SwapBack) {
			drawSwapBack(g2d);
			return;
		}else if (Type == AnimationType.Fill) {
			drawFill();
			return;
		} else if (Type == AnimationType.Explosion) {
			drawExplosion();
			return;
		}
		else if(Type == AnimationType.None) {
			DrawBoard(g2d);
			return;
		}
	}
	
	private void DrawBoard(Graphics2D g2d){
		GPanel.limx0 = GPanel.getWidth() * 240 / 800;
		GPanel.limy0 = GPanel.getHeight() * 40 / 600;

		nx = (GPanel.getWidth() * 66 / 800);
		ny = (GPanel.getHeight() * 66 / 600);

		Image temp = null;
		Gem[][] tab = GBoard.getTab();
		int i;
		int a;

	
			
		
			g2d.drawImage(Boardpng, 0, 0, GPanel.getWidth(), GPanel.getHeight(),
					null);

			for (i = 0; i < tab.length; i++)
				for (a = 0; a < tab.length; a++) {
					temp = tab[a][i].getImage();
						g2d.drawImage(temp, GPanel.limx0 + a * nx, GPanel.limy0 + i * ny, nx, ny,
								null);
				}
			
			if(GPanel.Focus != null)
			g2d.drawImage(Focuspng, GPanel.limx0 + GPanel.Focus.getPos().getCol() * nx, GPanel.limy0 + GPanel.Focus.getPos().getLine() * ny, nx, ny,
						null);
	}
	
	private void drawSwap(Graphics2D g2d) throws InterruptedException {

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
			 
			g2d.drawImage(Boardpng, 0, 0, GPanel.getWidth(), GPanel.getHeight(),
					null);

			for (i = 0; i < tab.length; i++)
				for (a = 0; a < tab.length; a++) {
					temp = tab[a][i].getImage();
					if (tab[a][i] != g1 || tab[a][i] != g2)
						g2d.drawImage(temp, GPanel.limx0 + a * nx, GPanel.limy0 + i * ny, nx, ny,
								null);
				}
			
			temp = g2.getImage();

			g2d.drawImage(temp, GPanel.limx0 + g2.getPos().getCol() * nx + 66/ (60 + time), GPanel.limy0 + g2.getPos().getLine() * ny + 66/ (60 + time), nx, ny,
					null);
			
			temp = g1.getImage();

			g2d.drawImage(temp, GPanel.limx0 + g1.getPos().getCol() * nx + 66/ (60 + time), GPanel.limy0 + g1.getPos().getLine() * ny + 66/ (60 + time), nx, ny,
					null);
			
			Thread.sleep(16);
			time++;
		}

	}

	private void drawSwapBack(Graphics2D g2d) throws InterruptedException {

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
			g2d.drawImage(Boardpng, 0, 0, GPanel.getWidth(), GPanel.getHeight(),
					null);

			for (i = 0; i < tab.length; i++)
				for (a = 0; a < tab.length; a++) {
					temp = tab[a][i].getImage();
					if (tab[a][i] != g1 || tab[a][i] != g2)
						g2d.drawImage(temp, GPanel.limx0 + a * nx, GPanel.limy0 + i * ny, nx, ny,
								null);
				}
			
			temp = g2.getImage();

			g2d.drawImage(temp, GPanel.limx0 + g2.getPos().getCol() * nx - 66/ (60 + time), GPanel.limy0 + g2.getPos().getLine() * ny + 66/ (60 + time), nx, ny,
					null);
			
			temp = g1.getImage();

			g2d.drawImage(temp, GPanel.limx0 + g1.getPos().getCol() * nx - 66/ (60 + time), GPanel.limy0 + g1.getPos().getLine() * ny + 66/ (60 + time), nx, ny,
					null);
			
			Thread.sleep(16);
			time++;
		}

	}

	private void drawFill() throws InterruptedException {
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