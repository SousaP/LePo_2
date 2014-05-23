package logic;

import java.util.Random;

public class GamePoints extends GameMode{
	int Points; //aumentando com o nivel
	
	public GamePoints(String name){
		super(name,new Board(false));
		this.Points = 0;
	}
}