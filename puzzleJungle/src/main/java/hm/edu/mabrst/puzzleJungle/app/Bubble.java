package hm.edu.mabrst.puzzleJungle.app;

import java.util.Random;

public class Bubble implements IBubbleShooter, IBubbleHandler {

	// TODO enum noch machen
	
	BubbleColor color;
	static int id;
	int[] neighbors;
	
	/**
	 * Construktor for Bubble
	 */
	public Bubble(){
		id++;
		Random r = new Random();
		int c = r.nextInt(4)+1;
		if(c==1)this.color = BubbleColor.RED;
		if(c==2)this.color = BubbleColor.BLUE;
		if(c==3)this.color = BubbleColor.GREEN;
		if(c==4)this.color = BubbleColor.YELLOW;
		
	}
	@Override
	public Bubble[] getSameColoredNeighbors() {
		// TODO Auto-generated method stub
		// abfrage ob nachbar gleiche farbe hat
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub4
		// löscht bei allen nachbarn diese Kugel
		
	}
	/**
	 * deletes the neighbor out of class
	 * @param id deleted neighbor
	 */
	public void deletesNeighbor(int id){
		for(int d=0; d<=6; d++){
			if(neighbors[d]==id){
				neighbors[d]=0;
			}
		}
	}

	@Override
	public void setNeighbors(int id) {
		// TODO Auto-generated method stub
		

	}

}
