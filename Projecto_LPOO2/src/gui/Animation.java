package gui;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.Gem;
public class Animation implements ActionListener {
	Gem Gems[];
	AnimationType Type;
	
	
	Animation(){
		
	}
	
	void update(Gem g[], AnimationType Type){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public enum AnimationType {
		Swap, Fill, Explosion
	}

}