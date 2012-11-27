package hm.edu.mabrst.puzzleJungle.app;

import java.util.Map;
import java.util.Random;

/**
 * Class for a Bubble
 * 
 * @author said
 * 
 */
public class Bubble implements IBubbleShooter, IBubbleHandler {

	public BubbleColor color;
	private int id;
	private int[] neighbors;
	private IHandler handler;

	/**
	 * Constructor for Bubble
	 * 
	 * @param h
	 *            Handler of the Bubbles
	 */
	public Bubble(Handler h, int i) {
		this.id = i;
		this.neighbors = new int[6];
		Random r = new Random();
		int c = r.nextInt(4) + 1;
		if (c == 1)
			this.color = BubbleColor.RED;
		if (c == 2)
			this.color = BubbleColor.BLUE;
		if (c == 3)
			this.color = BubbleColor.GREEN;
		if (c == 4)
			this.color = BubbleColor.YELLOW;
		this.handler = h;

	}

	/**
	 * Creates Array of same colored neighbors return Array of bubbles
	 */
	public Bubble[] getSameColoredNeighbors() {
		Bubble[] bubble=new Bubble[6];
		for (int i=0;i<6;i++){
			if(getNeighbors()[i]!=0 && getHandler().getBubble(getNeighbors()[i]).getColor()==getColor()) bubble[i]=getHandler().getBubble(getNeighbors()[i]);
			else bubble[i]=null;
			//if(getNeighbors()[i]!=0)bubble[i]=getHandler().getBubble(getNeighbors()[i]);
		}
		return bubble;
		// kann sein das nicht funktioniert das der array an ein paar stellen null enthält. (fange das null aber später ab!!!)
	}

	/**
	 * deletes Bubble out of all neighbors
	 */
	public void delete() {
		for (int i = 0; i <6; i++) {
			if (getNeighbors()[i] != 0) {
				Bubble b = getHandler().getBubble(getNeighbors()[i]);
				b.deleteNeighbor(getId());
				// muss enwentuell einen Try-Catch-block enthalten da er sich
				// kugel holen will die bereits gelöscht wurden.
				// muss noch verkleinert werden in dem "Bubble b" rausgenommen
				// wird und das obrige in der unteren Zeile hinzugefügt wird.
			}
		}
	}

	/**
	 * deletes one neighbor out of array
	 * 
	 * @param id
	 *            neighbor to delete
	 */
	public void deleteNeighbor(int id) {
		for (int i = 0; i < 6; i++) {
			if (getNeighbors()[i] == id) {
				int[] d = getNeighbors();
				d[i] = 0;
				setNeighbors(d);
				// unschöne Methode...^^
			}
		}
	}

	/**
	 * Sets Neighbors of the Bubble
	 * 
	 * @param id
	 *            array of the neighbors
	 */
	public void setNeighbors(int[] id) {
		neighbors = id;
	}

	public boolean gotNeighbor() {
		int i = 0;
		for (int d = 0; d < 6; d++) {
			i = i + getNeighbors()[d];
		}
		if (i > 0)
			return true;
		else
			return false;
	}

	public int getId() {
		return id;
	}

	public IHandler getHandler() {
		return handler;
	}

	public int[] getNeighbors() {
		return neighbors;
	}

	public BubbleColor getColor() {
		return color;
	}
}
