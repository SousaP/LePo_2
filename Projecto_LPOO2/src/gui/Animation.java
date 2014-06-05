package gui;
import java.awt.Graphics2D;
import java.awt.Image;

import logic.Board;
import logic.Gem;
public class Animation{
	AnimationType Type;
	GamePanel GPanel;
	Board GBoard;
	Gem g1;
	Gem g2;
	
	public Animation(Board GB, GamePanel GP){
		GBoard = GB;
	}
	
	void update(Gem g1, Gem g2, AnimationType Type){
		this.g1 = g1;
		this.g2 = g2;
		this.Type = Type;
		
	}
	
	public void drawBoard(Graphics2D g2d){
		Gem[][] tab = GBoard.getTab();

		for (int i = 0; i < tab.length; i++)
			for (int a = 0; a < tab.length; a++) {
				drawSymbol(g2d, a, i);
			}
	}
	
	
	private void drawSymbol(Graphics2D g2d, int col, int lin) {

		int nx = (GPanel.getWidth() / 8) ;
		int ny = (GPanel.getHeight() / 8);

		Image temp = GBoard.getTab()[col][lin].getImage();


		g2d.drawImage(temp, lin * nx, col * ny, nx, ny, null);

	}
	
	public enum AnimationType {
		Swap, Fill, Explosion, None
	}



}