package logic;

import java.util.Random;

public class GameTimed extends GameMode{
	int maxTime;
	
	public GameTimed(String name, int maxTime){
		this.name = name;
		this.maxTime = maxTime;
	}
}