package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import logic.Player;
import logic.Rank;

/**
 * Funçao que trata o Rank
 * 
 */
public class JRank extends JDialog {
	private static final long serialVersionUID = 1L;
	private GamePanel GPanel;
	private JTextField txtSave;
	JButton Save;
	Rank Top;
	Player AddP;

	private static final String SaveFolder = System.getProperty("user.dir")
			+ "/Top/";

	/**
	 * @param GF
	 *            : Frame do jogo
	 * @param GP
	 *            : Panel do jogo Contrutor
	 */
	public JRank(GamePanel GP) {
		setTitle("Top10");
		GPanel = GP;
		Top = GPanel.GTop;
		Load();
		getContentPane().setLayout(new GridLayout(2, 1, 0, 0));

		ButtonsActions();
		setVisible(false);

		try {
			PrintRank();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addSave();
		setSize(300, 200);
		setLocationRelativeTo(null);
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
				
				if (txtSave.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,
							"Write a name to Save the game");
					return;
				}
				
				if(!GPanel.Inicio){
				AddP = new Player(txtSave.getText(), "Time", GPanel.Score);
				GPanel.GTop.updateRank(AddP);
				
				}
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
			

			File savesFolder = new File(SaveFolder);
			if (!savesFolder.exists())
				savesFolder.mkdir();

			ObjectOutputStream file = null;
			try {
				file = new ObjectOutputStream(new FileOutputStream(SaveFolder
						+ "Top10"));

				file.writeObject(GPanel.GTop);
				file.close();
				JOptionPane.showMessageDialog(null, "Top10 successfully saved.");
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
			GPanel.GTop = Top;
			ois.close();

			setVisible(false);
		} catch (Exception ex) {
			GPanel.GTop = new Rank();
			//ex.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Erro ao fazer Load");
		}

	}

	public void PrintRank() throws IOException {

		JList<String> listRecords = new JList<String>();
		DefaultListModel<String> model = new DefaultListModel<String>();
		JLabel labelRecords = new JLabel("Top Ten");

		labelRecords.setFont(new Font("Dialog", Font.PLAIN, 30));
		labelRecords.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		// Shows the current high scores
		try {
			String line = null;
			for (int i = 0; i < Top.getRank().size(); i++) {
				if (Top.getRank().get(i).getName() == null)
					break;
				else {
					line = Top.getRank().get(i).getName() + "  "
							+ Top.getRank().get(i).getMode() + "  "
							+ Top.getRank().get(i).getScore() + "\n";
					model.addElement(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		listRecords.setModel(model);
		JScrollPane scrollRecords = new JScrollPane(listRecords);
		scrollRecords
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	//	add(labelRecords, BorderLayout.NORTH);
		add(scrollRecords, BorderLayout.SOUTH);
	}
}