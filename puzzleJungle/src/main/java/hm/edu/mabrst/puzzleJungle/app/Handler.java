package hm.edu.mabrst.puzzleJungle.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.view.MotionEvent;

public class Handler implements IHandler, IShootHandler {

	private int[][] coordinates;
	private HashMap<Integer, Bubble> map;
	private IShooter shooter;
	private int wall;
	private Bubble lastAdded;
	public int idCounter;
	private Bubble nextOne;
	private Bubble nextNextOne;

	/**
	 * Constructor for Handler
	 */
	public Handler() {
		this.shooter = new Shooter(this);
		this.map = new HashMap<Integer, Bubble>();
		this.idCounter = 10;
	}

	/**
	 * Method for Shooting Bubbles and checking if there are too many of the
	 * same color.
	 * 
	 * @param e
	 *            Motion Event of the shoot on the Touch Screen
	 */
	public void shoot(MotionEvent e) {
		try{
		coordinates = getShooter().shoot(getCoordinates(), e,nextOne);
		} catch(Exception ex){
			System.out.println("Shoot geht nicht");
		}
		lastAdded=nextOne;
		nextOne=nextNextOne;
		idCounter++;
		nextNextOne=new Bubble(this,idCounter);
		map.put(nextNextOne.getId(), nextNextOne);
		colorCheck(lastAdded);
	}

	/**
	 * Creates new game
	 */
	// wir hatten vergessen die Bubble in die Hashmap zutun.....
	// wir müssen die wall noch initialiseren
	public void newGame() {
		int[][] co = new int[20][16];
		int counter = 0;

		for (int i = 1; i <= 5*2; i += 2) {
			if (counter % 2 == 0) {
				for (int j = 0; j < 16; j += 2) {
					Bubble b = new Bubble(this, getID());
					map.put(b.getId(), b);
					for (int r = 0; r < 2; r++) {
						for (int c = 0; c < 2; c++) {
							co[i - r][j + c] = b.getId();
						}
					}

				}
			} else {
				for (int k = 1; k < 15; k += 2) {
					Bubble b = new Bubble(this, getID());
					map.put(b.getId(), b);
					for (int r = 0; r < 2; r++) {
						for (int c = 0; c < 2; c++) {
							co[i - r][k + c] = b.getId();
						}
					}
				}
			}
			counter++;
		}
		// bei jeder Kugel muss noch der Nachbar eingetragen werden.
		for (int k = 1; k < 10; k += 2) {
			for (int i = 1; i < 16; i += 2) {
				if (co[k][i] != 0) {
					Bubble b = map.get(co[k][i]);
					int[] ba = new int[6];
					for (int o = 0; o < 6; o++) {
						try {
							if (o == 0)
								ba[o] = co[k - 2][i-1];
							if (o == 1)
								ba[o] = co[k - 2][i +1];
							if (o == 2)
								ba[o] = co[k][i + 2];
							if (o == 3)
								ba[o] = co[k + 2][i + 1];
							if (o == 4)
								ba[o] = co[k + 2][i-1];
							if (o == 5)
								ba[o] = co[k][i - 2];
						} catch (Exception e) {
							ba[o] = 0;
						}
					}
					b.setNeighbors(ba);
				}
			}

		}

		idCounter++;
		nextOne=new Bubble(this,idCounter);
		map.put(nextOne.getId(), nextOne);
		idCounter++;
		nextNextOne=new Bubble(this,idCounter);
		map.put(nextNextOne.getId(), nextNextOne);
		setCoordinates(co);
	}

	public int[][] getCoordinates() {
		return coordinates;
	}

	private void setCoordinates(int[][] coordinates) {
		this.coordinates = coordinates;
	}

	public HashMap<Integer, Bubble> getMap() {
		return map;
	}

	public IShooter getShooter() {
		return shooter;
	}

	/**
	 * gets Bubble out of Hashmap
	 * 
	 * @param id
	 *            ID of the bubble
	 */
	public Bubble getBubble(int id) {
		return map.get(id);
	}

	/**
	 * Returns the Value of the Wall
	 */
	public int getWall() {
		return wall;
	}

	/**
	 * Returns id for a Bubble
	 * 
	 * @return ID for a new Bubble
	 */
	public int getID() {
		return idCounter++;
	}

	/**
	 * Method for checking if the new Bubble has more than 2 samecolored
	 * neighbors.
	 * 
	 * @param b
	 *            new Bubble
	 */
	public void colorCheck(Bubble b) {
		ArrayList<Bubble> list = moreColorCheck(getNextSameColoredNeighbors(
				b.getSameColoredNeighbors(), b));
		Iterator<Bubble> it2 = list.iterator();
		String s = "List der gelöschten Kugeln: ";
		while (it2.hasNext()) {
			s = s + it2.next().getId() + " ";
		}
		System.out.println(s + "\n");
		if (list.size() > 2) {
			Iterator<Bubble> it = list.iterator();
			while (it.hasNext()) {
				deleteBubble(it.next());
			}
			if (map.containsKey(b.getId()))
				deleteBubble(b);
			showCoordinates(this);
			checkIsland();
		}

	}

	/**
	 * Method for finding sameColored Neighbors.
	 * 
	 * @param list
	 *            Bubbles of first Selection.
	 * @return List of Bubbles with same Color.
	 */
	private ArrayList<Bubble> moreColorCheck(ArrayList<Bubble> list) {
		ArrayList<Bubble> checklist = new ArrayList<Bubble>();
		Iterator<Bubble> it = list.iterator();
		while (it.hasNext()) {
			Bubble b = it.next();
			ArrayList<Bubble> checklist2 = getNextSameColoredNeighbors(
					b.getSameColoredNeighbors(), b);
			Iterator<Bubble> it2 = checklist2.iterator();
			while (it2.hasNext()) {
				checklist.add(it2.next());
			}
		}
		Iterator<Bubble> it2 = checklist.iterator();
		boolean bool = false;
		while (it2.hasNext()) {
			Bubble b = it2.next();
			if (!list.contains(b)) {
				list.add(b);
				bool = true;
			}
		}
		if (bool == true) {
			list = moreColorCheck(checklist);
		}
		return list;

	}

	/**
	 * Getter for all bubbles neighbored to the bubbles in the array
	 * 
	 * @param ba
	 *            Bubbles in the array
	 * @return List of all Bubbles
	 */
	public ArrayList<Bubble> getNextSameColoredNeighbors(Bubble[] ba, Bubble b) {
		ArrayList<Bubble> list = new ArrayList<Bubble>();
		for (int i = 0; i < 6; i++) {
			if (ba[i] != null) {
				if (!list.contains(ba[i])) list.add(ba[i]);
					for (int j = 0; j < 6; j++) {
						if (ba[i].getSameColoredNeighbors()[j] != null
								&& ba[i].getSameColoredNeighbors()[j].color
										.equals(b.getColor())) {
							if (!list
									.contains(ba[i].getSameColoredNeighbors()[j]))
								list.add(ba[i].getSameColoredNeighbors()[j]);
						}
					
				}
			}
		}
		return list;
	}

	/**
	 * Deletes bubble from their neighbors. Deletes bubble from hashmap and
	 * coordinates.
	 * 
	 * @param b
	 *            bubble to delete
	 */
	public void deleteBubble(Bubble b) {
		b.delete();
		map.remove(b);
		deleteFromCoordinates(b);
	}

	/**
	 * Deletes a bubble from the coordinates array
	 * 
	 * @param b
	 *            the bubble to delete
	 */
	private void deleteFromCoordinates(Bubble b) {
		int counter = 0;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 16; j++) {
				counter++;
				if (b.getId() == getCoordinates()[i][j]) {
					coordinates[i][j] = 0;
				}
			}
		}
	}

	// müssen noch einarbeiten das die wall nach unten kommt
	private void checkIsland() {
		ArrayList<Integer> top = new ArrayList<Integer>();
		for (int i = 0; i < 16; i += 2) {
			if (coordinates[0][i] != 0)
				top.add(coordinates[0][i]);
		}
		
		
		//kommt weg sobald abgeschlossen
		Iterator<Integer> it = top.iterator();
		String s = "\nListe der CheckIsland top kugeln vor checkIsland ";
		while (it.hasNext()) {
			s = s + it.next() + "  ";
		}
		System.out.println(s + "\n");
		
		top=islandCheck(top);

		
		
		
		//kommt weg sobald Test abgeschlossen
		s = "";
		it = top.iterator();
		s = "\nListe der CheckIsland top kugeln danach ";
		while (it.hasNext()) {
			s = s + it.next() + "  ";
		}
		System.out.println(s + "\n");


		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 16; j++) {
				if (!top.contains(coordinates[i][j])&&map.containsKey(coordinates[i][j])) {
					deleteBubble(map.get(coordinates[i][j]));
				}
			}

		}
		
	}
	
	private ArrayList<Integer> islandCheck(ArrayList<Integer> top){
		ArrayList<Integer> newTop=recursiveIslandCheck(top);
		ArrayList<Integer> checkTop=recursiveIslandCheck(newTop);
		if(!newTop.equals(checkTop)) top=islandCheck(checkTop);
		else top=newTop;
		return top;
		
		

				
	}
	
	
	
		private ArrayList<Integer> recursiveIslandCheck(ArrayList<Integer> top){

			String s="";
			Iterator<Integer> it=top.iterator();
			
			for (int i = 2; i < 20; i += 2) {
				for (int j = 0; j < 16; j += 2) {
					if (map.get(coordinates[i][j]) != null) {
						//System.out.println("Kugel die gecheckt wird : "+ checkBubble.getId());  
						for (int k = 0; k < 6; k++) {
							//System.out.println("nachbar nummer " + k + " : "+ checkBubble.getNeighbors()[k]);
							if (map.get(coordinates[i][j]).getNeighbors()[k] != 0) {
									if (top.contains(map.get(coordinates[i][j]).getNeighbors()[k])) {
										top.add(map.get(coordinates[i][j]).getId());
										//System.out.println(checkBubble.getId() +" kugel wird hinzugefügt");
										k=6;
									}
								}
						}

						it = top.iterator();
						s = "\nListe der CheckIsland top kugeln ";
						while (it.hasNext()) {
							s = s + it.next() + "  ";
						}
						//System.out.println(s + "\n");
					}

				}
				for(int j=15;j>=0;j-=2){
					if(map.get(getCoordinates()[i][j])!=null){
						//System.out.println("Kugel die gecheckt wird : " + map.get(getCoordinates()[i][j]).getId());
						for (int k=0;k<6;k++){
							//System.out.println("Nachbar Nummer " + k + " : "+map.get(coordinates[i][j]).getNeighbors()[k]);
							if(map.get(coordinates[i][j]).getNeighbors()[k]!=0){
								if(top.contains(map.get(coordinates[i][j]).getNeighbors()[k])){
									top.add(map.get(coordinates[i][j]).getId());
									//System.out.println(map.get(coordinates[i][j]) + " Kugel wird hinzugefügt");
									k=6;
								}
								
							}
						}

						it = top.iterator();
						s = "\nListe der CheckIsland top kugeln ";
						while (it.hasNext()) {
							s = s + it.next() + "  ";
						}
						//System.out.println(s + "\n");
					}
				}

			}
			return top;
		}
		/*
		 * Versuch für jede Kugel zu überprüfen ob sie ein Verbindung zur wand
		 * hat. for (int i=40;i>=2;i--){ for (int j=0;i<=32;j++){
		 * if(!map.get(coordinates[i][j]).gotNeighbor())
		 * deleteBubble(map.get(coordinates[i][j])); else{
		 * if(!checkConnectionToWall(map.get(coordinates[i][j]),top))
		 * deleteBubble(map.get(coordinates[i][j])); }
		 * 
		 * } }
		 */

		// checkt ob Inseln von Kugeln bestehen
	

	/*
	 * Problem bei der Methode ist das man noch eine Lösung finden müsste wie
	 * man es schafft das er in einem zweiten array alle speichert die er schon
	 * durchsucht hat. ich denke das wir dann ganz unhandlich private boolean
	 * checkConnectionToWall(Bubble b,ArrayList list){ int[]
	 * neighbors=b.getNeighbors(); for(int i=0;i<=6;i++){ if(neighbors[i]!=0){
	 * if(list.contains(map.get(neighbors[i])))return true;
	 * if(checkConnectionToWall(map.get(neighbors[i]),list))return true; }
	 * 
	 * 
	 * }
	 * 
	 * return null; }
	 */

	/**
	 * Puts Bubble into Hashmap
	 * 
	 * @param b
	 *            Bubble to put in Hashmap
	 */

	public void addBubbleToHashMap(Bubble b) {
		lastAdded = b;
		map.put(b.getId(), b);

	}
	public static void showCoordinates(Handler h) {
		String s = "";
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 16; i++) {
				if (h.getCoordinates()[j][i] == 0)
					s = s + "0 |";
				else
					s = s + h.getCoordinates()[j][i] + "|";
			}
			s = s + "\n";
		}
		System.out.println(s);
	}
}
