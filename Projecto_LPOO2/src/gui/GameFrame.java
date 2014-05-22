package gui;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.applet.AudioClip;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private GamePanel GPanel;
	
	GameFrame() {
		//GButtons = new JPanel();
		GPanel = new GamePanel(this);
		setTitle("iBEJEWELD");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	//	ButtonsActions();
	//	addButtons();
		setSize(800, 480);
		setLocationRelativeTo(null);
	}
}