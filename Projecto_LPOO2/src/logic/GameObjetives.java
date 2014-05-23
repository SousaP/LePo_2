package logic;

import java.util.Random;

public class GameObjetives extends GameMode{
	String Objetives[]; // o jogador depois podera escolher, ou que seja por nivel
	
	public GameObjetives(String name){
		
		super(name,new Board(false));
		this.Objetives =  new String[0];
	}
}