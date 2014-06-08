package gui;

import gui.Animation.AnimationType;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.*;
import logic.Gem.Direction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	boolean Inicio;
	private Image Intro;
	GameFrame GFrame;
	Animation GAnimation;
	Board GBoard;
	Rank GTop;
	Gem Focus;
	JLabel JScore;
	int Score = 0;
	int Time = 0;
	Timer GTimer;
	boolean SoundOn;

	// Limites do board
	int limx0;
	int limxf;
	int limy0;
	int limyf;

	public GamePanel(GameFrame gameFrame) {
		SoundOn = true;
		GFrame = gameFrame;
		JScore = new JLabel("");
		setLayout(null);
		Inicio = true;
		GAnimation = new Animation(this);
		GTop = new Rank();
		Focus = null;
		loadImages();
		repaint();
		addMouse();
	}

	public void updateBegin(boolean I) {
		Inicio = I;
		if (!Inicio)
			GBoard = new Board();

	//	Score += GBoard.sweepTab();
		GAnimation.g1 = null;
		GAnimation.g2 = null;
		Focus = null;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		GAnimation.graphics2d = g2d;
		if (Inicio)
			g2d.drawImage(Intro, 0, 0, getWidth(), getHeight(), null);
		else {
			GAnimation.DrawBoard(g2d);
			DrawScore();
			GFrame.AddTip();
		}
		
		// System.out.printf("aqui");
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
				if (Inicio)
					return;

				limx0 = getWidth() * 240 / 800;
				limxf = getWidth() * 760 / 800;
				limy0 = getHeight() * 40 / 600;
				limyf = getHeight() * 560 / 600;
				int col = (e.getX() - limx0) / (getWidth() * 66 / 800);
				int lin = (e.getY() - limy0) / (getHeight() * 66 / 600);

				if (!(e.getX() > limx0 && e.getX() < limxf && e.getY() > limy0 && e
						.getY() < limyf))
					return;
				
				GAnimation.playSound("resources/select.wav");
				
				if (Focus == null) {
					Focus = GBoard.getTab()[lin][col];
					repaint();
					return;
				}

				if (Focus.getPos().getCol() == col
						&& Focus.getPos().getLine() == lin)
					return;

				if (Math.abs(Focus.getPos().getCol() - col) < 2
						&& Math.abs(Focus.getPos().getLine() - lin) == 0
						|| Math.abs(Focus.getPos().getCol() - col) == 0
						&& Math.abs(Focus.getPos().getLine() - lin) < 2)
					GAnimation.update(Focus, GBoard.getTab()[lin][col],
							AnimationType.Swap);
				else
					Focus = GBoard.getTab()[lin][col];

				Focus = GBoard.getTab()[lin][col];
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
		});

	}

	private void DrawScore() {
		//  <html>Score:<br>1000</html>
		JScore.setText("<html><br>Score: <br>" + Score + "<br>Time:<br>"+ Time + "</html>");
		JScore.setSize(200, 300);
		JScore.setForeground(Color.YELLOW);
		JScore.setFont(new Font("", Font.PLAIN, 50 * getHeight() / 600));
		JScore.setLocation(39 * getWidth() / 800, 50 * getHeight() / 600);
		
	}
	
	public void IncTime(){
		ActionListener myTimerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
					Time++;
					JScore.setText("<html>Score:<br>" + Score + "<br>Time:<br>"+ Time + "</html>");
				}

			
		};
		
		Time = 0;
		GTimer = new Timer(1000, myTimerListener);
		GTimer.start();
		
	}

}