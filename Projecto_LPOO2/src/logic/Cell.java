package logic;

import java.io.Serializable;

/**
 * Classe que representa as células do tabuleiro
 */
public class Cell implements Serializable{
	
	private static final long serialVersionUID = 1L;
	int line, colune;

	public Cell() {
	    line = 0;
		colune = 0;
	}

	public Cell(int y, int x) {
		line = y;
		colune = x;
	}

	public void setLine(int lin) {
		line = lin;
	}

	public void setColune(int col) {
		 colune = col;
	}
	
	public Boolean samePosition(Cell comp)
	{
		return(colune == comp.colune && line == comp.line);
	}
	
	void update(int lin, int col){
		this.colune= this.colune+col;
		this.line = this.line + lin;
	}
	
	public int getLine(){
		return line;
	}
	
	public int getCol(){
		return colune;
	}
}
