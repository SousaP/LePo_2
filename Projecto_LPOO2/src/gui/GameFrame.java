package gui;

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

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private GamePanel GPanel;
	private JPanel GButtons;
	private JButton GNewGame;
	private JButton GTop10;
	private JButton Gexit;
	
	GameFrame() {
		GButtons = new JPanel();
		GPanel = new GamePanel(this);
		
		ButtonsActions();
		addButtons();
		setTitle("BEJEWELD");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	//	ButtonsActions();
	//	addButtons();
		setSize(800, 450);
		setLocationRelativeTo(null);
		GPanel.requestFocus();
		
	}
	
	private void ButtonsActions() {
		GNewGame = new JButton("New Game");
		GNewGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				Icon icon = UIManager.getIcon("OptionPane.questionIcon");
				String[] buttons = { "New Game", "Create Game",
						"Cancel" };
				int choice = JOptionPane
						.showOptionDialog(rootPane, "Do you want New Game?",
								"Start Game", JOptionPane.PLAIN_MESSAGE, 0,
								icon, buttons, buttons[0]);
				
				GPanel.requestFocus();
			}

		});

		GTop10 = new JButton("Top10");
		GTop10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


			}
		});


		Gexit = new JButton("Quit");
		Gexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Icon icon = UIManager.getIcon("OptionPane.questionIcon");
				String[] buttons = { "Continue", "Menu",
						"Exit", "Cancel" };
				int choice = JOptionPane
						.showOptionDialog(null, "Are you sure you want to quit?",
								"Start Game", JOptionPane.PLAIN_MESSAGE, 0,
								icon, buttons, buttons[0]);
				if (choice == 0) 
					return;


				GPanel.requestFocus();
			}
		});



	}
	
	public void addButtons() {

		GButtons.setLayout(new GridLayout(1, 3));
		GButtons.add(GNewGame);
		GNewGame.setBackground(Color.BLACK);
		GNewGame.setForeground(Color.GRAY);

		GButtons.add(GTop10);
		GTop10.setBackground(Color.BLACK);
		GTop10.setForeground(Color.GRAY);


		GButtons.add(Gexit);
		Gexit.setBackground(Color.BLACK);
		Gexit.setForeground(Color.GRAY);



		getContentPane().add(GButtons, BorderLayout.SOUTH);

		GButtons.setFocusable(false);

		getContentPane().add(GPanel, BorderLayout.CENTER);
		pack();

	}
}