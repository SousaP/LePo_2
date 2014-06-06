package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	boolean Inicio;
	private Image Intro;
	Animation GAnimation;
	Board GBoard;
	Rank GTop;
	Gem Focus;

	public GamePanel(GameFrame gameFrame) {
		Inicio = true;
		GAnimation = new Animation(this);
		GTop = new Rank();
		Focus = null;
		loadImages();
		repaint();
		addMouse();
	}
	
	public void updateBegin(boolean I){
		Inicio = I;
		if(!Inicio)
			GBoard = new Board();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;

		if (Inicio)
			g2d.drawImage(Intro, 0, 0, getWidth(), getHeight(), null);
		else
			try {
				
				GAnimation.drawBoard(g2d);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//System.out.printf("aqui");
	}

	void update() {
		addMouse();
	}

	void loadImages() {
		ImageIcon temp;

		temp = new ImageIcon(this.getClass().getResource("resources/Intro.jpg"));
		Intro = temp.getImage();
	}

	private void addMouse() {
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int limx0 = getWidth() * 240 / 800;
				int limxf = getWidth() * 760 / 800;
				int limy0 = getHeight() * 40 / 600;
				int limyf = getHeight() * 560 / 600;
				
				
				if(e.getX() > limx0 && e.getX() < limxf &&
						e.getY() > limy0 && e.getY() < limyf)
					{
					Focus = GBoard.getTab()[(e.getX() - limx0)  / (getWidth() * 66 / 800)][(e.getY() - limy0)  / (getHeight() *66 / 600)];
					
					System.out.println(Focus.getPos().getCol());
					System.out.println(Focus.getPos().getLine());
					}
				
				
			repaint();
			}

			public void mouseEntered(MouseEvent e) {
				// System.out.println("mouseEntered");
			}

			public void mouseExited(MouseEvent e) {
				// System.out.println("mouseExited");
			}

			public void mousePressed(MouseEvent e) {
				// System.out.println("mousePressed");
			}

			public void mouseReleased(MouseEvent e) {
				// System.out.println("mouseReleased");
			}
			public void mouseMoved(MouseEvent e) {
				System.out.println("mouseMoved");
		        System.out.println("Mouse moved (" + e.getX() + ',' + e.getY() + ')');

		    }
		});

	}

}