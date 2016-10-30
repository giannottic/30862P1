package ca.thread;

import java.util.*;
import com.brackeen.javagamebook.tilegame.sprites.*;

public class TimerThread extends TimerTask{

	public TimerThread(Creature creature){
		owner = creature;
	}
	
	private Creature owner;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		owner.wakeUp();
	}

}
