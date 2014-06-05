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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean Inicio;
	private Image Intro;
	Animation GAnimation;
	Board GBoard;
	Rank GTop;

	public GamePanel(GameFrame gameFrame) {
		Inicio = true;
		GBoard = new Board();
		GAnimation = new Animation(GBoard,this);
		GTop = new Rank();
		loadImages();
		repaint();
		addMouse();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;

		if (Inicio)
			g2.drawImage(Intro, 0, 0, getWidth(), getHeight(), null);

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
				// System.out.println("mouseClicked");
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