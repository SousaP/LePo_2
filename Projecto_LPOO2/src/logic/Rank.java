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

	public List<Player> getRank() {
		return Rank;
	}

	void updateRank(Player P) {
		if (P.getScore() > Rank.get(9).getScore()) {
			Rank.add(9, P);
		}

		Collections.sort(Rank, COMPARATOR);
	}

}