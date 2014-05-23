package logic;

import java.util.Random;

public class GameTimed extends GameMode{
	int maxTime;
	
	public GameTimed(String name, int maxTime){
		super(name,new Board(false));
		this.maxTime = maxTime;
	}
}