package abeellab.kmertree;

import java.util.TimerTask;

public class show_advance extends TimerTask{
	public void run() {
	       System.out.println((float)Leaf.getDone()/Leaf.getCount() + "%"); 
	    }
}
