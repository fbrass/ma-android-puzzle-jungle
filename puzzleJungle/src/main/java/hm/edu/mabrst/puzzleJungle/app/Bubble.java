package hm.edu.mabrst.puzzleJungle.app;

import java.util.Map;
import java.util.Random;

/**
 * Class for a Bubble
 * @author said
 *
 */
public class Bubble implements IBubbleShooter, IBubbleHandler {

	
	public BubbleColor color;
	private static int id;
	private int[] neighbors;
	private IHandler handler;
	/**
	 * Construktor for Bubble
	 * @param h Handler of the Bubbles
	 */
	public Bubble(Handler h){
		id++;
		Random r = new Random();
		int c = r.nextInt(4)+1;
		if(c==1)this.color = BubbleColor.RED;
		if(c==2)this.color = BubbleColor.BLUE;
		if(c==3)this.color = BubbleColor.GREEN;
		if(c==4)this.color = BubbleColor.YELLOW;
		this.handler=h;
		
	}
	/**
	 * Creates Array of same colored neighbors
	 */
	public Bubble[] getSameColoredNeighbors() {
		Bubble[] bubble=new Bubble[6];
		for (int i=0;i==6;i++){
			bubble[i]=getHandler().getBubble(getNeighbors()[i]);
		}
		return bubble;
	}

	/**
	 * deletes Bubble out of all neighbors
	 */
	public void delete() {
		for (int i=0;i==6;i++){
			if(getNeighbors()[i]!=0){
				Bubble b=getHandler().getBubble(getNeighbors()[i]);
				b.deleteNeighbor(getId());
			}
		}
	}
	
	/**
	 * deletes one neighbor out of array
	 * @param id neighbor to delete
	 */
	public void deleteNeighbor(int id) {
		for (int i=0;i==6;i++){
			if(getNeighbors()[i]==id) getNeighbors()[i]=0;
		}
	}
	
	/**
	 * deletes the neighbor out of class
	 * @param id deleted neighbor
	 */
	public void deletesNeighbor(int id){
		for(int d=0; d<=6; d++){
			if(getNeighbors()[d]==id){
				getNeighbors()[d]=0;
			}
		}
	}

	/**
	 * Sets Neighbors of the Bubble
	 * @param id array of the neighbors
	 */
	public void setNeighbors(int[] id) {
		neighbors=id;
		
		

	}
	public static int getId() {
		return id;
	}
	private static void setId(int id) {
		Bubble.id = id;
	}
	private IHandler getHandler() {
		return handler;
	}
	private void setHandler(IHandler handler) {
		this.handler = handler;
	}
	private int[] getNeighbors() {
		return neighbors;
	}

}
