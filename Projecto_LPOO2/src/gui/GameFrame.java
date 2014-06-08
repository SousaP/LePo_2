package gui;

import gui.Animation.AnimationType;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.applet.AudioClip;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Frame do Jogo
 *
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private GamePanel GPanel;
	private JPanel GButtons;
	private JButton GNewGame;
	private JButton GTop10;
	private JButton Gexit;
	private JButton GTip;
	private JButton OnOffSound;
	
	GameFrame() {
		
		GButtons = new JPanel();
		GPanel = new GamePanel(this);
		
		ButtonsActions();
		addButtons();
		setTitle("BEJEWELD");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setSize(800, 450);
		setLocationRelativeTo(null);
		
	}
	
	/**
	 * Adiciona acçoes aos botoes do jogo
	 */
	private void ButtonsActions() {
		GNewGame = new JButton("New Game");
		GNewGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				setSize(800, 600);
				setLocationRelativeTo(null);
				GPanel.Score = 0;
				GPanel.updateBegin(false);
				GPanel.requestFocus();
				
				if(GPanel.GTimer != null)
					GPanel.GTimer.stop();
				
				GPanel.IncTime();
				GPanel.add(GPanel.JScore);
				GPanel.add(GTip);
			}

		});

		GTop10 = new JButton("Top10");
		GTop10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


			}
		});
		
		OnOffSound = new JButton("Off/On Sound");
		OnOffSound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(GPanel.SoundOn)
					GPanel.SoundOn = false;
				else
					GPanel.SoundOn = true;

			}
		});
		


		Gexit = new JButton("Quit");
		Gexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Icon icon = UIManager.getIcon("OptionPane.questionIcon");
				String[] buttons = { "Menu",
						"Exit", "Cancel" };
				int choice = JOptionPane
						.showOptionDialog(null, "Are you sure you want to quit?",
								"Start Game", JOptionPane.PLAIN_MESSAGE, 0,
								icon, buttons, buttons[0]);
				if (choice == 2) 
					return;
				else if(choice == 0){
					GPanel.GTimer.stop();
					GPanel.remove(GPanel.JScore);
					GPanel.remove(GTip);
					GPanel.Inicio = true;
					setSize(800, 450);
					setLocationRelativeTo(null);
				}
				else if(choice == 1){
					System.exit(0);
				}


				GPanel.requestFocus();
			}
		});

		// GTip
		
		GTip = new JButton("Tip");
		GTip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				GPanel.Focus = GPanel.GBoard.Tip();
				GPanel.GAnimation.update(null, null, AnimationType.None);
				
			}
		});

	}
	
	/**
	 * Adiciona o botao Tip e calcula suas dimensoes
	 * quando em jogo
	 */
	public void AddTip(){
		GTip.setBackground(Color.YELLOW);
		GTip.setForeground(Color.BLACK);
		GTip.setBounds(60 * GPanel.getWidth() / 800, 425 * getHeight() / 600,  70* GPanel.getWidth() / 800, 50 * GPanel.getWidth() / 600);
	
	}
	
	/**
	 * Adiciona botoes base da interface
	 */
	public void addButtons() {

		GButtons.setLayout(new GridLayout(1, 4));
		GButtons.add(GNewGame);
		GNewGame.setBackground(Color.BLACK);
		GNewGame.setForeground(Color.GRAY);

		GButtons.add(GTop10);
		GTop10.setBackground(Color.BLACK);
		GTop10.setForeground(Color.GRAY);
		
		GButtons.add(OnOffSound);
		OnOffSound.setBackground(Color.BLACK);
		OnOffSound.setForeground(Color.GRAY);


		GButtons.add(Gexit);
		Gexit.setBackground(Color.BLACK);
		Gexit.setForeground(Color.GRAY);
		
		


		getContentPane().add(GButtons, BorderLayout.SOUTH);

		GButtons.setFocusable(false);

		getContentPane().add(GPanel, BorderLayout.CENTER);
		pack();

	}
}