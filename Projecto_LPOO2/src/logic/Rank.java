package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Rank implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
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

	public void updateRank(Player P) {
		if (P.getScore() > Rank.get(9).getScore()) {
			Rank.add(9, P);
		}

		Collections.sort(Rank, COMPARATOR);
	}

}