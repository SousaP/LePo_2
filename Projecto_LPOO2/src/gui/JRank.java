package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Rank;

/**
 * Funçao que trata o Rank
 *
 */
public class JRank extends JDialog {
	private static final long serialVersionUID = 1L;
	private GamePanel GPanel;
	private GameFrame GFrame;
	private JTextField txtSave;
	JButton Save;
	Rank Top;

	private static final String SaveFolder = System.getProperty("user.dir")
			+ "/Top/";

	/**
	 * @param GF : Frame do jogo
	 * @param GP : Panel do jogo
	 * Contrutor
	 */
	JRank(GameFrame GF, GamePanel GP) {
		setSize(300, 200);
		setTitle("Save");
		GFrame = GF;
		GPanel = GP;

		getContentPane().setLayout(new GridLayout(2, 1, 0, 0));
		setLocationRelativeTo(null);
		 ButtonsActions();
	}

	/**
	 * Acoes do Botao Save
	 */
	private void ButtonsActions() {

		Save = new JButton("Save");
		Save.setBackground(Color.BLACK);
		Save.setForeground(Color.GRAY);
		Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
	}

	/**
	 * Adiciona Botao Save
	 */
	public void addSave() {

		JPanel JSave = new JPanel();

		JLabel titleSave = new JLabel("Name:");
		JSave.add(titleSave);

		txtSave = new JTextField(8);
		JSave.add(txtSave);

		JSave.add(Save);
		add(JSave);

	}

	/**
	 * Faz save do Top10
	 */
	public void Save() {
		try {
			if (txtSave.getText().length() == 0) {
				JOptionPane.showMessageDialog(null,
						"Write a name to Save the game");
				return;
			}

			File savesFolder = new File(SaveFolder);
			if (!savesFolder.exists())
				savesFolder.mkdir();

			ObjectOutputStream file = null;
			try {
				file = new ObjectOutputStream(new FileOutputStream(SaveFolder
						+ "Top10"));

				file.writeObject(GPanel.GTop);
				file.close();
				JOptionPane.showMessageDialog(null, "Game successfully saved.");
				setVisible(false);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"An error occured while saving the game.");
			}
		} finally {
		}
	}

	/**
	 * Load do Top10
	 */
	public void Load() {
			try {
				FileInputStream fin = new FileInputStream(SaveFolder + "Top10");
				ObjectInputStream ois = new ObjectInputStream(fin);
				Top = (Rank) ois.readObject();
				ois.close();

				setVisible(false);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao fazer Load");
			}
		
	}

}