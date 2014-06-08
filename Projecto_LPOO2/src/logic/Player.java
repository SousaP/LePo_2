package logic;


/**
 * Class utilizada no Top10
 *
 */
public class Player implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	String name;
	int score;
	String gameMode;
	
	public Player(String nome, String gameMode) {
		this.gameMode = gameMode;
		this.name = nome;
		score = 0;
	}
	public Player(String nome, String gameMode, int score) {
		this.gameMode = gameMode;
		this.name = nome;
		this.score = score;
	}
	public void updateScore(int s) {
		this.score = this.score + s;
	}
	
	public String getName(){
		return name;
	}
	
	public int getScore(){
		return score;
	}
	
	public String getMode(){
		return gameMode;
	}
}
