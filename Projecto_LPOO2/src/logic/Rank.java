package logic;

import java.util.ArrayList;
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

	List<Player> products = new ArrayList<Player>(10);

	public Rank() {

	}

	public String[] getRank() {
		return null;

	}
}