package logic;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Rank {

	List<Player> products = new ArrayList<Player>(10);

	private static Comparator<Player> COMPARATOR = new Comparator<Player>() {
		// This is where the sorting happens.
		public int compare(Player p1, Player p2) {
			return p1.getScore() - p2.getScore();
		}
	};

	List<Player> Rank = new ArrayList<Player>(10);

	public Rank() {
		Player temp = new Player(null, null);
		for (int i = 0; i < 10; i++) {
			Rank.add(temp);
		}
	}

	public String[] getRank() {
		return null;

	}

	void updateRank(Player P) {
		if (P.getScore() > Rank.get(9).getScore()) {
			Rank.add(9, P);
		}

		Collections.sort(Rank, COMPARATOR);
	}

	public void load(String fileName) {

		// The name of the file to open.

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String nome, mode, ints;
			int p;
			int i = 0;

			while ((line = bufferedReader.readLine()) != null) {
				nome = line;
				line = bufferedReader.readLine();
				mode = line;
				line = bufferedReader.readLine();
				p = Integer.parseInt(line);

				Rank.add(i, new Player(nome, mode, p));
				i++;
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public void save(String fileName) {

		// The name of the file to open.

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			for (int i = 0; i < 10; i++) {
				bufferedWriter.write(Rank.get(i).getName());
				bufferedWriter.newLine();
				bufferedWriter.write(Rank.get(i).getMode());
				bufferedWriter.newLine();
				bufferedWriter.write(Rank.get(i).getScore());
				if (i != 9)
					bufferedWriter.newLine();
			}
			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}
}