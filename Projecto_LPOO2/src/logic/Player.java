package logic;

import java.util.Random;


public class Player {
	
	String name;
	int score;
	String gameMode;
	
	public Player(String nome, String gameMode) {
		this.gameMode = gameMode;
		this.name = nome;
		score = 0;
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
}
